package com.rzk.servicehistory.database;

/**
 * Created by lenovo on 12/03/2015.
 */
public class ServiceData {
    private String ServiceName;
    private String ServiceDate;
    private String ServiceSparePart;
    private String ServiceInfo;
    private long id;

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getServiceDate() {
        return ServiceDate;
    }

    public void setServiceDate(String serviceDate) {
        ServiceDate = serviceDate;
    }

    public String getServiceSparePart() {
        return ServiceSparePart;
    }

    public void setServiceSparePart(String serviceSparePart) {
        ServiceSparePart = serviceSparePart;
    }

    public String getServiceInfo() {
        return ServiceInfo;
    }

    public void setServiceInfo(String serviceInfo) {
        ServiceInfo = serviceInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString(){
        return ServiceDate+" "+ServiceName;
    }
}
