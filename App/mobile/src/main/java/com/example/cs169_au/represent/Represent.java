package com.example.cs169_au.represent;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class Represent extends AppCompatActivity {

    private GoogleApiClient mApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                })
                .build();

        setContentView(R.layout.activity_represent);
        final EditText handleField = (EditText) findViewById(R.id.ZIPInput);
        final Button zipbtn = (Button) findViewById(R.id.ZIPSearchButton);
        final Button currbtn = (Button) findViewById(R.id.CurrentLocationButton);

        zipbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!(((handleField.getText()).toString()).isEmpty())) {
                    String zipcode = handleField.getText().toString();
                    passToCongressional(zipcode);
                }
            }
        });
        currbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String zipcode = getZipCode();
                passToCongressional(zipcode);
            }
        });
    }

    public String getZipCode() {
        return "94709";
    }

    public void passToCongressional(String zipcode) {
        Intent myReprIntent = new Intent(this, Congressional.class);
        onStartCommand(getIntent(), zipcode);
        myReprIntent.putExtra("zipcode", zipcode);
        startActivity(myReprIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_represent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onStartCommand(Intent intent, final String num) {
        // Which cat do we want to feed? Grab this info from INTENT
        // which was passed over when we called startService
        Bundle extras = intent.getExtras();

        // Send the message with the cat name
        mApiClient.connect();

        //now that you're connected, send a massage with the cat name


        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mApiClient).await();
                for (Node node : nodes.getNodes()) {
                    //we find 'nodes', which are nearby bluetooth devices (aka emulators)
                    //send a message for each of these nodes (just one, for an emulator)
                    Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), "zipcode", num.getBytes());
                    //4 arguments: api client, the node ID, the path (for the listener to parse),
                    //and the message itself (you need to convert it to bytes.)
                }
            }
        }).start();
    }

}