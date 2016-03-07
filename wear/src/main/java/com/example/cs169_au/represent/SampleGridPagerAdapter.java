package com.example.cs169_au.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.view.View;

import java.util.List;

public class SampleGridPagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;
    private List mRows;

    public SampleGridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
    }

    static final int[] BG_IMAGES = new int[] {
            R.drawable.down_buton, R.drawable.down_buton, R.drawable.down_buton, R.drawable.down_buton, R.drawable.down_buton
    };

    // Override methods in FragmentGridPagerAdapter
    // Obtain the UI fragment at the specified position
    @Override
    public Fragment getFragment(int row, int col) {
        CardFragment fragment = CardFragment.create("DUMMY", "DUMMY DUMMY (DUM)");;
        if(row == 0) {
            fragment = CardFragment.create("Senator", "Berner Sandy (I)");
        } else if(row == 1) {
            fragment = CardFragment.create("Senator", "Hillary Chinton (D)");
        } else if(row == 2) {
            if (col == 0) {
                fragment = CardFragment.create("Rep", "Don J. Rump (R)");
            } else {
                fragment = CardFragment.create("2012 Vote View", "Obama:14%, Romney:86%");
            }
        }

        // Advanced settings (card gravity, card expansion/scrolling)
        //fragment.setCardGravity(page.cardGravity);
        //fragment.setExpansionEnabled(page.expansionEnabled);
        //fragment.setExpansionDirection(page.expansionDirection);
        //fragment.setExpansionFactor(page.expansionFactor);
        return fragment;
    }

    // Obtain the number of pages (vertical)
    @Override
    public int getRowCount() {
        return 2;
    }

    // Obtain the number of pages (horizontal)
    @Override
    public int getColumnCount(int rowNum) {
        return 3;
    }


}
