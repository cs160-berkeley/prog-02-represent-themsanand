package com.example.cs169_au.represent;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;

public class Sandy extends Activity implements MessageApi.MessageListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandy);
        Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService1.class);
        startService(sendIntent);
    }

    public void nButton(View v) {
        Bundle extras = getIntent().getExtras();
        String zip = "Alameda";
        if (extras != null) {
            zip = extras.getString("zipcode");
        }
        Log.d("Checking Sandy", "nButton");
        Intent newIntent = new Intent(Sandy.this, Chinton.class);
        newIntent.putExtra("zipcode", zip);
        startActivity(newIntent);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("Checking Sandy", "onMessageReceived");
        Intent startIntent = new Intent(this, MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent.putExtra("zipcode", messageEvent.getData());
        startActivity(startIntent);
    }
}
