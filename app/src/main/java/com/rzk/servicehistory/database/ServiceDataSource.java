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
    private String allColums[]={newSqlLiteHelper.COLUMN_ID,newSqlLiteHelper.COLUMN_SERVICE_NAME,
    newSqlLiteHelper.COLUMN_SERVICE_DATE,newSqlLiteHelper.COLUMN_SERVICE_SPAREPART
    ,newSqlLiteHelper.COLUMN_SERVICE_INFO};

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
        long insertId=database.insert(newSqlLiteHelper.TABLE_SERVICE,null,values);

        Cursor cursor=database.query(newSqlLiteHelper.TABLE_SERVICE,allColums
        ,newSqlLiteHelper.COLUMN_ID + " = "+insertId,null,null,null,null);

        cursor.moveToFirst();
        cursor.close();

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
        }

        return  serviceData;
    }

    public List<ServiceData> getAllServiceHistory(){
        List<ServiceData> serviceDatas= new ArrayList<ServiceData>();

        Cursor cursor= database.query(newSqlLiteHelper.TABLE_SERVICE,allColums,
                null,null,null,null,null);
        cursor.moveToLast();

        while(!cursor.isBeforeFirst()){
            ServiceData serviceData=new ServiceData();
            serviceData.setServiceName(cursor.getString(1));
            serviceData.setServiceDate(cursor.getString(2));
            serviceData.setServiceSparePart(cursor.getString(3));
            serviceData.setServiceInfo(cursor.getString(4));

            serviceDatas.add(serviceData);
            cursor.moveToPrevious();
        }

        cursor.close();

        return serviceDatas;
    }


}
