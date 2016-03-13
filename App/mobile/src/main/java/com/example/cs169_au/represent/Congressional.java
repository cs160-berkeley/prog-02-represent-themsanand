package com.example.cs169_au.represent;

import android.app.ListActivity;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.example.cs169_au.represent.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Congressional extends AppCompatActivity {

    private String mZipcode = null;
    private String mLat = null;
    private String mLong = null;

    public static final String TAG = "DEBUG TAG";

    private final String apikey = "01f8c1619ea045a0b22cd45d55459d62";

    public ArrayList<HashMap<String, String>> results = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional);

        //final Button seeMoreButton1 = (Button) findViewById(R.id.SEEMORE1);
        //final Button seeMoreButton2 = (Button) findViewById(R.id.SEEMORE2);



        Intent i = getIntent();
        Bundle extr = i.getExtras();
        if(extr.containsKey("zipcode")) {
            mZipcode = extr.getString("zipcode");
        } else {
            mLat = extr.getString("lat");
            mLong = extr.getString("long");
        }


        getLegislators(new Runnable() {
            @Override
            public void run() {
                ArrayList<Congressperson> lList = new ArrayList<Congressperson>();
                for (HashMap<String, String> legislator : results) {
                    lList.add(new Congressperson(legislator));
                }

                /**
                 *

                 <ScrollView
                 android:layout_width="match_parent"
                 android:layout_height="match_parent"
                 android:fadeScrollbars="false"
                 android:scrollbarStyle="outsideInset">

                 <LinearLayout
                 android:layout_width="wrap_content"
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:orientation="vertical">

                 <ScrollView
                 android:layout_height="wrap_content"
                 android:layout_width="200dp"
                 android:src="@drawable/rectangle"
                 android:fadeScrollbars="false"
                 android:orientation="vertical"
                 android:padding="20dp"
                 android:scrollbarStyle="outsideInset">


                 <LinearLayout
                 android:layout_width="wrap_content"
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:orientation="vertical">

                 <TextView
                 android:layout_height="wrap_content"
                 android:layout_width="wrap_content"
                 android:gravity="center"
                 android:fontFamily="Sans-Serif"
                 android:textSize="26dp"
                 android:text="Senator Sandy (I)">
                 </TextView>

                 <ImageView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:src="@drawable/berniesanders"/>

                 <TextView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Serif"
                 android:textSize="16dp"
                 android:text="bsandy2@gmail.com">
                 </TextView>

                 <TextView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Serif"
                 android:textSize="16dp"
                 android:text="bsandy.com">
                 </TextView>

                 <TextView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Serif"
                 android:textSize="16dp"
                 android:text="#Me For Prez">
                 </TextView>

                 <Button
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Serif"
                 android:background="@android:color/transparent"
                 android:textSize="16dp"
                 android:textColor="#0000FF"
                 android:id="@+id/SEEMORE1"
                 android:text="See More">
                 </Button>

                 </LinearLayout>
                 </ScrollView>

                 <ScrollView
                 android:layout_height="wrap_content"
                 android:layout_width="200dp"
                 android:src="@drawable/rectangle"
                 android:fadeScrollbars="false"
                 android:padding="20dp"
                 android:scrollbarStyle="outsideInset">

                 <LinearLayout
                 android:layout_width="wrap_content"
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:orientation="vertical">

                 <TextView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Sans-Serif"
                 android:textSize="22dp"
                 android:text="Senator Chinton (D)">
                 </TextView>

                 <ImageView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:src="@drawable/hillaryclinton"/>

                 <TextView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Serif"
                 android:textSize="16dp"
                 android:text="hchin@hotmail.com">
                 </TextView>

                 <TextView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Serif"
                 android:textSize="16dp"
                 android:text="iwasfirstlady.net">
                 </TextView>

                 <TextView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Serif"
                 android:textSize="16dp"
                 android:text="#Vote 4 Me">
                 </TextView>

                 <Button
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Serif"
                 android:background="@android:color/transparent"
                 android:textSize="16dp"
                 android:textColor="#0000FF"
                 android:id="@+id/SEEMORE2"
                 android:text="See More">
                 </Button>

                 </LinearLayout>
                 </ScrollView>

                 <ScrollView
                 android:layout_height="wrap_content"
                 android:layout_width="200dp"
                 android:src="@drawable/rectangle"
                 android:fadeScrollbars="false"
                 android:padding="20dp"
                 android:scrollbarStyle="outsideInset">

                 <LinearLayout
                 android:layout_width="wrap_content"
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:orientation="vertical">

                 <TextView
                 android:layout_height="wrap_content"
                 android:layout_width="wrap_content"
                 android:gravity="center"
                 android:fontFamily="Sans-Serif"
                 android:textSize="22dp"
                 android:text="Representative Rump (R)">
                 </TextView>

                 <ImageView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:src="@drawable/donaldtrump"/>

                 <TextView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Serif"
                 android:textSize="16dp"
                 android:text="oman@yahoo.com">
                 </TextView>

                 <TextView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Serif"
                 android:textSize="16dp"
                 android:text="omann.me">
                 </TextView>

                 <TextView
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Serif"
                 android:textSize="16dp"
                 android:text="#Something ridiculous">
                 </TextView>

                 <Button
                 android:layout_height="wrap_content"
                 android:layout_gravity="center_horizontal"
                 android:layout_width="wrap_content"
                 android:fontFamily="Serif"
                 android:background="@android:color/transparent"
                 android:textSize="16dp"
                 android:textColor="#0000FF"
                 android:id="@+id/SEEMORE3"
                 android:text="See More">
                 </Button>

                 </LinearLayout>
                 </ScrollView>

                 </LinearLayout>
                 </ScrollView>

                 */

                CongressAdapter cAdapter = new CongressAdapter(Congressional.this, lList);

                ListView lv = (ListView) findViewById(R.id.lvItems);

                lv.setAdapter(cAdapter);

                //ListAdapter itemsAdapter = new ArrayAdapter<String>(Congressional.this, android.R.layout.simple_list_item_1, lList);

                //ListView listView = (ListView) findViewById(R.id.lvItems);

                //listView.setAdapter(itemsAdapter);

            }


        });

        //seeMoreButton1.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View v) {
        //        passToDetail("Sanders");
        //   }
        //});

    }


    public void passToDetail(String id) {

            Intent myReprIntent = new Intent(this, Detailed.class);
            myReprIntent.putExtra("Person", id);
            startActivity(myReprIntent);
    }

    public void getLegislators(final Runnable runnable) {

        String legislatorsURL;

        if(mZipcode != null) {
            legislatorsURL = "https://congress.api.sunlightfoundation.com/legislators/locate?apikey=" + apikey + "&zip=" + mZipcode;
        } else {
            legislatorsURL = "https://congress.api.sunlightfoundation.com/legislators/locate?latitude=" + mLat + "&longitude=" + mLong + "&apikey=" + apikey;
        }


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(legislatorsURL)
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
                        HashMap<String, String> hm = new HashMap<String, String>();
                        JSONObject jsonPart = jsA.getJSONObject(a);
                        hm.put("bioguide_id", jsonPart.getString("bioguide_id"));
                        hm.put("first_name", jsonPart.getString("first_name"));
                        hm.put("last_name", jsonPart.getString("last_name"));
                        hm.put("party", jsonPart.getString("party"));
                        hm.put("title", jsonPart.getString("title"));
                        hm.put("oc_email", jsonPart.getString("oc_email"));
                        hm.put("phone", jsonPart.getString("phone"));
                        hm.put("office", jsonPart.getString("office"));
                        hm.put("website", jsonPart.getString("website"));
                        hm.put("term_end", jsonPart.getString("term_end"));
                        results.add(hm);

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

