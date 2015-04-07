package com.rzk.servicehistory.database;

/**
 * Created by lenovo on 07/04/2015.
 */
public class ServiceReminder {
    private String VehicleId;
    private String Date;
    private String info;
    private String status;

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) {
        VehicleId = vehicleId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString(){
        return getVehicleId()+" "+getDate()+" "+getInfo();
    }
}
