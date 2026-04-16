package com.hilton.snake.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Premium Vehicle Base Class for Hilton Snake Parking.
 * [SECURITY NOTE] This class implements Serializable which is prone to Insecure Deserialization
 * if not handled correctly.
 */
public abstract class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String plateNumber;
    private String ownerName;
    private Date entryTime;
    private String vehicleType;

    public Vehicle(String plateNumber, String ownerName, String vehicleType) {
        this.plateNumber = plateNumber;
        this.ownerName = ownerName;
        this.vehicleType = vehicleType;
        this.entryTime = new Date();
    }

    public String getPlateNumber() { return plateNumber; }
    public String getOwnerName() { return ownerName; }
    public Date getEntryTime() { return entryTime; }
    public String getVehicleType() { return vehicleType; }

    @Override
    public String toString() {
        return "[" + vehicleType + "] " + plateNumber + " - " + ownerName;
    }
}
