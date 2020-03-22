package com.aquarian.drivers.util;

import android.app.Application;

public class GlobalVariables extends Application {

    private String driverID;
    private String driverFirstname;
    private String driverLastConnection;
    private String vehicleID;

    public String getVehicleID() { return vehicleID; }

    public void setVehicleID(String vehicleID) { this.vehicleID = vehicleID; }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getDriverFirstname() {
        return driverFirstname;
    }

    public void setDriverFirstname(String driverFirstname) { this.driverFirstname = driverFirstname; }

    public String getDriverLastConnection() { return driverLastConnection; }

    public void setDriverLastConnection(String driverLastConnection) { this.driverLastConnection = driverLastConnection; }
}
