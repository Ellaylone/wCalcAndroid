package com.example.ellaylone.waitcalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ellaylone on 09.04.17.
 */

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DATES (_id INTEGER PRIMARY KEY AUTOINCREMENT, DATE_VALUE TEXT, DATE_TYPE INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DATES;");
        onCreate(db);
    }
}
