package com.tajway.tajwaycabs.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tajway.tajwaycabs.MyBidTabAdapter;
import com.tajway.tajwaycabs.R;
import com.google.android.material.tabs.TabLayout;

public class MyBidActivity extends AppCompatActivity {


    TabLayout tablayoutMyBid;
    ViewPager ViewPagerMyBid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bid);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tablayoutMyBid = findViewById(R.id.tablayoutMyBid);
        ViewPagerMyBid = findViewById(R.id.ViewPagerMyBid);

        tablayoutMyBid.addTab(tablayoutMyBid.newTab().setText("  ONE WAY"));
        tablayoutMyBid.addTab(tablayoutMyBid.newTab().setText("  ROUND TRIP "));
        tablayoutMyBid.addTab(tablayoutMyBid.newTab().setText(" LOCAL TRIP"));


        tablayoutMyBid.setTabGravity(TabLayout.GRAVITY_FILL);
        MyBidTabAdapter subTabDescriptionTabAdapter = new MyBidTabAdapter(getSupportFragmentManager(), MyBidActivity.this,
                tablayoutMyBid.getTabCount());



        ViewPagerMyBid.setAdapter(subTabDescriptionTabAdapter);
        //subTab_viewPager.addOnAdapterChangeListener(new TabLayoutOnPageChangeListener(subTabLayout));
        ViewPagerMyBid.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayoutMyBid));
        tablayoutMyBid.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewPagerMyBid.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}