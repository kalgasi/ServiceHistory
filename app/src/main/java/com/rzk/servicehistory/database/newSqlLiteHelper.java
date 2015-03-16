package com.rzk.servicehistory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lenovo on 12/03/2015.
 */
public class newSqlLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_SERVICE="service";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SERVICE_NAME="service_name";
    public static final String COLUMN_SERVICE_DATE="service_date";
    public static final String COLUMN_SERVICE_SPAREPART="service_sparepart";
    public static final String COLUMN_SERVICE_INFO="info";
    private static final String DATABASE_NAME = "service.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table "
            + TABLE_SERVICE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SERVICE_NAME
            + " text not null, "+COLUMN_SERVICE_DATE+" text not null, "+ COLUMN_SERVICE_SPAREPART
            + " text, "+COLUMN_SERVICE_INFO+" text);";


    public newSqlLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(newSqlLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICE);
        onCreate(db);

    }
}