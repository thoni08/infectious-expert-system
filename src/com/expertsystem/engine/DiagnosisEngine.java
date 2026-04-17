package com.expertsystem.engine;

import com.expertsystem.model.DiagnosisResult;
import com.expertsystem.model.Disease;
import com.expertsystem.model.PatientInput;

import java.util.List;

public interface DiagnosisEngine {
    DiagnosisResult diagnose(PatientInput input, List<Disease> diseases);
}
