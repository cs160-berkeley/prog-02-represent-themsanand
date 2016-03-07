package com.example.cs169_au.represent;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;

public class Chinton extends Activity implements MessageApi.MessageListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinton);
        Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
        sendIntent.putExtra("From", "Chinton");
        startService(sendIntent);
    }


    public void nButton(View v) {
        Intent myIntent;
        Bundle extras = getIntent().getExtras();
        String zip = "Alameda";
        if (extras != null) {
            zip = extras.getString("zipcode");
        }
        switch(v.getId())
        {
            case R.id.goForth:
                myIntent = new Intent(Chinton.this, Rump.class);
                myIntent.putExtra("zipcode", zip);
                startActivity(myIntent);
                break;
            case R.id.goBack:
                myIntent = new Intent(Chinton.this, Sandy.class);
                myIntent.putExtra("zipcode", zip);
                startActivity(myIntent);
                break;
        }

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("Checking Chinton", "onMessageReceived");
        Intent startIntent = new Intent(this, MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent.putExtra("zipcode", messageEvent.getData());
        startActivity(startIntent);
    }
}
