package com.tajway.tajwaycabs.Activities;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.tajway.tajwaycabs.BuildConfig;
import com.tajway.tajwaycabs.Fragments.Home_Fragment;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RelativeLayout relative_cart;
    public static TextView txtHeader, TvCartQty, txtNotifyQuantity;
    DialogInterface newdialogInterface;
    private DrawerLayout drawer;
    Toolbar toolbar;
    public static TextView tv_headerContact;
    NavigationView navigationView;
    Intent intent;
    Context context;
    int location;
    SessonManager sessonManager;
    ProgressDialog progressIndicator;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressIndicator = new ProgressDialog(MainActivity.this);
        progressIndicator.setMessage("Loading..."); // Setting Message
        progressIndicator.setTitle("ProgressDialog"); // Setting Title
        progressIndicator.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner

        //locationEnabled();
        //hooks
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessonManager = new SessonManager(MainActivity.this);
        drawer = findViewById(R.id.drawer_layout);
        progressBar = findViewById(R.id.geoProgressBar);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        tv_headerContact = headerView.findViewById(R.id.tv_headerContact);
        tv_headerContact.setText(sessonManager.getString("userNumber", ""));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                new Home_Fragment()).commit();

        if (savedInstanceState == null) {
            toolbar.setTitle("Home");
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    new Home_Fragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            
        }else{
            requestPermission();
        }

//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                recreate();
//
//            }
//        }, 5000);

    }

    private void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)){

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Permission Needed")
                    .setMessage("GPS Enable")
                    .setPositiveButton("Okay", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);



                                }

                            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();

        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                toolbar.setTitle("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Home_Fragment()).commit();
                break;

            /*case R.id.nav_abou_us:
                toolbar.setTitle("About us");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new About_us_Fragment()).commit();
                break;*/


            case R.id.nav_myProfile:
                toolbar.setTitle("Profile");
               /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyProfileActivity()).commit();*/

                Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(intent);
                break;

//            case R.id.nav_current_booking:
//                // toolbar.setTitle("Current Booking");
//                intent = new Intent(MainActivity.this, CurrentBookingDetailsTab_Activity.class);
//                intent.putExtra("MyBid", "Current Booking");
//                startActivity(intent);
//
//                break;


            case R.id.nav_myBooking:
//                toolbar.setTitle("Booking");
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new MyVehicle_Fragment()).commit();
                intent = new Intent(MainActivity.this, MyBookingActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_createProfile:
//                toolbar.setTitle("Booking");
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new MyVehicle_Fragment()).commit();
                intent = new Intent(MainActivity.this, CreateBooking.class);
                startActivity(intent);
                break;


            case R.id.nav_rejectedBooking:
                // toolbar.setTitle("Rejected Booking");
               /* Toast.makeText(this, "Rejected Booking", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, RejectBookingTab_Activity.class);
                startActivity(intent);*/

                intent = new Intent(MainActivity.this, RejectBookingActivity.class);
                intent.putExtra("bookingStatus", "Rejected Booking");
                startActivity(intent);
                break;


            case R.id.nav_complete_booking:
                // toolbar.setTitle("Complete Booking");
                intent = new Intent(MainActivity.this, CompleteBooking_Activity.class);
                startActivity(intent);
                break;


            case R.id.nav_myWallete:
                toolbar.setTitle("Wallet");
               /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new My_Wallete_Activity()).commit();*/

                intent = new Intent(MainActivity.this, My_Wallete_Activity.class);
                startActivity(intent);
                break;


            case R.id.nav_myDrivers:
                toolbar.setTitle("Drivers");
              /*  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Drivers_Activity()).commit();*/
                Intent driver = new Intent(MainActivity.this, Drivers_Activity.class);
                startActivity(driver);

                break;
            case R.id.nav_myCars:
                toolbar.setTitle("Drivers");
              /*  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Drivers_Activity()).commit();*/
                Intent car = new Intent(MainActivity.this, CarList.class);
                startActivity(car);

                break;

            case R.id.nav_notifications:
                toolbar.setTitle("Notifications");
                Intent notifica = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(notifica);

                break;


            case R.id.nav_Going:
                toolbar.setTitle("Going Home");
               /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyRoute_Activity()).commit();*/
                Intent myRout = new Intent(MainActivity.this, MyRoute_Activity.class);
                startActivity(myRout);
                break;


            case R.id.nav_information:
                toolbar.setTitle("Information");
               /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Information_Activity()).commit();*/

                Intent information = new Intent(MainActivity.this, Information_Activity.class);
                startActivity(information);

                break;


            case R.id.nav_penaltyStructure:
                toolbar.setTitle("Penalty Structure");
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Penaulty_Structure_Activity()).commit();*/

                Intent penultyStucture = new Intent(MainActivity.this, Penaulty_Structure_Activity.class);
                startActivity(penultyStucture);

                break;


            case R.id.nav_tutorials:
                toolbar.setTitle("Tutorials");
               /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new My_tutorial_Activity()).commit();*/

                Intent myTutorial = new Intent(MainActivity.this, My_tutorial_Activity.class);
                startActivity(myTutorial);


                break;


            case R.id.nav_upload_payment_details:
                //  toolbar.setTitle("Upload Payment Detals");
                startActivity(new Intent(MainActivity.this, UploadPaymentDetailsActivity.class));
                break;
            case R.id.nav_term_condition:
                toolbar.setTitle("Terms and Condition");
               /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Term_Condition_Activity()).commit();*/
                startActivity(new Intent(MainActivity.this, Term_Condition_Activity.class));

                break;
            case R.id.nav_privacy_policy:
                toolbar.setTitle("Privacy Policy");

                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Privacy_Policy_Activity()).commit();*/

                startActivity(new Intent(MainActivity.this, Privacy_Policy_Activity.class));

                break;

            case R.id.nav_share:
                toolbar.setTitle("Share");
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;

            case R.id.nav_contact_us:
                toolbar.setTitle("Contact Us");
              /*  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Contact_Us_Activity()).commit();*/

                startActivity(new Intent(MainActivity.this, Contact_Us_Activity.class));

                break;


            case R.id.nav_logout:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("Are you sure you want to logout?");
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                /*SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                sharedPreferences2.edit().clear().commit();*/
                                sessonManager.setToken("");
                                Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                return;

                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

                break;







           /* case R.id.nav_myBids:
              //  toolbar.setTitle("My Bid");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyBid_Fragment()).commit();

                intent = new Intent(MainActivity.this, CurrentBookingDetailsTab_Activity.class);
                intent.putExtra("MyBid","My Bid");
                startActivity(intent);
                break;*/

           /* case R.id.nav_accepted_booking:
               // toolbar.setTitle("Accept Booking");
                // Toast.makeText(this, "Accept_booking", Toast.LENGTH_SHORT).show();
               *//* intent = new Intent(MainActivity.this, AcceptBookingTab_Activity.class);
                startActivity(intent);*//*
                intent = new Intent(MainActivity.this, AcceptBookingActivity.class);
                intent.putExtra("bookingStatus","Accept Booking");
                startActivity(intent);
                break;*/

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    Fragment fragment = null;

    private void showHome() {
        Fragment fragment = new Home_Fragment();
        if (fragment != null) {
            toolbar.setTitle("Home");
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_viewcart);
        MenuItemCompat.setActionView(item, R.layout.badge_menu);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        relative_cart = (RelativeLayout) notifCount.findViewById(R.id.relative_cart);
        TvCartQty = (TextView) notifCount.findViewById(R.id.txtCartQuantity);
        txtNotifyQuantity = (TextView) notifCount.findViewById(R.id.txtNotifyQuantity);
        ImageView notification = (ImageView) notifCount.findViewById(R.id.notification);
        ImageView search = (ImageView) notifCount.findViewById(R.id.search);


        if (sessonManager.getNotificationQty() == 0) {
            txtNotifyQuantity.setVisibility(View.GONE);
        } else {
            txtNotifyQuantity.setText("" + sessonManager.getNotificationQty());
        }
//        TvCartQty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MyCartActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtNotifyQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainpageActivity.this, SearchActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        });

        return true;
    }

    private void locationEnabled () {
        LocationManager lm = (LocationManager)
                getSystemService(Context. LOCATION_SERVICE ) ;
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager. GPS_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager. NETWORK_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        if (!gps_enabled && !network_enabled) {

            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("GPS Enable")
                    .setPositiveButton("Settings", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                    paramDialogInterface.dismiss();
                                    newdialogInterface = paramDialogInterface;
                                    if(lm.isProviderEnabled(LocationManager. GPS_PROVIDER )){
                                        progressIndicator.hide();

                                    }else{
                                        progressIndicator.show();
                                        progressIndicator.setCancelable(false);
                                    }


                                }

                            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
              .show();

        }


    }



    private boolean doubleBackToExitPressedOnce = true;

    @Override
    public void onBackPressed() {
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragment instanceof Home_Fragment) {
                super.onBackPressed();

            } else {
                showHome();

                if (doubleBackToExitPressedOnce) {
                    doubleBackToExitPressedOnce = false;
                    Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show ();
                } else {
                    finish();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==1){
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_DENIED){
                finish();
            }

        }
    }
}