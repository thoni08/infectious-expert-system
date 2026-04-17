package com.expertsystem.model;

public class Symptom {
    private String id;
    private String description;
    private String category;

    public Symptom(String id, String description, String category) {
        this.id = id;
        this.description = description;
        this.category = category;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
}
