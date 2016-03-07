package com.example.cs169_au.represent;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.wearable.view.GridViewPager;
import android.util.Log;

import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;

public class MainActivity extends Activity implements MessageApi.MessageListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        String zip = "Alameda";
        if (extras != null) {
            zip = extras.getString("zipcode");
        }
        Intent newIntent = new Intent(this, Sandy.class);
        newIntent.putExtra("zipcode", zip);
        startActivity(newIntent);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("Checking MAIN", "onMessageReceived");
        Intent startIntent = new Intent(this, MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent.putExtra("zipcode", messageEvent.getData());
        startActivity(startIntent);
    }
}
