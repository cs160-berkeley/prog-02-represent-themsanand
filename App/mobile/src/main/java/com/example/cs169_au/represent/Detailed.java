package com.example.cs169_au.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Detailed extends AppCompatActivity {

    private ArrayList<String> committees = new ArrayList<String>();
    private ArrayList<String> billsList = new ArrayList<String>();
    private ArrayList<String> billsListT = new ArrayList<String>();

    private String bioguide_id;
    private final String apikey = "01f8c1619ea045a0b22cd45d55459d62";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent i = getIntent();
        Bundle extr = i.getExtras();
        Congressperson cp = extr.getParcelable("detailed");
        TextView nameV = (TextView) findViewById(R.id.name);
        nameV.setText(cp.theirName);
        TextView endTerm = (TextView) findViewById(R.id.termend);
        endTerm.setText("Term ends " + cp.termEnd);
        bioguide_id = cp.bioid;


        getCommittees(new Runnable() {
            @Override
            public void run() {


                TextView commitee1 = (TextView) findViewById(R.id.c1);

                commitee1.setText(committees.get(0));

                TextView commitee2 = (TextView) findViewById(R.id.c2);

                commitee2.setText(committees.get(1));

                TextView commitee3 = (TextView) findViewById(R.id.c3);

                commitee3.setText(committees.get(2));

                TextView commitee4 = (TextView) findViewById(R.id.c4);

                commitee4.setText(committees.get(3));

                TextView commitee5 = (TextView) findViewById(R.id.c5);

                commitee5.setText(committees.get(4));


                //ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(Detailed.this, android.R.layout.simple_list_item_2, committees);

                //ListView lvCommitees = (ListView) findViewById(R.id.listView);

                //lvCommitees.setAdapter(itemsAdapter);

            }


        });

        getBills(new Runnable() {
            @Override
            public void run() {

                TextView bill1 = (TextView) findViewById(R.id.b1);

                bill1.setText(billsList.get(0));

                TextView bill2 = (TextView) findViewById(R.id.b2);

                bill2.setText(billsList.get(1));

                TextView bill3 = (TextView) findViewById(R.id.b3);

                bill3.setText(billsList.get(2));

                TextView bill4 = (TextView) findViewById(R.id.b4);

                bill4.setText(billsList.get(3));

                TextView bill5 = (TextView) findViewById(R.id.b5);

                bill5.setText(billsList.get(4));

                //ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(Detailed.this, android.R.layout.simple_list_item_2, billsList);

                //ListView lvCommitees = (ListView) findViewById(R.id.listView);

                //lvCommitees.setAdapter(itemsAdapter);

            }


        });
    }

    public void getCommittees(final Runnable runnable) {



        String commURL = "https://congress.api.sunlightfoundation.com/committees?member_ids=" + bioguide_id + "&apikey=" + apikey;


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(commURL)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call request, IOException e) {
                try {
                    throw new IOException();
                } catch (IOException g) {
                }
            }

            @Override
            public void onResponse(Call c, Response response) throws IOException {
                try {
                    String data = response.body().string();

                    JSONObject lData = new JSONObject(data);

                    String lInfo = lData.getString("results");

                    JSONArray jsA = new JSONArray(lInfo);


                    for (int a = 0; a < jsA.length(); a++) {
                        JSONObject jsO = (JSONObject) jsA.get(a);
                        committees.add(jsO.getString("name"));
                    }

                    if (response.isSuccessful()) {
                        runOnUiThread(runnable);
                    } else {
                    }

                } catch (Exception e) {
                }
            }

        });
    }

    public void getBills(final Runnable runnable) {


        final String billURL = "https://congress.api.sunlightfoundation.com/bills?sponsor_id=" + bioguide_id + "&apikey=" + apikey;

        String commURL = "https://congress.api.sunlightfoundation.com/committees?member_ids=" + bioguide_id + "&apikey=" + apikey;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(billURL)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call request, IOException e) {
                try {
                    throw new IOException();
                } catch (IOException g) {
                }
            }

            @Override
            public void onResponse(Call c, Response response) throws IOException {
                try {
                    String data = response.body().string();

                    JSONObject lData = new JSONObject(data);

                    String lInfo = lData.getString("results");

                    JSONArray jsA = new JSONArray(lInfo);


                    for (int a = 0; a < jsA.length(); a++) {
                        String title;
                        if((((JSONObject) (jsA.get(a))).getString("short_title")) == "null") {
                            title = (((JSONObject) (jsA.get(a))).getString("official_title"));
                            int tL = title.length();
                            title = title.substring(0, Math.min(tL, 30));
                            if(tL > 30) {
                                title += "...";
                            }

                        } else {
                            title = (((JSONObject) (jsA.get(a))).getString("short_title"));
                        }
                        billsList.add(title + " (Introduced " + ((JSONObject) (jsA.get(a))).getString("introduced_on") + ")");

                    }



                    if (response.isSuccessful()) {
                        runOnUiThread(runnable);
                    } else {
                    }

                } catch (Exception e) {
                }
            }

        });

    }

}
