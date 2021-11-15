package com.tajway.tajwaycabs.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tajway.tajwaycabs.Fragments.LocalTrip_Fragment;
import com.tajway.tajwaycabs.Fragments.OneWay_Fragment;
import com.tajway.tajwaycabs.Fragments.RoundTrip_Fragment;
import com.tajway.tajwaycabs.R;
import com.google.android.material.tabs.TabLayout;

public class CurrentBookingDetailsTab_Activity extends AppCompatActivity {

    TabLayout subTabLayout;
    ViewPager subTab_viewPager;
    String Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_booking_details_tab_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Current Booking");


        subTabLayout = findViewById(R.id.subTabLayout);
        subTab_viewPager = findViewById(R.id.subTab_viewPager);
        subTabLayout.addTab(subTabLayout.newTab().setText("  One Way Trip"));
        subTabLayout.addTab(subTabLayout.newTab().setText("  Round Trip "));
        subTabLayout.addTab(subTabLayout.newTab().setText(" Local Trip"));


        subTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        SubTabDescriptionTab_Adapter subTabDescriptionTabAdapter = new SubTabDescriptionTab_Adapter(getSupportFragmentManager(), CurrentBookingDetailsTab_Activity.this,
                subTabLayout.getTabCount());

        subTab_viewPager.setAdapter(subTabDescriptionTabAdapter);
        //subTab_viewPager.addOnAdapterChangeListener(new TabLayoutOnPageChangeListener(subTabLayout));
        subTab_viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(subTabLayout));
        subTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                subTab_viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public class SubTabDescriptionTab_Adapter extends FragmentPagerAdapter {

        Context subTab_context;
        int subTab_totalTabs;

        public SubTabDescriptionTab_Adapter(@NonNull FragmentManager fm, Context subTab_context, int subTab_totalTabs) {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
/*
        if (item.getItemId() == R.id.nav_home) {

            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/





}