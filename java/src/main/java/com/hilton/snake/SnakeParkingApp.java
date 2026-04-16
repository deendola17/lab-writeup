package com.hilton.snake;

import com.hilton.snake.ui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main Application Entry for Snake Parking Hilton Hotel.
 * Premium Edition with intentional vulnerabilities for security training.
 */
public class SnakeParkingApp {
    public static void main(String[] args) {
        // Set Look and Feel to System if possible, or stay with Swing default
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
