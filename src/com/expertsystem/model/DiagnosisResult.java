package com.expertsystem.model;

import java.util.List;

public class DiagnosisResult {
    private Disease diagnosedDisease;
    private List<Symptom> matchedSymptoms;
    private double confidencePercentage;
    private String formattedConfidence;
    private java.util.Map<String, String> diseaseDetails;

    public DiagnosisResult(Disease diagnosedDisease, List<Symptom> matchedSymptoms, double confidencePercentage, String formattedConfidence, java.util.Map<String, String> diseaseDetails) {
        this.diagnosedDisease = diagnosedDisease;
        this.matchedSymptoms = matchedSymptoms;
        this.confidencePercentage = confidencePercentage;
        this.formattedConfidence = formattedConfidence;
        this.diseaseDetails = diseaseDetails;
    }

    public Disease getDiagnosedDisease() { return diagnosedDisease; }
    public List<Symptom> getMatchedSymptoms() { return matchedSymptoms; }
    public double getConfidencePercentage() { return confidencePercentage; }
    public String getFormattedConfidence() { return formattedConfidence; }
    public java.util.Map<String, String> getDiseaseDetails() { return diseaseDetails; }
}
