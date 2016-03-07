package com.example.cs169_au.represent;

import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Congressional extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional);
        final Button seeMoreButton1 = (Button) findViewById(R.id.SEEMORE1);
        final Button seeMoreButton2 = (Button) findViewById(R.id.SEEMORE2);

        seeMoreButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                passToDetail("Sanders");
            }
        });

    }


    public void passToDetail(String id) {
            Intent myReprIntent = new Intent(this, Detailed.class);
            myReprIntent.putExtra("Person", id);
            startActivity(myReprIntent);
    }


}

