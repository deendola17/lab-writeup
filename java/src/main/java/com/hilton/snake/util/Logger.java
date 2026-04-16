package com.hilton.snake.util;

import java.util.Date;

/**
 * Hilton Snake Custom Logger.
 * [VULNERABILITY] Improper Output Neutralization (Log Forgery).
 */
public class Logger {
    
    /**
     * [VULNERABILITY] Log Forgery.
     * User-controlled 'message' is directly appended to the logs.
     */
    public static void log(String level, String message) {
        String logEntry = String.format("[%s] [%s] %s\n", new Date().toString(), level, message);
        System.out.print(logEntry);
        // In a real system, this would write to a file, enabling log forgery.
    }

    public static void info(String msg) { log("INFO", msg); }
    public static void error(String msg) { log("ERROR", msg); }
    public static void debug(String msg) { log("DEBUG", msg); }
}
