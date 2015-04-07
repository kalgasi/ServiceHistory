package com.rzk.servicehistory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lenovo on 12/03/2015.
 */
public class newSqlLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_VEHICLE="VEHICLE";
    public static final String COLUMN_VEHICLE_ID="vehicle_id";
    public static final String COLUMN_VEHICLE_NAME="vehicle_name";
    public static final String COLUMN_VEHICLE_DATA="vehicle_data";
    public static final String COLUMN_VEHICLE_LAST_SERVICE_DATE="vehicle_last_service_date";

    private static final String DATABASE_CREATE_TABLE_VEHICLE="create table "
            + TABLE_VEHICLE+ "("+COLUMN_VEHICLE_ID
            + " text primary key, "+COLUMN_VEHICLE_NAME
            + " text, "+COLUMN_VEHICLE_DATA
            + " text, "+COLUMN_VEHICLE_LAST_SERVICE_DATE
            + " text);";

    public static final String TABLE_SERVICE="service";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SERVICE_NAME="service_name";
    public static final String COLUMN_SERVICE_DATE="service_date";
    public static final String COLUMN_SERVICE_SPAREPART="service_sparepart";
    public static final String COLUMN_SERVICE_INFO="info";
    private static final String DATABASE_NAME = "service.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE_TABLE_SERVICE = "create table "
            + TABLE_SERVICE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SERVICE_NAME
            + " text not null, "+COLUMN_SERVICE_DATE+" text not null, "+ COLUMN_SERVICE_SPAREPART
            + " text, "+COLUMN_SERVICE_INFO+" text, "+COLUMN_VEHICLE_ID+ " text);";

    public static final String TABLE_REMINDER="reminder";
    public static final String COLUMN_REMINDER_STATUS="status";
    public static final String COLUMN_REMINDER_DETAIL="info";
    public static final String COLUMN_REMINDER_DATE="date";
    private static final String DATABASE_CREATE_TABLE_REMINDER="create table "
            + TABLE_REMINDER + "("+COLUMN_VEHICLE_ID
            + " text, "+COLUMN_REMINDER_DATE
            + " text, "+COLUMN_SERVICE_INFO
            + " text, "+COLUMN_REMINDER_DETAIL
            + " text, "+ COLUMN_REMINDER_STATUS
            + " text);";

    public newSqlLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE_VEHICLE);
        db.execSQL(DATABASE_CREATE_TABLE_SERVICE);
        db.execSQL(DATABASE_CREATE_TABLE_REMINDER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(newSqlLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICE);
        db.execSQL("DROP TABLE IF EXIST "+ TABLE_VEHICLE);
        db.execSQL("DROP TABLE IF EXIST "+ TABLE_REMINDER);
        onCreate(db);

    }
}
