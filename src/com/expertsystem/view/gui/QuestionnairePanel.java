package com.expertsystem.view.gui;

import com.expertsystem.controller.SystemController;
import com.expertsystem.engine.DiagnosisEngine;
import com.expertsystem.model.PatientInput;
import com.expertsystem.model.Symptom;
import com.expertsystem.view.GuiUI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class QuestionnairePanel extends JPanel {
    private GuiUI parent;
    private SystemController controller;
    private Map<String, List<Symptom>> categorizedSymptoms;
    private List<String> categories;
    private int currentCategoryIndex = 0;
    private JPanel centerPanel;
    private PatientInput patientInput;
    private Map<String, JCheckBox> checkboxes;

    public QuestionnairePanel(GuiUI parent, SystemController controller) {
        this.parent = parent;
        this.controller = controller;
        this.patientInput = new PatientInput();
        this.checkboxes = new HashMap<>();
        
        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 250));

        categorizedSymptoms = new LinkedHashMap<>();
        for (Symptom s : controller.getAllSymptoms()) {
            categorizedSymptoms.computeIfAbsent(s.getCategory(), k -> new ArrayList<>()).add(s);
        }
        categories = new ArrayList<>(categorizedSymptoms.keySet());

        initUI();
    }

    private void initUI() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(new EmptyBorder(40, 50, 40, 50));
        add(centerPanel, BorderLayout.CENTER);

        updateView();
    }

    private void updateView() {
        centerPanel.removeAll();
        String currentCat = categories.get(currentCategoryIndex);
        
        JLabel header = new JLabel(currentCat);
        header.setFont(new Font("SansSerif", Font.BOLD, 28));
        header.setForeground(new Color(25, 28, 29));
        
        JLabel sub = new JLabel("<html>Silahkan lihat daftar gejala di bawah ini. Pilih semua gejala yang saat ini dirasakan.</html>");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(new Color(66, 71, 82));
        sub.setBorder(new EmptyBorder(10, 0, 30, 0));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        headerPanel.add(header);
        headerPanel.add(sub);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.Y_AXIS));
        gridPanel.setBackground(Color.WHITE);

        for (Symptom s : categorizedSymptoms.get(currentCat)) {
            JCheckBox cb = checkboxes.computeIfAbsent(s.getId(), k -> {
                JCheckBox newCb = new JCheckBox("<html>" + s.getDescription() + "</html>");
                newCb.setFont(new Font("SansSerif", Font.BOLD, 16));
                newCb.setBackground(new Color(248, 249, 250));
                newCb.setOpaque(true);
                newCb.setBorderPainted(true);
                newCb.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(226, 230, 212), 1),
                    new EmptyBorder(15, 20, 15, 20)
                ));
                return newCb;
            });
            cb.setAlignmentX(Component.LEFT_ALIGNMENT);
            cb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
            gridPanel.add(cb);
            gridPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setBackground(Color.WHITE);
        actions.setBorder(new EmptyBorder(30, 0, 0, 0));
        
        if (currentCategoryIndex > 0) {
            JButton prevBtn = new JButton("Sebelumnya");
            prevBtn.addActionListener(e -> {
                currentCategoryIndex--;
                updateView();
            });
            actions.add(prevBtn);
        }

        if (currentCategoryIndex < categories.size() - 1) {
            JButton nextBtn = new JButton("Berikutnya");
            nextBtn.addActionListener(e -> {
                currentCategoryIndex++;
                updateView();
            });
            actions.add(nextBtn);
        } else {
            JButton finishBtn = new JButton("Selesai & Diagnosa");
            finishBtn.setBackground(new Color(0, 63, 135));
            finishBtn.setForeground(Color.WHITE);
            finishBtn.addActionListener(e -> finishDiagnosis());
            actions.add(finishBtn);
        }

        centerPanel.add(headerPanel, BorderLayout.NORTH);
        centerPanel.add(gridPanel, BorderLayout.CENTER);
        centerPanel.add(actions, BorderLayout.SOUTH);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void finishDiagnosis() {
        for (Map.Entry<String, JCheckBox> entry : checkboxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                patientInput.addSymptom(entry.getKey());
            }
        }
        parent.finishQuestionnaire(patientInput);
    }
}
