package com.expertsystem.view;

import com.expertsystem.controller.SystemController;
import com.expertsystem.engine.DiagnosisEngine;
import com.expertsystem.engine.RuleBasedEngine;
import com.expertsystem.model.DiagnosisResult;
import com.expertsystem.model.PatientInput;
import com.expertsystem.view.gui.ModelSelectionPanel;
import com.expertsystem.view.gui.QuestionnairePanel;
import com.expertsystem.view.gui.ReportPanel;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class GuiUI {
    private JFrame frame;
    private SystemController controller;
    private DiagnosisEngine currentEngine;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public void start(SystemController controller) {
        this.controller = controller;
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            // Set some global defaults to match medical theme
            UIManager.put("Button.arc", 8);
            UIManager.put("Component.arc", 8);
            UIManager.put("CheckBox.arc", 8);
            UIManager.put("ProgressBar.arc", 8);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Sistem Pakar: Diagnosa Penyakit Infeksi dan Non-Infeksi");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024, 768);
            frame.setLocationRelativeTo(null);
            
            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);
            mainPanel.setBackground(new Color(241, 243, 249));

            startOver();

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }

    public void setEngine(DiagnosisEngine engine) {
        this.currentEngine = engine;
    }

    public void showQuestionnaire() {
        QuestionnairePanel qPanel = new QuestionnairePanel(this, controller);
        mainPanel.add(qPanel, "Questionnaire");
        cardLayout.show(mainPanel, "Questionnaire");
    }

    public void finishQuestionnaire(PatientInput input) {
        DiagnosisResult result = controller.runDiagnosis(input, currentEngine);
        String engineName = currentEngine instanceof RuleBasedEngine ? "Rule-Based System" : "Weighted System";
        ReportPanel rPanel = new ReportPanel(this, result, engineName);
        mainPanel.add(rPanel, "Report");
        cardLayout.show(mainPanel, "Report");
    }

    public void startOver() {
        mainPanel.removeAll();
        ModelSelectionPanel msPanel = new ModelSelectionPanel(this, controller);
        mainPanel.add(msPanel, "ModelSelection");
        cardLayout.show(mainPanel, "ModelSelection");
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
