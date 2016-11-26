package com.egco428.a23264;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;



public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMMENT = "comments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LATITUDE = "latitude";


    private static final String DATABASE_NAME = "comments.db"; // file Database Name
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMMENT + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_USERNAME + " text not null, " + COLUMN_PASSWORD + " text not null, "+ COLUMN_LONGITUDE + " text not null, "+ COLUMN_LATITUDE
            + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE); // execute SQL statement
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENT);
        onCreate(db);
    }
}
