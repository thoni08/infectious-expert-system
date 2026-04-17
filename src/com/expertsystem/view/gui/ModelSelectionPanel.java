package com.expertsystem.view.gui;

import com.expertsystem.controller.SystemController;
import com.expertsystem.engine.RuleBasedEngine;
import com.expertsystem.engine.WeightedEngine;
import com.expertsystem.view.GuiUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ModelSelectionPanel extends JPanel {
    private GuiUI parent;
    private SystemController controller;
    private JRadioButton ruleBasedBtn;
    private JRadioButton weightedBtn;

    public ModelSelectionPanel(GuiUI parent, SystemController controller) {
        this.parent = parent;
        this.controller = controller;
        setLayout(new GridBagLayout());
        setBackground(new Color(241, 243, 249));

        JPanel card = createCardPanel();
        add(card, new GridBagConstraints());
    }

    private JPanel createCardPanel() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(194, 198, 212), 1),
                new EmptyBorder(30, 30, 30, 30)
        ));
        card.setPreferredSize(new Dimension(600, 350));
        card.setMinimumSize(new Dimension(600, 350));

        // Header
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Color.WHITE);
        JLabel title = new JLabel("Modul Pendekatan Analisis");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(25, 28, 29));
        
        JLabel subtitle = new JLabel("<html>Sistem pakar lokal untuk mengidentifikasi kondisi menular dan tidak menular.</html>");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(66, 71, 82));
        header.add(title);
        header.add(Box.createRigidArea(new Dimension(0, 10)));
        header.add(subtitle);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Separator
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setForeground(new Color(231, 232, 233));

        // Form
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(20, 0, 0, 0));

        JLabel lbl = new JLabel("Pilih Model Pendekatan:");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        lbl.setForeground(new Color(66, 71, 82));
        form.add(lbl);
        form.add(Box.createRigidArea(new Dimension(0, 15)));

        ButtonGroup group = new ButtonGroup();
        ruleBasedBtn = new JRadioButton("Rule-Based System (Logika Boolean)");
        ruleBasedBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        ruleBasedBtn.setBackground(Color.WHITE);
        ruleBasedBtn.setSelected(true);
        
        weightedBtn = new JRadioButton("Weighted System (Bobot Gejala)");
        weightedBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        weightedBtn.setBackground(Color.WHITE);

        group.add(ruleBasedBtn);
        group.add(weightedBtn);

        form.add(ruleBasedBtn);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(weightedBtn);
        form.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton initBtn = new JButton("Mulai Diagnosa");
        initBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        initBtn.setBackground(new Color(0, 63, 135));
        initBtn.setForeground(Color.WHITE);
        initBtn.setFocusPainted(false);
        initBtn.setPreferredSize(new Dimension(Integer.MAX_VALUE, 45));
        initBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        initBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        initBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        initBtn.addActionListener(e -> {
            if (ruleBasedBtn.isSelected()) {
                parent.setEngine(new RuleBasedEngine());
            } else {
                parent.setEngine(new WeightedEngine());
            }
            parent.showQuestionnaire();
        });

        form.add(initBtn);

        card.add(header, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);

        return card;
    }
}
