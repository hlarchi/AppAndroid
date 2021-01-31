package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getContact();
    }

    public void getContact() {
        List<String> contacts = new ArrayList<String>();
        SharedPreferences contactPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = contactPref.edit();
        //Intent intent = getIntent();
        //intent.setClassName(com.example.app2, MainActivity.class);
        ContentResolver contentResolver = this.getContentResolver(); // pour acces au données du tel
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE,
                        ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
        if (cursor == null) {
            Log.d("recup", "cursor null");
        } else {
            ListView contact = (ListView) findViewById(R.id.contact);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contacts.add(name + " : " + number);
            }
            editor.putString("contact", String.valueOf(contacts));
            editor.commit();
            String pref = contactPref.getString("contact", null);
            Log.e("CON", pref);
            //intent.putExtra("contact", String.valueOf(contacts));
            contact.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts));
            cursor.close();
        }
    }
}