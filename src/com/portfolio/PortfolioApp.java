package com.portfolio;

import com.portfolio.ui.PortfolioManagementUI;
import javax.swing.*;

/**
 * Main entry point for the Portfolio Management System application.
 */
public class PortfolioApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for better appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new PortfolioManagementUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
