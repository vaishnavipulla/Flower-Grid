package application;

import gui.FlowerPanelGrid;

import javax.swing.*;
import java.awt.*;

public class FlowerApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flower Grid");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            FlowerPanelGrid flowerPanel = new FlowerPanelGrid();

            JPanel controlPanel = new JPanel(new FlowLayout());

            JSlider flowerSlider = new JSlider(1, 15, 4);
            flowerSlider.setMajorTickSpacing(2);
            flowerSlider.setPaintTicks(true);
            flowerSlider.setPaintLabels(true);

            JButton regenerateButton = new JButton("Regenerate Flowers");
            JCheckBox fallingFlowersCheckBox = new JCheckBox("Falling Flowers", true);
            JButton toggleBackgroundButton = new JButton("Toggle Background");

            flowerSlider.addChangeListener(e ->
                    flowerPanel.setFlowerCount(flowerSlider.getValue())
            );

            regenerateButton.addActionListener(e ->
                    flowerPanel.setFlowerCount(flowerSlider.getValue())
            );

            fallingFlowersCheckBox.addActionListener(e ->
                    flowerPanel.toggleFallingFlowers(fallingFlowersCheckBox.isSelected())
            );

            toggleBackgroundButton.addActionListener(e ->
                    flowerPanel.toggleBackground()
            );

            controlPanel.add(new JLabel("Flowers:"));
            controlPanel.add(flowerSlider);
            controlPanel.add(regenerateButton);
            controlPanel.add(fallingFlowersCheckBox);
            controlPanel.add(toggleBackgroundButton);

            frame.setLayout(new BorderLayout());
            frame.add(flowerPanel, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);

            frame.pack();
            flowerPanel.setFlowerCount(flowerSlider.getValue());

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
