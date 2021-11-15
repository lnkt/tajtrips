package com.tajway.tajwaycabs.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.tajway.tajwaycabs.Fragments.LocalTrip_Fragment;
import com.tajway.tajwaycabs.Fragments.OneWay_Fragment;
import com.tajway.tajwaycabs.Fragments.RoundTrip_Fragment;
import com.tajway.tajwaycabs.R;
import com.google.android.material.tabs.TabLayout;

public class AcceptBookingTab_Activity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_booking_tab_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("One Way Trip"));
        tabLayout.addTab(tabLayout.newTab().setText("Round Trip"));
        tabLayout.addTab(tabLayout.newTab().setText("Local Trip"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }



    class MyAdapter extends FragmentPagerAdapter {
        Context context;
        int totalTabs;
        public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
            super(fm);
            context = c;
            this.totalTabs = totalTabs;
        }
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
            return totalTabs;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}