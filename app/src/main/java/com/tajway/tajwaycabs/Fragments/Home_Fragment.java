package com.tajway.tajwaycabs.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.tajway.tajwaycabs.Activities.CompleteBooking_Activity;
import com.tajway.tajwaycabs.Activities.Contact_Us_Activity;
import com.tajway.tajwaycabs.Activities.CurrentBookingDetailsTab_Activity;
import com.tajway.tajwaycabs.Activities.DriverAccounting;
import com.tajway.tajwaycabs.Activities.Information_Activity;
import com.tajway.tajwaycabs.Activities.MyBookingActivity;
import com.tajway.tajwaycabs.Activities.My_Wallete_Activity;
import com.tajway.tajwaycabs.Activities.RejectBookingActivity;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.responsedata.HomeItem;
import com.tajway.tajwaycabs.retrofitModel.InfoStatusModel;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.session.SessonManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home_Fragment extends Fragment {

    //CurrentBooking
    TabLayout subTabLayout;
    ViewPager subTab_viewPager;
    String Title;
    //previous
    RecyclerView RV_home;
    TextView tv_headerContact;
    ArrayList<HomeItem> arrListHome = new ArrayList<>();
    ArrayList<HomeItem> arrListHomeDriver = new ArrayList<>();
    HomeItemAdapter homeItemAdapter;
    int[] homeItemImage = {R.drawable.my_booking_img1, R.drawable.booking_2_2, R.drawable.com_book_icon,
            R.drawable.rejected_img_icon, R.drawable.wallet_img, R.drawable.contact_us};

    String[] homeItemName = {"Current Booking", "My Booking", "Completed Booking", "Cancel Booking", "Wallet", "Contact Us"};


    int[] homeItemImageDriver = {R.drawable.my_booking_img1, R.drawable.booking_2_2, R.drawable.com_book_icon,
            R.drawable.rejected_img_icon, R.drawable.wallet_img, R.drawable.ic_calculations, R.drawable.contact_us};

    String[] homeItemNameDriver = {"Current Booking", "My Booking", "Completed Booking", "Cancel Booking", "Wallet", "Driver Accounting", "Contact Us"};

    View view;
    SessonManager sessonManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_, container, false);
        sessonManager = new SessonManager(getContext());
        RV_home = view.findViewById(R.id.home_recycler);
        //
        subTabLayout = view.findViewById(R.id.subTabLayout);
        subTab_viewPager = view.findViewById(R.id.subTab_viewPager);
        subTabLayout.addTab(subTabLayout.newTab().setText("  One Way Trip"));
        subTabLayout.addTab(subTabLayout.newTab().setText("  Round Trip "));
        subTabLayout.addTab(subTabLayout.newTab().setText(" Local Trip"));
        //
        subTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
       // CurrentBookingDetailsTab_Activity.SubTabDescriptionTab_Adapter subTabDescriptionTabAdapter = new CurrentBookingDetailsTab_Activity.SubTabDescriptionTab_Adapter(getChildFragmentManager(),requireContext(),
         //       subTabLayout.getTabCount());
        Home_Fragment.SubTabDescriptionTab_Adapter subTabDescriptionTabAdapter= new Home_Fragment.SubTabDescriptionTab_Adapter(getChildFragmentManager(),getContext(),subTabLayout.getTabCount());
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
        //


        tv_headerContact = view.findViewById(R.id.tv_headerContact);
        hitIsDrive();
        Log.d("iusewqwxzcx", sessonManager.getDriverType());
        setRv_home(sessonManager.getDriverType());
        return view;
    }

    private void setRv_home(String driver) {
        RV_home.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        RV_home.setLayoutManager(layoutManager);
        if (driver.equalsIgnoreCase("2")) {
            homeItemAdapter = new HomeItemAdapter(getActivity(), arrListHomeDriver, driver);
        } else {
            homeItemAdapter = new HomeItemAdapter(getActivity(), arrListHome, driver);
        }
        // homeItemAdapter = new HomeItemAdapter(getActivity(), arrListHome, driver);
        RV_home.setAdapter(homeItemAdapter);

        for (int i = 0; i < homeItemImage.length; i++) {
            HomeItem homeItem = new HomeItem();
            homeItem.setImageHome(homeItemImage[i]);
            homeItem.setNameIemHome(homeItemName[i]);
            arrListHome.add(homeItem);
        }
        for (int i = 0; i < homeItemImageDriver.length; i++) {
            HomeItem homeItem = new HomeItem();
            homeItem.setImageHome(homeItemImageDriver[i]);
            homeItem.setNameIemHome(homeItemNameDriver[i]);
            arrListHomeDriver.add(homeItem);
        }

    }

    public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {
        Context context;
        ArrayList<HomeItem> arrayList;
        String driver;

        public HomeItemAdapter(Context context, ArrayList<HomeItem> arrayList, String driver) {
            this.context = context;
            this.arrayList = arrayList;
            this.driver = driver;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HomeItem homeItemMod = arrayList.get(position);

            holder.tv_name_home_item.setText(arrayList.get(position).getNameIemHome());

            holder.iv_home_item_img.setImageResource(homeItemMod.getImageHome());
            if (driver.equalsIgnoreCase("2")) {
                if (position == 0) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), CurrentBookingDetailsTab_Activity.class);
                            startActivity(intent);
                        }
                    });

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.backgrnd_home_green));
                    }
                } else if (position == 1) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), MyBookingActivity.class);
                            startActivity(intent);
                        }
                    });

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.background_home1));

                    }
                } else if (position == 2) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), CompleteBooking_Activity.class);
                            startActivity(intent);

                        }
                    });

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.background_home_blue));
                    }

                } else if (position == 3) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), RejectBookingActivity.class);
                            startActivity(intent);

                        }
                    });
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.background_home2));
                    }

                } else if (position == 4) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), My_Wallete_Activity.class);
                            startActivity(intent);
                        }
                    });
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.backgrnd_home_green));
                    }
                } else if (position == 5) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(), DriverAccounting.class));
                        }
                    });

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.background_home1));
                    }
                } else if (position == 6) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(), Contact_Us_Activity.class));
                        }
                    });

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.background_home_blue));
                    }
                }
            } else {

                if (position == 0) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), CurrentBookingDetailsTab_Activity.class);
                            startActivity(intent);
                        }
                    });

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.backgrnd_home_green));
                    }
                } else if (position == 1) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), MyBookingActivity.class);
                            startActivity(intent);
                        }
                    });

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.background_home1));

                    }
                } else if (position == 2) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), CompleteBooking_Activity.class);
                            startActivity(intent);

                        }
                    });

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.background_home_blue));
                    }

                } else if (position == 3) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), RejectBookingActivity.class);
                            startActivity(intent);

                        }
                    });
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.background_home2));
                    }

                } else if (position == 4) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), My_Wallete_Activity.class);
                            startActivity(intent);
                        }
                    });
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.backgrnd_home_green));
                    }
                } else if (position == 5) {
                    holder.linear_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(), Contact_Us_Activity.class));
                        }
                    });

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.linear_country.setBackground(getActivity().getDrawable(R.drawable.background_home_blue));
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            Log.d("gaggagyyy", String.valueOf(arrayList.size()));
            return arrayList.size();

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            int pos;
            TextView tv_name_home_item;
            ImageView iv_home_item_img;
            RelativeLayout linear_country;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                tv_name_home_item = itemView.findViewById(R.id.home_item_name);
                iv_home_item_img = itemView.findViewById(R.id.img_home_item);
                linear_country = itemView.findViewById(R.id.linear_country);
                pos = getAdapterPosition();


            }
        }
    }

    private void hitIsDrive() {
        if (CommonUtils.isOnline(getActivity())) {
            final ProgressDialog dialog = ProgressDialog.show(getActivity(), null, getString(R.string.loading));
            String mobile = sessonManager.getString("userNumber", "");
            Call<InfoStatusModel> call = ApiExecutor.getApiService(getActivity()).isDriver(mobile);
            Log.d("isMobileNumber", "hitIsDrive: "+mobile);

            call.enqueue(new Callback<InfoStatusModel>() {
                             @Override
                             public void onResponse(Call<InfoStatusModel> call, Response<InfoStatusModel> response) {
                                 // System.out.println("ViewVisitorType " + "API Data" + new Gson().toJson(response.body()));

                                 String respon = new Gson().toJson(response.body());
                                 Log.d("OneWaResponse==", "onResponse: "+response.code());
                                 Log.d("OneWaResponse==", respon);

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().getStatus() != null && response.body().getStatus().equals("success")) {
                                         if (response.body().getType() != null) {
                                             sessonManager.setDriverType(response.body().getType());
                                         }
                                     }
                                 } else {
                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     Log.d("sdsasadsda", response.message());
                                     //CommonUtils.setSnackbar(tvClassName, getString(R.string.server_not_responding));
                                 }
                             }

                             @Override
                             public void onFailure(Call<InfoStatusModel> call, Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(getActivity(), getString(R.string.please_check_network));
        }
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

            return true;
        }
/*
        if (item.getItemId() == R.id.nav_home) {

            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

}