package com.example.cs169_au.represent;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by cs169-au on 3/11/16.
 */
public class Congressperson implements Parcelable {

    public String theirName;
    public String party;
    public String bioid;
    public String email;
    public String site;
    public String termEnd;

    public Congressperson(HashMap<String, String> hm) {
        theirName = hm.get("title") + " " + hm.get("first_name") + " " + hm.get("last_name") + " (" + (hm.get("party")).substring(0, 1) + ")";
        party = hm.get("party");
        bioid = hm.get("bioguide_id");
        email = hm.get("oc_email");
        site = hm.get("website");
        termEnd = hm.get("term_end");
    }

    protected Congressperson(Parcel in) {
        theirName = in.readString();
        party = in.readString();
        bioid = in.readString();
        email = in.readString();
        site = in.readString();
        termEnd = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(theirName);
        dest.writeString(party);
        dest.writeString(bioid);
        dest.writeString(email);
        dest.writeString(site);
        dest.writeString(termEnd);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Congressperson> CREATOR = new Parcelable.Creator<Congressperson>() {
        @Override
        public Congressperson createFromParcel(Parcel in) {
            return new Congressperson(in);
        }

        @Override
        public Congressperson[] newArray(int size) {
            return new Congressperson[size];
        }
    };
}