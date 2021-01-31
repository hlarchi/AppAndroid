package com.example.app2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BDDSite {
    private final static int VERSION_BDD = 1;
    private SQLiteDatabase bdd;
    private MabaseSQLite mabaseSQLite;

    public BDDSite(Context context){
        mabaseSQLite = new MabaseSQLite(context,"Site",null,VERSION_BDD);
    }

    public void open(){
        bdd = mabaseSQLite.getWritableDatabase();
        if (bdd == null)
            System.out.println("La base n'existe pas\n");
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getBdd(){
        return bdd;
    }

    public long insertSite(Site site){
        ContentValues values = new ContentValues();
        values.put("url", site.getUrl());
        values.put("note", site.getNote());
        System.out.print("bonjour la bdd marche bien et insere "+site.getUrl()+" ratato "+site.getNote()+"\n");
        return bdd.insert("site",null, values);
    }

    public List<String> getSites(){
        String selectQuery = "SELECT * FROM site WHERE note > 1 ORDER BY note";
        Cursor cursor = bdd.rawQuery(selectQuery, null);
        List<String> url_array = new ArrayList<String>();
        while(cursor.moveToNext()){
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String rating = cursor.getString(cursor.getColumnIndex("note"));
            url_array.add(url + " Note : " + rating + "*");
        }
        return url_array;
    }
}
