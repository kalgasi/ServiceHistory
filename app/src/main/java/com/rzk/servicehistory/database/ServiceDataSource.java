package com.rzk.servicehistory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 12/03/2015.
 */
public class ServiceDataSource {
    private SQLiteDatabase database;
    private newSqlLiteHelper helper;
    private String AllVehicleColumn[]={newSqlLiteHelper.COLUMN_VEHICLE_ID,
            newSqlLiteHelper.COLUMN_VEHICLE_NAME,
    newSqlLiteHelper.COLUMN_VEHICLE_DATA,
    newSqlLiteHelper.COLUMN_VEHICLE_LAST_SERVICE_DATE};
    private String allColums[]={newSqlLiteHelper.COLUMN_ID,newSqlLiteHelper.COLUMN_SERVICE_NAME,
    newSqlLiteHelper.COLUMN_SERVICE_DATE,newSqlLiteHelper.COLUMN_SERVICE_SPAREPART
    ,newSqlLiteHelper.COLUMN_SERVICE_INFO,newSqlLiteHelper.COLUMN_VEHICLE_ID};
    private String reminderColumns[]={newSqlLiteHelper.COLUMN_VEHICLE_ID,newSqlLiteHelper.COLUMN_REMINDER_DATE,
    newSqlLiteHelper.COLUMN_REMINDER_DETAIL,newSqlLiteHelper.COLUMN_REMINDER_STATUS};

    public ServiceDataSource(Context context){
        helper=new newSqlLiteHelper(context);
    }
    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
    }

    public void createServiceData(ServiceData data){
        ContentValues values=new ContentValues();
        values.put(newSqlLiteHelper.COLUMN_SERVICE_NAME,data.getServiceName() );
        values.put(newSqlLiteHelper.COLUMN_SERVICE_DATE,data.getServiceDate() );
        values.put(newSqlLiteHelper.COLUMN_SERVICE_SPAREPART,data.getServiceSparePart() );
        values.put(newSqlLiteHelper.COLUMN_SERVICE_INFO,data.getServiceInfo() );
        values.put(newSqlLiteHelper.COLUMN_VEHICLE_ID,data.getVehicleId());
        long insertId=database.insert(newSqlLiteHelper.TABLE_SERVICE,null,values);

        Cursor cursor=database.query(newSqlLiteHelper.TABLE_SERVICE, allColums
                , newSqlLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        cursor.close();

    }

    public void createVehicleData(VehicleData vehicleData){
        ContentValues values=new ContentValues();
        values.put(newSqlLiteHelper.COLUMN_VEHICLE_ID,vehicleData.getVehicleId());
        values.put(newSqlLiteHelper.COLUMN_VEHICLE_NAME,vehicleData.getVehicleName());
        values.put(newSqlLiteHelper.COLUMN_VEHICLE_DATA, vehicleData.getVehicleData());
        values.put(newSqlLiteHelper.COLUMN_VEHICLE_LAST_SERVICE_DATE, vehicleData.getVehicleLastServiceDate());
        long insertId=database.insert(newSqlLiteHelper.TABLE_VEHICLE, null, values);


       // Cursor cursor=database.query(newSqlLiteHelper.TABLE_VEHICLE);
    }

    public List<VehicleData> getAllvehicleData(){

        List<VehicleData> vehicleDatas=new ArrayList<VehicleData>();
        Cursor cursor=database.query(newSqlLiteHelper.TABLE_VEHICLE,AllVehicleColumn ,null,null,null,null,null);
        cursor.moveToLast();
        while(!cursor.isBeforeFirst()){
            VehicleData vehicleData=new VehicleData();
            vehicleData.setVehicleId(cursor.getString(0));
            vehicleData.setVehicleName(cursor.getString(1));
            vehicleData.setVehicleData(cursor.getString(2));
            vehicleData.setVehicleLastServiceDate(cursor.getString(3));
            vehicleDatas.add(vehicleData);

            cursor.moveToPrevious();
        }
        cursor.close();
        return  vehicleDatas;
    }

    public ServiceData getLastServiceData(){
        ServiceData serviceData=new ServiceData();
        Cursor cursor = database.query(newSqlLiteHelper.TABLE_SERVICE,
                allColums, null, null, null, null, null);
        cursor.moveToLast();

        if(!cursor.isAfterLast()) {
            serviceData.setServiceName(cursor.getString(1));
            serviceData.setServiceDate(cursor.getString(2));
            serviceData.setServiceSparePart(cursor.getString(3));
            serviceData.setServiceInfo(cursor.getString(4));
            serviceData.setVehicleId(cursor.getString(5));
        }
        cursor.close();
        return  serviceData;
    }

    public ServiceData getLastServiceData(VehicleData vehicleData){
        ServiceData serviceData=new ServiceData();
        String order="date("+newSqlLiteHelper.COLUMN_SERVICE_DATE+ ") ASC";
        String selection=newSqlLiteHelper.COLUMN_VEHICLE_ID+" LIKE?";
        String[] arg={String.valueOf(vehicleData.getVehicleId())};
        Cursor cursor = database.query(newSqlLiteHelper.TABLE_SERVICE,
                allColums, selection, arg, null, null, order);
        cursor.moveToLast();

        if(!cursor.isAfterLast()) {
            serviceData.setServiceName(cursor.getString(1));
            serviceData.setServiceDate(cursor.getString(2));
            serviceData.setServiceSparePart(cursor.getString(3));
            serviceData.setServiceInfo(cursor.getString(4));
            serviceData.setVehicleId(cursor.getString(5));
        }
        cursor.close();
        return  serviceData;
    }

    public List<ServiceData> getAllServiceHistory(){
        List<ServiceData> serviceDatas= new ArrayList<ServiceData>();

        Cursor cursor= database.query(newSqlLiteHelper.TABLE_SERVICE,allColums,
                null,null,null,null,null);
        cursor.moveToLast();

        while(!cursor.isBeforeFirst()){
            ServiceData serviceData=new ServiceData();
            serviceData.setId(cursor.getLong(0));
            serviceData.setServiceName(cursor.getString(1));
            serviceData.setServiceDate(cursor.getString(2));
            serviceData.setServiceSparePart(cursor.getString(3));
            serviceData.setServiceInfo(cursor.getString(4));
            serviceData.setVehicleId(cursor.getString(5));

            serviceDatas.add(serviceData);
            cursor.moveToPrevious();
        }

        cursor.close();

        return serviceDatas;
    }

    public void updateVehicleList(VehicleData vehicleData){
        ContentValues values=new ContentValues();
        values.put(newSqlLiteHelper.COLUMN_VEHICLE_LAST_SERVICE_DATE, vehicleData.getVehicleLastServiceDate());
        String selection=newSqlLiteHelper.COLUMN_VEHICLE_ID+" LIKE?";
        String[] arg={String.valueOf(vehicleData.getVehicleId())};
        int number=database.update(newSqlLiteHelper.TABLE_VEHICLE,values,selection,arg);

    }

    public List<ServiceData> getAllServiceHistory(VehicleData vehicleData){
        List<ServiceData> serviceDatas= new ArrayList<ServiceData>();
        String order="date("+newSqlLiteHelper.COLUMN_SERVICE_DATE+ ") ASC";
        String selection=newSqlLiteHelper.COLUMN_VEHICLE_ID+" LIKE?";
        String[] arg={String.valueOf(vehicleData.getVehicleId())};
        Cursor cursor = database.query(newSqlLiteHelper.TABLE_SERVICE,
                allColums, selection, arg, null, null, order);
        cursor.moveToLast();
        while(!cursor.isBeforeFirst()){
            ServiceData serviceData=new ServiceData();
            serviceData.setId(cursor.getLong(0));
            serviceData.setServiceName(cursor.getString(1));
            serviceData.setServiceDate(cursor.getString(2));
            serviceData.setServiceSparePart(cursor.getString(3));
            serviceData.setServiceInfo(cursor.getString(4));
            serviceData.setVehicleId(cursor.getString(5));

            serviceDatas.add(serviceData);
            cursor.moveToPrevious();
        }

        cursor.close();
        return serviceDatas;
    }
    public void deleteDataService(ServiceData serviceData){
        long id=serviceData.getId();
        System.out.println("Service " + serviceData.getServiceName() + id + " deleted");
        database.delete(newSqlLiteHelper.TABLE_SERVICE, newSqlLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public void deleteVehicleData(VehicleData vehicleData){
        String vehicleId=vehicleData.getVehicleId();
        System.out.println(vehicleData.getVehicleName()+" data deleted");
        database.delete(newSqlLiteHelper.TABLE_VEHICLE, newSqlLiteHelper.COLUMN_VEHICLE_ID + " LIKE '" + vehicleId + "'", null);
        database.delete(newSqlLiteHelper.TABLE_SERVICE, newSqlLiteHelper.COLUMN_VEHICLE_ID + " LIKE '" + vehicleId + "'", null);
        database.delete(newSqlLiteHelper.TABLE_REMINDER,newSqlLiteHelper.COLUMN_VEHICLE_ID+ " LIKE '"+vehicleId+"'",null);
    }

    public boolean checkVehicleId(String vehicleId){
        Cursor cursor=database.query(newSqlLiteHelper.TABLE_VEHICLE, AllVehicleColumn, " lower(" +
                newSqlLiteHelper.COLUMN_VEHICLE_ID + ") LIKE lower('" + vehicleId + "')", null, null, null, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }

        //return false;
    }

    public void createServiceReminder(ServiceReminder serviceReminder){
        ContentValues contentValues=new ContentValues();
        contentValues.put(newSqlLiteHelper.COLUMN_VEHICLE_ID,serviceReminder.getVehicleId());
        contentValues.put(newSqlLiteHelper.COLUMN_REMINDER_DATE,serviceReminder.getDate());
        contentValues.put(newSqlLiteHelper.COLUMN_REMINDER_DETAIL,serviceReminder.getDetail());
        contentValues.put(newSqlLiteHelper.COLUMN_REMINDER_STATUS, serviceReminder.getStatus());
        long insertId=database.insert(newSqlLiteHelper.TABLE_REMINDER,null,contentValues);
    }

    public void deleteServiceReminder(ServiceReminder serviceReminder){
        String condition=newSqlLiteHelper.COLUMN_VEHICLE_ID+" LIKE '"+serviceReminder.getVehicleId()
                +"' AND "+newSqlLiteHelper.COLUMN_REMINDER_DATE+" LIKE '"+serviceReminder.getDate()
                +"' " +" AND "+newSqlLiteHelper.COLUMN_REMINDER_DETAIL+" LIKE '"
                +serviceReminder.getDetail()+"'";
        database.delete(newSqlLiteHelper.TABLE_REMINDER,condition,null);
    }

    public List<ServiceReminder> getAllServiceReminder(){
        List<ServiceReminder> serviceReminders=new ArrayList<ServiceReminder>();
        String order="date("+newSqlLiteHelper.COLUMN_REMINDER_DATE+ ") ASC";
        Cursor cursor = database.query(newSqlLiteHelper.TABLE_REMINDER,
                reminderColumns, null, null, null, null, order);
        cursor.moveToLast();
        while (!cursor.isBeforeFirst()){
            ServiceReminder serviceReminder=new ServiceReminder();
            serviceReminder.setVehicleId(cursor.getString(0));
            serviceReminder.setDate(cursor.getString(1));
            serviceReminder.setDetail(cursor.getString(2));
            serviceReminder.setStatus(cursor.getString(3));

            serviceReminders.add(serviceReminder);

            cursor.moveToPrevious();
        }

        return  serviceReminders;
    }

    public List<ServiceReminder> getAllServiceReminder(ServiceReminder serviceReminder){
        List<ServiceReminder> serviceReminders=new ArrayList<ServiceReminder>();
        String order="date("+newSqlLiteHelper.COLUMN_REMINDER_DATE+ ") ASC";
        Cursor cursor = database.query(newSqlLiteHelper.TABLE_REMINDER,
                reminderColumns, newSqlLiteHelper.COLUMN_VEHICLE_ID +" LIKE '"+
                        serviceReminder.getVehicleId()+"'", null, null, null, order);


        return  serviceReminders;
    }

    public int getCountServiceReminder(){
        int total=0;
        String query="select * from "+newSqlLiteHelper.TABLE_REMINDER;
        Cursor cursor=database.rawQuery(query, null);
        total=cursor.getCount();
        return total;
    }

    public String getVehicleName(String vehicleId){
        String name="";

        String query="select "+ newSqlLiteHelper.COLUMN_VEHICLE_NAME+" from "
                +newSqlLiteHelper.TABLE_VEHICLE+ " where "+newSqlLiteHelper.COLUMN_VEHICLE_ID
                + " LIKE '"+vehicleId+"'";
        Cursor cursor=database.rawQuery(query,null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast())
            name=cursor.getString(0);
        return name;
    }



}
