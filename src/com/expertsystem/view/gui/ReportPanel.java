package com.expertsystem.view.gui;

import com.expertsystem.model.DiagnosisResult;
import com.expertsystem.model.Symptom;
import com.expertsystem.view.GuiUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReportPanel extends JPanel {
    private GuiUI parent;

    public ReportPanel(GuiUI parent, DiagnosisResult result, String engineName) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 250));

        JPanel container = new JPanel(new GridLayout(1, 2, 40, 0));
        container.setBackground(new Color(248, 249, 250));
        container.setBorder(new EmptyBorder(50, 50, 50, 50));
        
        // Left Column (Header)
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(new Color(248, 249, 250));

        JLabel title1 = new JLabel("Evaluasi Analisis");
        title1.setForeground(new Color(0, 63, 135));
        title1.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        JLabel title2 = new JLabel("<html>Laporan<br>Diagnosa</html>");
        title2.setFont(new Font("SansSerif", Font.BOLD, 48));
        title2.setForeground(new Color(25, 28, 29));

        left.add(title1);
        left.add(Box.createRigidArea(new Dimension(0, 10)));
        left.add(title2);

        // Right Column (Results)
        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(Color.WHITE);
        right.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(231, 232, 233), 1),
            new EmptyBorder(30, 30, 30, 30)
        ));

        // Banner
        JPanel banner = new JPanel();
        banner.setLayout(new BoxLayout(banner, BoxLayout.Y_AXIS));
        banner.setBackground(new Color(0, 63, 135));
        banner.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel method = new JLabel("Metode: " + engineName);
        method.setForeground(Color.WHITE);
        method.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        String diseaseName = (result.getDiagnosedDisease() != null) ? result.getDiagnosedDisease().getName() : "Tidak Valid";
        String category = (result.getDiagnosedDisease() != null) ? result.getDiagnosedDisease().getCategory() : "Tidak Valid";
        
        JLabel resultLbl = new JLabel("<html>Hasil Diagnosa: " + diseaseName + " (" + category + ")</html>");
        resultLbl.setForeground(Color.WHITE);
        resultLbl.setFont(new Font("SansSerif", Font.BOLD, 24));
        
        banner.add(method);
        banner.add(Box.createRigidArea(new Dimension(0, 15)));
        banner.add(resultLbl);

        // Details
        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setAlignmentX(Component.LEFT_ALIGNMENT);
        details.setBackground(Color.WHITE);
        details.setBorder(new EmptyBorder(30, 0, 10, 0));

        JLabel confLbl = new JLabel("<html>Tingkat Keyakinan: " + result.getFormattedConfidence() + "</html>");
        confLbl.setFont(new Font("SansSerif", Font.BOLD, 18));
        confLbl.setBorder(new EmptyBorder(0, 0, 0, 0));
        confLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue((int) result.getConfidencePercentage());
        bar.setStringPainted(false);
        bar.setBorderPainted(false);
        bar.setForeground(new Color(0, 63, 135));
        bar.setPreferredSize(new Dimension(Integer.MAX_VALUE, 12));
        bar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 12));
        bar.setBackground(new Color(225, 227, 228));
        bar.setAlignmentX(Component.LEFT_ALIGNMENT);

        details.add(confLbl);
        details.add(Box.createRigidArea(new Dimension(0, 10)));
        details.add(bar);
        details.add(Box.createRigidArea(new Dimension(0, 30)));

        class WrapPanel extends JPanel implements Scrollable {
            public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
            public int getScrollableUnitIncrement(Rectangle r, int o, int d) { return 16; }
            public int getScrollableBlockIncrement(Rectangle r, int o, int d) { return 16; }
            public boolean getScrollableTracksViewportWidth() { return true; }
            public boolean getScrollableTracksViewportHeight() { return false; }
        }
        JPanel matchPanel = new WrapPanel();
        matchPanel.setBackground(Color.WHITE);
        matchPanel.setLayout(new BoxLayout(matchPanel, BoxLayout.Y_AXIS));
        matchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel matchLbl = new JLabel("<html>Gejala yang Cocok</html>");
        matchLbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        matchLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        matchPanel.add(matchLbl);
        matchPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        for (Symptom s : result.getMatchedSymptoms()) {
            JLabel sLbl = new JLabel("<html>- " + s.getDescription() + "</html>");
            sLbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
            sLbl.setBorder(new EmptyBorder(0, 0, 8, 0));
            sLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            matchPanel.add(sLbl);
        }

        matchPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel allLbl = new JLabel("<html>Evaluasi Semua Penyakit</html>");
        allLbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        allLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        matchPanel.add(allLbl);
        matchPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        for (java.util.Map.Entry<String, String> entry : result.getDiseaseDetails().entrySet()) {
            JLabel dLbl = new JLabel("<html>- " + entry.getKey() + ": " + entry.getValue() + "</html>");
            dLbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
            dLbl.setBorder(new EmptyBorder(0, 0, 8, 0));
            dLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            matchPanel.add(dLbl);
        }

        JButton restartBtn = new JButton("Mulai Analisis Baru");
        restartBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        restartBtn.setBackground(new Color(0, 63, 135));
        restartBtn.setForeground(Color.WHITE);
        restartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        restartBtn.addActionListener(e -> parent.startOver());
        
        JPanel actionPnl = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        actionPnl.setBackground(Color.WHITE);
        actionPnl.setBorder(new EmptyBorder(20, 0, 0, 0));
        actionPnl.setAlignmentX(Component.LEFT_ALIGNMENT);
        actionPnl.add(restartBtn);

        JScrollPane scrollPane = new JScrollPane(matchPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        details.add(scrollPane);

        right.add(banner, BorderLayout.NORTH);
        right.add(details, BorderLayout.CENTER);
        right.add(actionPnl, BorderLayout.SOUTH);

        container.add(left);
        container.add(right);

        add(container, BorderLayout.CENTER);
    }
}
