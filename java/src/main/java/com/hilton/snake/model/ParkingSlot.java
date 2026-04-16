package com.hilton.snake.model;

/**
 * High-end Parking Slot representation.
 */
public class ParkingSlot {
    private int slotId;
    private String slotType; // VIP, Regular, EV
    private boolean isOccupied;
    private Vehicle parkedVehicle;

    public ParkingSlot(int slotId, String slotType) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.isOccupied = false;
    }

    public int getSlotId() { return slotId; }
    public String getSlotType() { return slotType; }
    public boolean isOccupied() { return isOccupied; }
    public Vehicle getParkedVehicle() { return parkedVehicle; }

    public void park(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
        this.isOccupied = true;
    }

    public void release() {
        this.parkedVehicle = null;
        this.isOccupied = false;
    }
}
