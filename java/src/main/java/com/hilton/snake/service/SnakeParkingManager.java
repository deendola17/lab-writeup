package com.hilton.snake.service;

import com.hilton.snake.model.ParkingSlot;
import com.hilton.snake.model.Vehicle;
import com.hilton.snake.util.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hilton Snake Parking Manager Service.
 * [VULNERABILITY] Insecure Deserialization of Object Streams.
 */
public class SnakeParkingManager {
    private List<ParkingSlot> slots;
    private DatabaseManager db;
    private PaymentProcessor payment;

    public SnakeParkingManager() {
        this.slots = new ArrayList<>();
        this.db = new DatabaseManager();
        this.payment = new PaymentProcessor();
        initializeSlots();
        db.initDatabase();
    }

    private void initializeSlots() {
        for (int i = 1; i <= 10; i++) slots.add(new ParkingSlot(i, "REGULAR"));
        for (int i = 11; i <= 15; i++) slots.add(new ParkingSlot(i, "VIP"));
    }

    public ParkingSlot parkVehicle(Vehicle vehicle) {
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied() && slot.getSlotType().equals(vehicle.getVehicleType())) {
                slot.park(vehicle);
                db.logAction("Vehicle Parked: " + vehicle.getPlateNumber());
                return slot;
            }
        }
        return null;
    }

    public double releaseVehicle(int slotId) {
        for (ParkingSlot slot : slots) {
            if (slot.getSlotId() == slotId && slot.isOccupied()) {
                double fee = payment.calculateFee(slot.getParkedVehicle().getEntryTime().getTime());
                String plate = slot.getParkedVehicle().getPlateNumber();
                slot.release();
                db.logAction("Vehicle Released: " + plate + " (Fee: " + fee + ")");
                return fee;
            }
        }
        return -1;
    }

    public List<ParkingSlot> getSlots() { return slots; }

    /**
     * [VULNERABILITY] Insecure Deserialization.
     * Reading an object directly from a stream without validation.
     */
    public void loadState(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            // FINDING: Insecure Deserialization
            Object obj = in.readObject();
            Logger.info("State loaded successfully.");
        } catch (Exception e) {
            Logger.error("Failed to load state: " + e.getMessage());
        }
    }

    public void saveState(String filePath, Object state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(state);
            Logger.info("State saved successfully.");
        } catch (Exception e) {
            Logger.error("Failed to save state.");
        }
    }
}
