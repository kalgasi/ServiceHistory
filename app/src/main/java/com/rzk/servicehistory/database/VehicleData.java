package com.rzk.servicehistory.database;

/**
 * Created by lenovo on 27/03/2015.
 */
public class VehicleData {
    private String VehicleId;
    private String VehicleName;
    private String VehicleData;
    private String VehicleLastServiceDate;

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) {
        VehicleId = vehicleId;
    }

    public String getVehicleName() {
        return VehicleName;
    }

    public void setVehicleName(String vehicleName) {
        VehicleName = vehicleName;
    }

    public String getVehicleData() {
        return VehicleData;
    }

    public void setVehicleData(String vehicleData) {
        VehicleData = vehicleData;
    }

    public String toString(){
        return (VehicleId+" "+VehicleName);
    }

    public String getVehicleLastServiceDate() {
        return VehicleLastServiceDate;
    }

    public void setVehicleLastServiceDate(String vehicleLastServiceDate) {
        VehicleLastServiceDate = vehicleLastServiceDate;
    }
}
