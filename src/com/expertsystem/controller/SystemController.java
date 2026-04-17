package com.expertsystem.controller;

import com.expertsystem.engine.DiagnosisEngine;
import com.expertsystem.model.DiagnosisResult;
import com.expertsystem.model.Disease;
import com.expertsystem.model.PatientInput;
import com.expertsystem.model.Symptom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemController {
    private List<Disease> knowledgeBase;

    public SystemController() {
        initializeKnowledgeBase();
    }

    private void initializeKnowledgeBase() {
        knowledgeBase = new ArrayList<>();

        // Influenza
        Symptom s1 = new Symptom("S1", "Demam 37.5 - 39°C, meningkat bertahap, menggigil ringan?", "Bagian 1: Suhu Tubuh & Kondisi Umum");
        Symptom s2 = new Symptom("S2", "Batuk kering/berdahak ringan, >3 kali per jam?", "Bagian 2: Pernapasan (THT)");
        Symptom s3 = new Symptom("S3", "Hidung tersumbat, berair bening, bersin berulang?", "Bagian 2: Pernapasan (THT)");
        Symptom s4 = new Symptom("S4", "Nyeri saat menelan (sakit tenggorokan)?", "Bagian 2: Pernapasan (THT)");
        Disease influenza = new Disease("Influenza", "Infeksi", Arrays.asList(s1, s2, s3, s4));

        // Demam Berdarah
        Symptom s5 = new Symptom("S5", "Demam tinggi mendadak (>39°C), pola naik-turun 2-7 hari?", "Bagian 1: Suhu Tubuh & Kondisi Umum");
        Symptom s6 = new Symptom("S6", "Nyeri atau linu pada otot dan tulang?", "Bagian 3: Neurologis & Nyeri");
        Symptom s7 = new Symptom("S7", "Muncul bintik merah di kulit, tidak hilang saat ditekan?", "Bagian 4: Pencernaan, Kulit & Cairan");
        Symptom s8 = new Symptom("S8", "Mual muntah >2x sehari, nafsu makan turun tajam?", "Bagian 4: Pencernaan, Kulit & Cairan");
        Disease dbd = new Disease("Demam Berdarah", "Infeksi", Arrays.asList(s5, s6, s7, s8));

        // Diabetes
        Symptom s9 = new Symptom("S9", "Sangat sering haus, minum >3 liter sehari?", "Bagian 4: Pencernaan, Kulit & Cairan");
        Symptom s10 = new Symptom("S10", "Sering buang air kecil (hingga 8x sehari), terutama malam?", "Bagian 4: Pencernaan, Kulit & Cairan");
        Symptom s11 = new Symptom("S11", "Sangat mudah lelah, energi terasa cepat habis?", "Bagian 1: Suhu Tubuh & Kondisi Umum");
        Symptom s12 = new Symptom("S12", "Ada luka yang sulit sembuh (>2 minggu)?", "Bagian 4: Pencernaan, Kulit & Cairan");
        Disease diabetes = new Disease("Diabetes", "Non-Infeksi", Arrays.asList(s9, s10, s11, s12));

        // Hipertensi
        Symptom s13 = new Symptom("S13", "Sakit kepala berdenyut di pagi hari, bagian belakang kepala?", "Bagian 3: Neurologis & Nyeri");
        Symptom s14 = new Symptom("S14", "Pusing berputar, kepala ringan saat ubah posisi?", "Bagian 3: Neurologis & Nyeri");
        Symptom s15 = new Symptom("S15", "Penglihatan sering kabur/gangguan visual sementara?", "Bagian 3: Neurologis & Nyeri");
        Symptom s16 = new Symptom("S16", "Detak jantung meningkat dan tekanan darah konsisten tinggi (>=140/90 mmHg)?", "Bagian 3: Neurologis & Nyeri");
        Symptom s17 = new Symptom("S17", "Mimisan (perdarahan dari hidung)?", "Bagian 2: Pernapasan (THT)");
        Disease hipertensi = new Disease("Hipertensi", "Non-Infeksi", Arrays.asList(s13, s14, s15, s16, s17));

        knowledgeBase.add(influenza);
        knowledgeBase.add(dbd);
        knowledgeBase.add(diabetes);
        knowledgeBase.add(hipertensi);
    }

    public List<Disease> getKnowledgeBase() {
        return knowledgeBase;
    }

    public List<Symptom> getAllSymptoms() {
        List<Symptom> all = new ArrayList<>();
        for(Disease d : knowledgeBase) {
            all.addAll(d.getRequiredSymptoms());
        }
        return all;
    }

    public DiagnosisResult runDiagnosis(PatientInput input, DiagnosisEngine engine) {
        return engine.diagnose(input, knowledgeBase);
    }
}
