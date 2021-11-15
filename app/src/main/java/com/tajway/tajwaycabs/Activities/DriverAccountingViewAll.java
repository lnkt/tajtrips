package com.tajway.tajwaycabs.Activities;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.adapter.DriveAccountAdapter;
import com.tajway.tajwaycabs.adapter.DriveViewAllAccountAdapter;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.helper.DBManagerQuery;
import com.tajway.tajwaycabs.helper.DatabaseHelper;
import com.tajway.tajwaycabs.helper.DriverApplication;
import com.tajway.tajwaycabs.responsedata.DriverAccount;
import com.tajway.tajwaycabs.retrofitModel.DriverAccountingModel;
import com.tajway.tajwaycabs.retrofitModel.InfoStatusModel;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverAccountingViewAll extends AppCompatActivity {
    private RecyclerView recyclerviewNavOct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_accounting_all_view);
        getSupportActionBar().setTitle("View All Accounting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerviewNavOct = findViewById(R.id.recyclerviewNavOct);
        hitCompletedBookingId();
    }


    private void hitCompletedBookingId() {
        if (CommonUtils.isOnline(this)) {
            final ProgressDialog dialog = ProgressDialog.show(DriverAccountingViewAll.this, null, getString(R.string.loading));
            //String mobile = sessonManager.getString("userNumber", "");
            Call<InfoStatusModel> call = ApiExecutor.getApiService(DriverAccountingViewAll.this).accountingViewAll("245");

            call.enqueue(new Callback<InfoStatusModel>() {
                             @Override
                             public void onResponse(Call<InfoStatusModel> call, Response<InfoStatusModel> response) {
                                 String respon = new Gson().toJson(response.body());
                                 Log.d("OneWaResponse", respon);

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().getStatus() != null && response.body().getStatus().equals("success")) {
                                         if (response.body().getListing_driver_accounting() != null) {
                                             LinearLayoutManager layoutManager = new LinearLayoutManager(DriverAccountingViewAll.this);
                                             layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                             recyclerviewNavOct.setLayoutManager(layoutManager);
                                             recyclerviewNavOct.setNestedScrollingEnabled(false);
                                             DriveViewAllAccountAdapter adapter = new DriveViewAllAccountAdapter(DriverAccountingViewAll.this, response.body().getListing_driver_accounting());
                                             recyclerviewNavOct.setAdapter(adapter);
                                         }
                                     }
                                 } else {
                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     Log.d("sdsasadsda", response.message());
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
            CommonUtils.showToastInCenter(DriverAccountingViewAll.this, getString(R.string.please_check_network));
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
