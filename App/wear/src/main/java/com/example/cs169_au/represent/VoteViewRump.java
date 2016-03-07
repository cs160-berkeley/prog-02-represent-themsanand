package com.example.cs169_au.represent;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;

public class VoteViewRump extends Activity implements MessageApi.MessageListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_view_rump);
        Bundle extras = getIntent().getExtras();
        String zip = "Alameda";
        if (extras != null) {
            zip = extras.getString("zipcode");
        }
        ((TextView)findViewById(R.id.county)).setText(zip);
    }


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("Checking VV", "onMessageReceived");
        Intent startIntent = new Intent(this, MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent.putExtra("zipcode", messageEvent.getData());
        startActivity(startIntent);
    }
}
