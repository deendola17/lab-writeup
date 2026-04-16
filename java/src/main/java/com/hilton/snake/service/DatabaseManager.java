package com.hilton.snake.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Hilton Snake Parking Database Manager.
 * [VULNERABILITY] Hardcoded Credentials & SQL Injection.
 */
public class DatabaseManager {
    
    // CRITICAL FINDING: Hardcoded Credentials
    private static final String DB_USER = "snake_admin";
    private static final String DB_PASS = "Hilton@Snake2026!"; 
    private static final String DB_URL = "jdbc:h2:mem:snakeparking;DB_CLOSE_DELAY=-1";

    public Connection getConnection() {
        try {
            // Using H2 for simulation
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void initDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS system_logs (id INT AUTO_INCREMENT, message VARCHAR(255))");
            stmt.execute("CREATE TABLE IF NOT EXISTS users (username VARCHAR(50), password VARCHAR(50))");
            stmt.execute("INSERT INTO users VALUES ('admin', 'p@ssword123')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * [VULNERABILITY] SQL Injection via String Concatenation.
     */
    public void logAction(String message) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            // Finding: SQL Injection
            String sql = "INSERT INTO system_logs (message) VALUES ('" + message + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            // Improper Error Handling: printing stack trace
            e.printStackTrace(); 
        }
    }
}
