package com.expertsystem.engine;

import com.expertsystem.model.DiagnosisResult;
import com.expertsystem.model.Disease;
import com.expertsystem.model.PatientInput;
import com.expertsystem.model.Symptom;

import java.util.ArrayList;
import java.util.List;

public class RuleBasedEngine implements DiagnosisEngine {
    @Override
    public DiagnosisResult diagnose(PatientInput input, List<Disease> diseases) {
        Disease bestMatch = null;
        double bestScore = -1;
        List<Symptom> bestMatchedSymptoms = new ArrayList<>();
        java.util.Map<String, String> diseaseDetails = new java.util.LinkedHashMap<>();
        int bestMatchedCount = 0;
        int bestTotalCount = 0;
        boolean hasTie = false;

        for (Disease disease : diseases) {
            List<Symptom> matchedSymptoms = new ArrayList<>();
            for (Symptom s : disease.getRequiredSymptoms()) {
                if (input.getSelectedSymptomIds().contains(s.getId())) {
                    matchedSymptoms.add(s);
                }
            }

            int matchedCount = matchedSymptoms.size();
            int totalCount = disease.getRequiredSymptoms().size();
            double score = totalCount == 0 ? 0 : ((double) matchedCount / totalCount) * 100;
            
            diseaseDetails.put(disease.getName(), matchedCount + "/" + totalCount + " benar");

            if (score > bestScore) {
                bestScore = score;
                bestMatch = disease;
                bestMatchedSymptoms = new ArrayList<>(matchedSymptoms);
                bestMatchedCount = matchedCount;
                bestTotalCount = totalCount;
                hasTie = false;
            } else if (score == bestScore) {
                hasTie = true;
                for (Symptom s : matchedSymptoms) {
                    if (!bestMatchedSymptoms.contains(s)) {
                        bestMatchedSymptoms.add(s);
                    }
                }
            }
        }

        if (hasTie || bestScore == 0) {
            bestMatch = null;
            if (bestScore == 0) {
                bestMatchedSymptoms = new ArrayList<>();
            }
        }
        
        String formattedConfidence = bestTotalCount == 0 ? "0/0 benar" : bestMatchedCount + "/" + bestTotalCount + " benar" + (bestMatch != null ? " (" + bestMatch.getName() + ")" : "");

        if (bestScore == 100.0) {
            formattedConfidence = bestMatchedCount + "/" + bestTotalCount + " benar";
            return new DiagnosisResult(bestMatch, bestMatchedSymptoms, bestScore, formattedConfidence, diseaseDetails);
        }

        return new DiagnosisResult(null, bestMatchedSymptoms, bestScore, formattedConfidence, diseaseDetails);
    }
}
