package com.expertsystem.view;

import com.expertsystem.controller.SystemController;
import com.expertsystem.engine.DiagnosisEngine;
import com.expertsystem.engine.RuleBasedEngine;
import com.expertsystem.engine.WeightedEngine;
import com.expertsystem.model.DiagnosisResult;
import com.expertsystem.model.PatientInput;
import com.expertsystem.model.Symptom;

import java.util.*;

public class ConsoleUI {
    public void start(SystemController controller) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Sistem Pakar: Diagnosa Penyakit Infeksi dan Non-Infeksi ===");
        System.out.println("Pilih Model Pendekatan:");
        System.out.println("1. Rule-Based System (Logika Boolean)");
        System.out.println("2. Weighted System (Bobot Gejala)");
        int engineChoice = scanner.nextInt();
        scanner.nextLine();

        DiagnosisEngine engine = (engineChoice == 1) ? new RuleBasedEngine() : new WeightedEngine();

        PatientInput input = new PatientInput();

        Map<String, List<Symptom>> categorizedSymptoms = new LinkedHashMap<>();
        for (Symptom s : controller.getAllSymptoms()) {
            categorizedSymptoms.computeIfAbsent(s.getCategory(), k -> new ArrayList<>()).add(s);
        }

        for (Map.Entry<String, List<Symptom>> entry : categorizedSymptoms.entrySet()) {
            System.out.println("\n--- " + entry.getKey() + " ---");
            for (Symptom s : entry.getValue()) {
                System.out.print(s.getDescription() + " (y/t): ");
                String answer = scanner.nextLine().trim().toLowerCase();
                if (answer.equals("y") || answer.equals("ya") || answer.equals("yes")) {
                    input.addSymptom(s.getId());
                }
            }
        }

        DiagnosisResult result = controller.runDiagnosis(input, engine);

        System.out.println("\n=== Laporan Diagnosa ===");
        if (result.getDiagnosedDisease() != null) {
            System.out.println("Penyakit: " + result.getDiagnosedDisease().getName() + " (Kategori: " + result.getDiagnosedDisease().getCategory() + ")");
            System.out.println("Keyakinan: " + result.getFormattedConfidence());
        } else {
            System.out.println("Penyakit: Tidak Valid");
            System.out.println("Keyakinan: " + result.getFormattedConfidence());
        }

        System.out.println("\nGejala yang Cocok:");
        for (Symptom s : result.getMatchedSymptoms()) {
            System.out.println("- " + s.getDescription());
        }
        
        System.out.println("\nEvaluasi Semua Penyakit:");
        for (Map.Entry<String, String> entry : result.getDiseaseDetails().entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue());
        }

        scanner.close();
    }
}
