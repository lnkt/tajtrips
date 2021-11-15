package com.tajway.tajwaycabs.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.adapter.InfoAdapter;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.retrofitModel.InfoStatusModel;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class My_tutorial_Activity extends AppCompatActivity {

  View v;
    RecyclerView rvInfo;
    SessonManager sessonManager;
    TextView tv_norecord_local;
    ArrayList<InfoStatusModel.Data.Gallery> list = new ArrayList<>();

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_my_tutorial_, container, false);*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_tutorial_);
        getSupportActionBar().setTitle("Tutorials");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    

    rvInfo = findViewById(R.id.rvInfo);
        sessonManager = new SessonManager(My_tutorial_Activity.this);
        tv_norecord_local = findViewById(R.id.tv_norecord_local);

        hitApi();
      //  return v;
    }

    private void hitApi() {

        if (CommonUtils.isOnline(My_tutorial_Activity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(My_tutorial_Activity.this, null, getString(R.string.loading));

            Call<InfoStatusModel> call = ApiExecutor.getApiService(My_tutorial_Activity.this).apiInfoList("Bearer " + sessonManager.getToken(),"tutorials");

            call.enqueue(new Callback<InfoStatusModel>() {
                             @Override
                             public void onResponse(Call<InfoStatusModel> call, Response<InfoStatusModel> response) {
                                 // System.out.println("ViewVisitorType " + "API Data" + new Gson().toJson(response.body()));

                                 String respon = new Gson().toJson(response.body());
                                 Log.d("OneWaResponse", respon);

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().getStatus() != null && response.body().getStatus().equals("success")) {

                                         if (response.body().getData() != null) {
                                             list = response.body().getData().getGallery();
                                             setRvData();
                                         } else {
                                             tv_norecord_local.setVisibility(View.VISIBLE);
                                             rvInfo.setVisibility(View.GONE);
                                         }
                                     } else {
                                         tv_norecord_local.setVisibility(View.VISIBLE);
                                         rvInfo.setVisibility(View.GONE);
                                     }
                                 } else {
                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                   //  Log.d("sdsasadsda",response.message());
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
            CommonUtils.showToastInCenter(My_tutorial_Activity.this, getString(R.string.please_check_network));
        }
    }
    private void setRvData() {
        rvInfo.setAdapter(new InfoAdapter(My_tutorial_Activity.this,list));
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