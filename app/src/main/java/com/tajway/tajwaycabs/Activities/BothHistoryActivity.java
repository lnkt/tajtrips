package com.tajway.tajwaycabs.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import com.tajway.tajwaycabs.Fragments.BalanceHistoryFragment;
import com.tajway.tajwaycabs.Fragments.CashBackHistoryFragment;
import com.tajway.tajwaycabs.R;

public class BothHistoryActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    String name, position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_both_history);

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "History" + "</font>")));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Balance history"));
        //tabLayout.addTab(tabLayout.newTab().setText("Cash back history"));



        Intent intent = getIntent();
        position = intent.getStringExtra("position");

        Log.d("position",position);
        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(Integer.parseInt(position));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                //   sessonManager.setPosition(String.valueOf(position));
                viewPager.setCurrentItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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


    public class MyAdapter extends FragmentPagerAdapter {

        private Context myContext;
        int totalTabs;

        public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
            super(fm);
            myContext = context;
            this.totalTabs = totalTabs;
        }

        // this is for fragment tabs
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                   // Log.d("check1", name);
                    BalanceHistoryFragment balanceHistoryFragment = new BalanceHistoryFragment();
                    Bundle bundle = new Bundle();
                    //bundle.putString("name", "Men");

                    return balanceHistoryFragment;

                case 1:
                    //Log.d("check2", name);
                    CashBackHistoryFragment cashBackHistoryFragment = new CashBackHistoryFragment();
                    Bundle bundlewomen = new Bundle();
                    //bundlewomen.putString("name", "Women");
                   // womenFragment.setArguments(bundlewomen);
                    return cashBackHistoryFragment;

                default:
                    return null;
            }
        }

        // this counts total number of tabs
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

       /* if (item.getItemId() == R.id.badge) {

            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }


}