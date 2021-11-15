package com.tajway.tajwaycabs.Activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.apiJsonResponse.LoginJsonResponse;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.requestdata.LoginRequest;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {


    public static final String CHANNEL_ID = "Customer";
    public static final String CHANNEL_NAME = "Customer";
    String notificationToken;


    EditText et_phone_nubmer_login;
    TextView registerTab, tv_clickToRead;
    Button tv_done_login;
    SessonManager sessonManager;

    CheckBox checkBox;
    // viewPager
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    String one = "2";
    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        sessonManager = new SessonManager(Login_Activity.this);
        mWebview = new WebView(this);
        //viewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        checkBox = findViewById(R.id.checkBox);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];


        et_phone_nubmer_login = findViewById(R.id.et_phone_nubmer_login);
        tv_done_login = findViewById(R.id.tv_done_login);
        registerTab = findViewById(R.id.registerTab);
        tv_clickToRead = findViewById(R.id.tv_clickToRead);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        tv_clickToRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  openWebView();

                Intent intent = new Intent(Login_Activity.this, openWebView.class);
                startActivity(intent);
            }
        });

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dots));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dots));

      /*  viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
*/

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                one = "1";
            }
        });

        tv_done_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
                if (setLogin()) {


                    MobileEmailAPI();
                }

            }
        });

        registerTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(intent);
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dots));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dots));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


     /*   FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d("getInstanceId failed", String.valueOf(task.getException()));
                            return;
                        }

                        // Get new Instance ID token
                        notificationToken = task.getResult().getToken();

                        // Log and toast
                        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String msg = getString(R.string.msg_token_fmt, notificationToken);
                        Log.d("tascxzvg", msg);
//                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });*/


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d("FetchingFCMregistration", String.valueOf(task.getException()));
                            return;
                        }

                      /*  // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(Integer.parseInt("firebaseToken"), token);
                        Log.d("cnkjhnckc", msg);
                        Toast.makeText(Login_Activity.this, msg, Toast.LENGTH_SHORT).show();*/


                        // Get new Instance ID token
                        notificationToken = task.getResult();
                        Log.d("asldfhsdgv", notificationToken);
                        // Log and toast
                        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String msg = getString(R.string.msg_token_fmt, notificationToken);
                        Log.d("tascxzvg", msg);
//                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                });


        //  Log.d("dfhjgsdf", notificationToken);

    }


    private void openWebView() {
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        final Activity activity = this;
        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        mWebview.loadUrl("http://tajtripcars.appoffice.xyz/api/terms-condition");
        setContentView(mWebview);

    }


    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private Integer[] images = {R.drawable.logo1024x512};

        public ViewPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.login_banner_row, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setImageResource(images[position]);

            ViewPager vp = (ViewPager) container;
            vp.addView(view, 0);
            return view;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ViewPager vp = (ViewPager) container;
            View view = (View) object;
            vp.removeView(view);

        }
    }


    private boolean setMobileNo() {
        if (et_phone_nubmer_login.getText().toString().length() > 9) {
            sessonManager.putString("userNumber", et_phone_nubmer_login.getText().toString());
            Toast.makeText(Login_Activity.this, "" + sessonManager.getString("userNumber", ""), Toast.LENGTH_SHORT).show();

            return true;
        } else {
            et_phone_nubmer_login.setError("Please enter 10 digit mobile no");
            et_phone_nubmer_login.requestFocus();
            return false;
        }
    }


    private boolean setCheck() {
        if (one.equalsIgnoreCase("2")) {
            return true;
        } else {
            Toast.makeText(this, "Please select term and conditions", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean setLogin() {
        if (!setMobileNo()) {
            return false;
        } else if (!setCheck()) {
            return false;
        }
        //LoginAPI();
        return true;
    }


    private void MobileEmailAPI() {
        if (CommonUtils.isOnline(Login_Activity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(Login_Activity.this, null, getString(R.string.loading));
            LoginRequest request = new LoginRequest();

            // request.setEmail("admin");
            request.setMobile(et_phone_nubmer_login.getText().toString());

            Log.d("sghgs", et_phone_nubmer_login.getText().toString());
            //   request.setPassword("123456");

            Call<LoginJsonResponse> call = ApiExecutor.getApiService(Login_Activity.this).apiLogin(request);
            System.out.println("API url ---" + call.request().url());

            System.out.println("API request  ---" + new Gson().toJson(request));
            call.enqueue(new Callback<LoginJsonResponse>() {
                             @Override
                             public void onResponse(Call<LoginJsonResponse> call, Response<LoginJsonResponse> response) {
                                 String respon = new Gson().toJson(response.body());
                                 dialog.dismiss();
                                 Log.d("responLogin", respon);
                                 Log.d("responLogin", "MobileEmailAPI: "+response.code());
                                 System.out.println("MobileVerifyActivity" + "API Data" + new Gson().toJson(response.body()));

                                 if (response.body() != null) {
                                     if (response.body().status != null && response.body().status.equals("success")) {

                                         String message = response.body().message;
                                         Toast.makeText(Login_Activity.this, message, Toast.LENGTH_SHORT).show();
                                         Intent Login = new Intent(Login_Activity.this, Otp_Activity.class);
                                         Login.putExtra("auth_firebasetoken", notificationToken);
                                         Login.putExtra("mobile", et_phone_nubmer_login.getText().toString());
                                         Login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                         //   Log.d("hahagagag",notificationToken);
                                         startActivity(Login);
                                     } else {
                                         CommonUtils.showToast(Login_Activity.this, response.body().message);

                                     }
                                 } else {
                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     // CommonUtils.setSnackbar(tvLogin, getString(R.string.server_not_responding));
                                 }


                             }

                             @Override
                             public void onFailure(Call<LoginJsonResponse> call, Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                                 Log.d("error==", "onFailure: "+t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(Login_Activity.this, getString(R.string.please_check_network));
        }
    }


}