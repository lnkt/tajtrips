package com.tajway.tajwaycabs;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tajway.tajwaycabs.Fragments.LocalTrip_Fragment;
import com.tajway.tajwaycabs.Fragments.OneWay_Fragment;
import com.tajway.tajwaycabs.Fragments.RoundTrip_Fragment;

public class MyBidTabAdapter extends FragmentPagerAdapter {

    Context subTab_context;
    int subTab_totalTabs;

    public MyBidTabAdapter(@NonNull FragmentManager fm,  Context subTab_context, int subTab_totalTabs) {
        super(fm);
        this.subTab_context = subTab_context;
        this.subTab_totalTabs = subTab_totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                OneWay_Fragment oneWay_fragment = new OneWay_Fragment();
                return oneWay_fragment;
            case 1:
                RoundTrip_Fragment roundTrip_fragment = new RoundTrip_Fragment();
                return roundTrip_fragment;
            case 2:
                LocalTrip_Fragment localTrip_fragment = new LocalTrip_Fragment();
                return localTrip_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return subTab_totalTabs;
    }
}
