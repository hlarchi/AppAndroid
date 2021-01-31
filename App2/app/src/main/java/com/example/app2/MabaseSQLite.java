package com.example.app2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/*** Inspired by zemmari on 14/11/16.*/

public class MabaseSQLite extends SQLiteOpenHelper {

    private final static String CREATE_TABLE =
            "create table site(" +
                    "numero integer primary key autoincrement," +
                    "url varchar(50)," +
                    "note int);";

    public MabaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table site;");
    }
}