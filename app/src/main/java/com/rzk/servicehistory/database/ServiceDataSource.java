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

        Cursor cursor=database.query(newSqlLiteHelper.TABLE_SERVICE,allColums
        ,newSqlLiteHelper.COLUMN_ID + " = "+insertId,null,null,null,null);

        cursor.moveToFirst();
        cursor.close();

    }

    public void createVehicleData(VehicleData vehicleData){
        ContentValues values=new ContentValues();
        values.put(newSqlLiteHelper.COLUMN_VEHICLE_ID,vehicleData.getVehicleId());
        values.put(newSqlLiteHelper.COLUMN_VEHICLE_NAME,vehicleData.getVehicleName());
        values.put(newSqlLiteHelper.COLUMN_VEHICLE_DATA,vehicleData.getVehicleData());
        values.put(newSqlLiteHelper.COLUMN_VEHICLE_LAST_SERVICE_DATE,vehicleData.getVehicleLastServiceDate());
        long insertId=database.insert(newSqlLiteHelper.TABLE_VEHICLE,null,values);


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
        values.put(newSqlLiteHelper.COLUMN_VEHICLE_LAST_SERVICE_DATE,vehicleData.getVehicleLastServiceDate());
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
        System.out.println("Service "+serviceData.getServiceName()+ id+" deleted");
        database.delete(newSqlLiteHelper.TABLE_SERVICE,newSqlLiteHelper.COLUMN_ID+" = "+ id,null);
    }


}
