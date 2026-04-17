package com.expertsystem.engine;

import com.expertsystem.model.DiagnosisResult;
import com.expertsystem.model.Disease;
import com.expertsystem.model.PatientInput;
import com.expertsystem.model.Symptom;

import java.util.ArrayList;
import java.util.List;

public class WeightedEngine implements DiagnosisEngine {
    private static final double MIN_THRESHOLD = 40.0;

    @Override
    public DiagnosisResult diagnose(PatientInput input, List<Disease> diseases) {
        Disease bestMatch = null;
        double bestPercentage = -1;
        List<Symptom> bestMatchedSymptoms = new ArrayList<>();
        java.util.Map<String, String> diseaseDetails = new java.util.LinkedHashMap<>();
        boolean hasTie = false;

        for (Disease disease : diseases) {
            double totalWeight = disease.getRequiredSymptoms().size();
            double matchedWeight = 0;
            List<Symptom> matchedSymptoms = new ArrayList<>();

            for (Symptom s : disease.getRequiredSymptoms()) {
                if (input.getSelectedSymptomIds().contains(s.getId())) {
                    matchedWeight += 1;
                    matchedSymptoms.add(s);
                }
            }

            double percentage = totalWeight == 0 ? 0 : (matchedWeight / totalWeight) * 100;
            
            diseaseDetails.put(disease.getName(), String.format("%.2f", percentage) + "%");

            if (percentage > bestPercentage) {
                bestPercentage = percentage;
                bestMatch = disease;
                bestMatchedSymptoms = new ArrayList<>(matchedSymptoms);
                hasTie = false;
            } else if (percentage == bestPercentage) {
                hasTie = true;
                for (Symptom s : matchedSymptoms) {
                    if (!bestMatchedSymptoms.contains(s)) {
                        bestMatchedSymptoms.add(s);
                    }
                }
            }
        }

        if (hasTie || bestPercentage == 0) {
            bestMatch = null;
            if (bestPercentage == 0) {
                bestMatchedSymptoms = new ArrayList<>();
            }
        }
        
        String formattedConfidence = String.format("%.2f", bestPercentage) + "%";

        if (bestPercentage >= MIN_THRESHOLD) {
            return new DiagnosisResult(bestMatch, bestMatchedSymptoms, bestPercentage, formattedConfidence, diseaseDetails);
        }
        return new DiagnosisResult(null, bestMatchedSymptoms, bestPercentage, formattedConfidence, diseaseDetails);
    }
}
