package com.rzk.servicehistory.database;

/**
 * Created by lenovo on 12/03/2015.
 */
public class ServiceData {
    private String serviceName;
    private String serviceDate;
    private String serviceSparePart;
    private String serviceInfo;
    private String vehicleId;
    private long id;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceSparePart() {
        return serviceSparePart;
    }

    public void setServiceSparePart(String serviceSparePart) {
        this.serviceSparePart = serviceSparePart;
    }

    public String getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(String serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString() {
        return serviceDate + " " + serviceName;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
