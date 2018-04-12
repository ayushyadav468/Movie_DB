package com.example.nitantsood.moviedb.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nitantsood.moviedb.PlaceholderFragment;

/**
 * Created by NITANT SOOD on 26-07-2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == PlaceholderFragment.MOVIES){
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(PlaceholderFragment.TAB_NUMBER, PlaceholderFragment.MOVIES);
            fragment.setArguments(args);
            return fragment;
        }else if(position == PlaceholderFragment.TV_SHOWS){
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(PlaceholderFragment.TAB_NUMBER, PlaceholderFragment.TV_SHOWS);
            fragment.setArguments(args);
            return fragment;
        }else if(position == PlaceholderFragment.CELEBS){
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(PlaceholderFragment.TAB_NUMBER,PlaceholderFragment.CELEBS);
            fragment.setArguments(args);
            return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case PlaceholderFragment.MOVIES:
                return "MOVIES";
            case PlaceholderFragment.TV_SHOWS:
                return "TV SHOWS";
            case PlaceholderFragment.CELEBS:
                return "CELEBS";
        }
        return null;
    }
}
