package com.expertsystem.model;

import java.util.HashSet;
import java.util.Set;

public class PatientInput {
    private Set<String> selectedSymptomIds = new HashSet<>();

    public void addSymptom(String id) {
        selectedSymptomIds.add(id);
    }

    public Set<String> getSelectedSymptomIds() {
        return selectedSymptomIds;
    }
}
