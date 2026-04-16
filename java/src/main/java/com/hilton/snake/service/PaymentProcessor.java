package com.hilton.snake.service;

import java.security.MessageDigest;
import java.util.Random;

/**
 * Hilton Snake Payment Processor.
 * [VULNERABILITY] Weak Cryptography & Hardcoded Keys.
 */
public class PaymentProcessor {

    // CRITICAL FINDING: Hardcoded Encryption Key
    private static final String SECRET_KEY = "SNAKE_HILTON_2026_PRI_KEY";

    /**
     * [VULNERABILITY] Weak Hashing Algorithm (MD5).
     */
    public String hashTransaction(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(data.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "ERROR_HASHING";
        }
    }

    /**
     * [VULNERABILITY] Predictable Random Number for Transaction ID.
     */
    public String generateTransactionId() {
        Random rand = new Random(); // Should use SecureRandom
        int id = rand.nextInt(999999);
        return "TXN-" + String.format("%06d", id);
    }

    public double calculateFee(long entryTimeMillis) {
        long durationMillis = System.currentTimeMillis() - entryTimeMillis;
        long hours = durationMillis / (1000 * 60 * 60);
        if (hours < 1) return 10.0; // Flat RM10 for first hour
        return 10.0 + (hours * 5.0); // RM5 every subsequent hour
    }
}
