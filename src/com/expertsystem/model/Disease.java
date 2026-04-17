package com.expertsystem.model;

import java.util.List;

public class Disease {
    private String name;
    private String category;
    private List<Symptom> requiredSymptoms;

    public Disease(String name, String category, List<Symptom> requiredSymptoms) {
        this.name = name;
        this.category = category;
        this.requiredSymptoms = requiredSymptoms;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public List<Symptom> getRequiredSymptoms() { return requiredSymptoms; }
}
