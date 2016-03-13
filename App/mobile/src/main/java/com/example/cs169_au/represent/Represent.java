package com.example.cs169_au.represent;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetui.UserTimeline;

import io.fabric.sdk.android.Fabric;

public class Represent extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "STTw4x9gW3rHCLlvubYbuksWt";
    private static final String TWITTER_SECRET = "hPx6raZWEQnDSgoJPuz26RQO8FG6ArcvCgNtWlHnlhvqbMl0Eb";


    private GoogleApiClient mApiClient;
    private GoogleApiClient mGoogleApiClient;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

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

        UserTimeline userTimeline = new UserTimeline.Builder().screenName("fabric").build();
        

        setContentView(R.layout.activity_represent);
        final EditText handleField = (EditText) findViewById(R.id.ZIPInput);
        final Button zipbtn = (Button) findViewById(R.id.ZIPSearchButton);
        final Button currbtn = (Button) findViewById(R.id.CurrentLocationButton);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

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
                getZipCode();
            }
        });
    }

    public void getZipCode() {
        Intent myReprIntent = new Intent(this, Congressional.class);
        //onStartCommand(getIntent(), zipcode);
        myReprIntent.putExtra("lat", Double.toString(latitude));
        myReprIntent.putExtra("long", Double.toString(longitude));
        startActivity(myReprIntent);
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


    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.e("TAG", "Location services connected.");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            // Blank for a moment...
        }
        else {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        };

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("TAG", "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("TAG", "Location services failed. Please reconnect.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
}