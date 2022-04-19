package com.tajway.tajwaycabs.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.apiJsonResponse.OTPJsonResponse;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.requestdata.OTPRequest;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Otp_Activity extends AppCompatActivity {

    Button tv_done_otp;
    PinEntryEditText pinEntry;
    String mobile,firebase_token;
    SessonManager sessonManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_);
        sessonManager = new SessonManager(Otp_Activity.this);
        final String getIntentData = getIntent().getStringExtra("addNewDriverActivity");
        mobile = getIntent().getStringExtra("mobile");
        firebase_token=getIntent().getStringExtra("auth_firebasetoken");
      // Log.d("fiihshhbb",firebase_token);

//        Log.d("jfjjfjjfjfjf",getIntentData);
//        Log.d("mobile",mobile);

        pinEntry=findViewById(R.id.peet_otp);
        tv_done_otp = findViewById(R.id.tv_done_otp);
        tv_done_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (getIntentData.equalsIgnoreCase("addNewDriverActivity")) {
                    Intent intent = new Intent(Otp_Activity.this, AddNewDriverForm_Activity.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(Otp_Activity.this, MainActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }*/
                if (setOTP()) {
                    OTPAPI();
                }

            }
        });
    }


    private boolean setInputOTP() {
        if (pinEntry.getText().toString().length() > 5) {
            return true;
        } else {
            pinEntry.setError("Please enter 6 digit otp no");
            return false;
        }
    }


    private boolean setOTP() {
        if (!setInputOTP()) {
            return false;
        }
        // OTPAPI();
        return true;
    }


    private void OTPAPI() {
        if (CommonUtils.isOnline(Otp_Activity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(Otp_Activity.this, null, getString(R.string.loading));
            OTPRequest request = new OTPRequest();

            // request.setEmail("admin");
            request.setMobile(mobile);
            //   request.setPassword("123456");
            request.setOtp(pinEntry.getText().toString());
            request.setType("login");
            request.setNotification_token(firebase_token);

            Call<OTPJsonResponse> call = ApiExecutor.getApiService(Otp_Activity.this).apiOTP(request);

//            Log.d("hahaggTTT",firebase_token);
            System.out.println("API url ---" + call.request().url());
            System.out.println("API request  ---" + new Gson().toJson(request));
            Log.e("OTPAPI","token"+firebase_token);
            call.enqueue(new Callback<OTPJsonResponse>() {
                             @Override
                             public void onResponse(Call<OTPJsonResponse> call, Response<OTPJsonResponse> response) {
                                 String respon = new Gson().toJson(response.body());
                                 dialog.dismiss();
                                 Log.d("responOTP", respon);
                                 System.out.println("MobileVerifyActivity" + "API Data" + new Gson().toJson(response.body()));


                                 if (response.body() != null) {
                                     if (response.body().status != null && response.body().status.equals("success")) {
                                         String message = response.body().message;
                                         String token = response.body().token;
                                         String fire_token=response.body().notification_token;
                                         sessonManager.setToken(token);
                                         Log.d("fgfga", token);
                                         Log.e("OTPAPI","getting token"+response.body().token);
                                         Toast.makeText(Otp_Activity.this, message, Toast.LENGTH_SHORT).show();
                                         Intent Login = new Intent(Otp_Activity.this, MainActivity.class);
                                         Login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                         startActivity(Login);
                                         finish();
                                     } else {
                                         CommonUtils.showToast(Otp_Activity.this, response.body().message);

                                     }
                                 } else {
                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     // CommonUtils.setSnackbar(tvLogin, getString(R.string.server_not_responding));
                                 }


                             }

                             @Override
                             public void onFailure(Call<OTPJsonResponse> call, Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(Otp_Activity.this, getString(R.string.please_check_network));
        }
    }


}