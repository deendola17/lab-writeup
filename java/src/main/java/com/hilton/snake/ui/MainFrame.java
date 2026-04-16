package com.hilton.snake.ui;

import com.hilton.snake.service.SnakeParkingManager;
import com.hilton.snake.model.Vehicle;
import com.hilton.snake.model.ParkingSlot;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;

/**
 * Main Premium Dashboard for Hilton Snake Parking.
 */
public class MainFrame extends JFrame {
    private SnakeParkingManager manager;
    private JPanel slotPanel;
    private JLabel statusLabel;

    public MainFrame() {
        manager = new SnakeParkingManager();
        initUI();
    }

    private void initUI() {
        setTitle("SNAKE PARKING - HILTON HOTEL LUXURY EDITION");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(StyleConstants.BACKGROUND);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(StyleConstants.PANEL_BG);
        JLabel title = new JLabel("SNAKE PARKING HILTON");
        title.setFont(StyleConstants.HEADER_FONT);
        title.setForeground(StyleConstants.SNAKE_GREEN);
        header.add(title);
        header.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // Content
        slotPanel = new JPanel(new GridLayout(4, 5, 10, 10));
        slotPanel.setBackground(StyleConstants.BACKGROUND);
        slotPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        refreshSlots();
        add(new JScrollPane(slotPanel), BorderLayout.CENTER);

        // Sidebar / Controls
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        controls.setBackground(StyleConstants.PANEL_BG);
        controls.setPreferredSize(new Dimension(300, 0));
        controls.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel ctrlTitle = new JLabel("SECURITY CONTROLS");
        ctrlTitle.setForeground(StyleConstants.ACCENT_GOLD);
        ctrlTitle.setFont(StyleConstants.BODY_FONT);
        controls.add(ctrlTitle);
        controls.add(Box.createVerticalStrut(20));

        JTextField plateField = createStyledTextField("Plate Number");
        JTextField ownerField = createStyledTextField("Owner Name");
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"REGULAR", "VIP"});
        
        JButton checkInBtn = createStyledButton("CHECK IN", StyleConstants.SNAKE_GREEN);
        checkInBtn.addActionListener(e -> {
            String plate = plateField.getText();
            String owner = ownerField.getText();
            String type = (String) typeBox.getSelectedItem();

            if (!plate.isEmpty() && !owner.isEmpty()) {
                // Potential Security Flaw: No input sanitization (SQLi risk in DB layer)
                Vehicle v = new Vehicle(plate, owner, type) {};
                ParkingSlot slot = manager.parkVehicle(v);
                if (slot != null) {
                    statusLabel.setText("Status: Parked at Slot " + slot.getSlotId());
                    refreshSlots();
                } else {
                    statusLabel.setText("Status: No slots available!");
                }
            }
        });

        controls.add(plateField);
        controls.add(Box.createVerticalStrut(10));
        controls.add(ownerField);
        controls.add(Box.createVerticalStrut(10));
        controls.add(typeBox);
        controls.add(Box.createVerticalStrut(20));
        controls.add(checkInBtn);

        statusLabel = new JLabel("Status: Ready");
        statusLabel.setForeground(Color.GRAY);
        controls.add(Box.createVerticalGlue());
        controls.add(statusLabel);

        add(controls, BorderLayout.EAST);
    }

    private void refreshSlots() {
        slotPanel.removeAll();
        for (ParkingSlot slot : manager.getSlots()) {
            JPanel p = new JPanel(new BorderLayout());
            p.setBackground(slot.isOccupied() ? Color.DARK_GRAY : StyleConstants.PANEL_BG);
            p.setBorder(BorderFactory.createLineBorder(slot.isOccupied() ? Color.RED : StyleConstants.SNAKE_GREEN, 1));
            
            JLabel id = new JLabel("SLOT " + slot.getSlotId(), SwingConstants.CENTER);
            id.setForeground(Color.WHITE);
            p.add(id, BorderLayout.CENTER);

            if (slot.isOccupied()) {
                JButton release = new JButton("OUT");
                release.setFont(new Font("Arial", Font.PLAIN, 10));
                release.addActionListener(e -> {
                    double fee = manager.releaseVehicle(slot.getSlotId());
                    statusLabel.setText("Status: Paid RM" + String.format("%.2f", fee));
                    refreshSlots();
                });
                p.add(release, BorderLayout.SOUTH);
            }

            slotPanel.add(p);
        }
        slotPanel.revalidate();
        slotPanel.repaint();
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        tf.setBackground(Color.BLACK);
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), placeholder));
        return tf;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        b.setBackground(bg);
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return b;
    }
}
