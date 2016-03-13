package com.example.cs169_au.represent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cs169-au on 3/11/16.
 */
public class CongressAdapter extends ArrayAdapter<Congressperson> {

    public CongressAdapter(Context context, ArrayList<Congressperson> al) {
        super(context, 0, al);
    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        Log.e("DEBUG TAG Adapter", "get VIEW");

        final Congressperson thisCP = getItem(position);

        Log.e("DEBUG TAG Adapter", thisCP.theirName);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item_1, parent, false);

        }

        // Lookup view for data population

        TextView cName = (TextView) convertView.findViewById(R.id.name);

        TextView cEmail = (TextView) convertView.findViewById(R.id.email);

        TextView cSite = (TextView) convertView.findViewById(R.id.site);

        Button seeMoreButton = (Button) convertView.findViewById(R.id.SEEMORE);
        seeMoreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myReprIntent = new Intent(getContext(), Detailed.class);
                myReprIntent.putExtra("detailed", thisCP);
                myReprIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(myReprIntent);
            }
        });

        // Populate the data into the template view using the data object
        cName.setText(thisCP.theirName);
        cEmail.setText(thisCP.email);
        cSite.setText(thisCP.site);
        // Return the completed view to render on screen

        return convertView;

    }

}