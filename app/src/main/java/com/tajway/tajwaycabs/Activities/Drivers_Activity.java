package com.tajway.tajwaycabs.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.apiJsonResponse.DriverJsonResponse;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.requestdata.DriverRequest;
import com.tajway.tajwaycabs.responsedata.DriverDataResponse;
import com.tajway.tajwaycabs.retrofitModel.DriverList;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.retrofitwebservices.Apiclient;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Drivers_Activity extends AppCompatActivity {

    TextView tv_addDerivers;
    TextView tv_submitDriverNumber;
    RecyclerView RVDriver;
    DriverAdapter driverAdapter;
    ArrayList<DriverDataResponse> arListDriver;
    SessonManager sessonManager;

  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drivers_, container, false);*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_drivers_);
        getSupportActionBar().setTitle("Drivers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    sessonManager = new SessonManager(Drivers_Activity.this);
        Log.d("sessonManager", String.valueOf(sessonManager));

        tv_addDerivers = findViewById(R.id.tv_addDerivers);
        RVDriver = findViewById(R.id.rv_driver);
        arListDriver = new ArrayList<>();

        tv_addDerivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent intent = new Intent(Drivers_Activity.this, AddDriverActivity.class);
                startActivity(intent);*/

               /* Intent intent = new Intent(Drivers_Activity.this, Otp_Activity.class);
                String intentData = "addNewDriverActivity";
                intent.putExtra("addNewDriverActivity",intentData);
                startActivity(intent);*/

                Intent intent = new Intent(Drivers_Activity.this, AddDiverData_Activity.class);
                startActivity(intent);

            }
        });


        tv_submitDriverNumber = findViewById(R.id.tv_submitDriverNumber);
        tv_submitDriverNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Drivers_Activity.this, Otp_Activity.class);
                String intentData = "addNewDriverActivity";
                intent.putExtra("addNewDriverActivity", intentData);
                startActivity(intent);
            }
        });
        setRVDriver();
        //DriverListAPI();
        getDriverList();
    }

    private void getDriverList() {
        final ProgressDialog dialog = ProgressDialog.show(Drivers_Activity.this, null, getString(R.string.loading));
        String TAG ="driver details";
        Call<DriverList> call = Apiclient.getMyService().driverList();
        call.enqueue(new Callback<DriverList>() {
            @Override
            public void onResponse(Call<DriverList> call, Response<DriverList> response) {
                Log.d(TAG, "onResponse: "+response.body().getMessage());
                Log.d(TAG, "onResponse: "+response.code());
                Log.d(TAG, "onResponse: "+response.errorBody());
                if(response.body().getStatus().equalsIgnoreCase("success")){
                    List<DriverList.Datum> driverData = response.body().getData();
                    driverAdapter = new DriverAdapter(Drivers_Activity.this, driverData);
                    RVDriver.setAdapter(driverAdapter);
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DriverList> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                dialog.dismiss();
            }
        });
    }


    private void setRVDriver() {
        RVDriver.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Drivers_Activity.this, 1);
        RVDriver.setLayoutManager(layoutManager);


    }

    public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {

        Context context;
        List<DriverList.Datum> driverData;

        public DriverAdapter(Context context, List<DriverList.Datum> driverData) {
            this.context = context;
            this.driverData = driverData;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driverlist_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.driverName.setText(driverData.get(position).getDriver_name());
            holder.mobileNumberValue.setText(driverData.get(position).getDriver_mobile());
            holder.mobileNumber2Value.setText(driverData.get(position).getMobile1());
            holder.currentAddressValue.setText(driverData.get(position).getCurrent_address());
            holder.currentCityValue.setText(driverData.get(position).getCurrent_city());
            holder.licenseNumberValue.setText(driverData.get(position).getLicence_no());
            Glide.with(context).load(driverData.get(position).getImage()).into(holder.driverImage);
        }

        @Override
        public int getItemCount() {
            return driverData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView driverName, mobileNumberValue,mobileNumber2Value,currentAddressValue,currentCityValue,licenseNumberValue;
            ImageView driverImage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                driverName = itemView.findViewById(R.id.driverName);
                mobileNumberValue = itemView.findViewById(R.id.mobileNumberValue);
                mobileNumber2Value = itemView.findViewById(R.id.mobileNumber2Value);
                currentAddressValue = itemView.findViewById(R.id.currentAddressValue);
                currentCityValue = itemView.findViewById(R.id.currentCityValue);
                licenseNumberValue = itemView.findViewById(R.id.licenseNumberValue);
                driverImage = itemView.findViewById(R.id.driverImage);
            }
        }
    }


    private void DriverListAPI() {
        if (CommonUtils.isOnline(Drivers_Activity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(Drivers_Activity.this, null, getString(R.string.loading));
            DriverRequest request = new DriverRequest();
            Call<DriverJsonResponse> call = ApiExecutor.getApiService(Drivers_Activity.this).apiDriverList("Bearer " + sessonManager.getToken());

            Log.d("URLshgghd", String.valueOf(call.request().url()));
            System.out.println("API url ---" + call.request().url());
            System.out.println("API request  ---" + new Gson().toJson(request));
            call.enqueue(new Callback<DriverJsonResponse>() {
                             @Override
                             public void onResponse(Call<DriverJsonResponse> call, Response<DriverJsonResponse> response) {
                                 System.out.println("ViewVisitorType " + "API Data" + new Gson().toJson(response.body()));

                                 String respon = new Gson().toJson(response.body());
                                 Log.d("DriverListResponse", respon);

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().status != null && response.body().status.equals("success")) {
                                         String message = response.body().message;

                                         Log.d("ghdd", message);
                                         if (response.body().data.drivers != null && response.body().data.drivers.size() > 0) {
                                             arListDriver.clear();
                                             arListDriver.addAll(response.body().data.drivers);
                                             driverAdapter.notifyDataSetChanged();
                                         } else {
                                             arListDriver.clear();
                                             arListDriver.addAll(response.body().data.drivers);
                                             driverAdapter.notifyDataSetChanged();
                                         }

                                     } else {
                                         //  tv_norecord_one.setVisibility(View.VISIBLE);
                                         // RvOneWay.setVisibility(View.GONE);
                                         //  CommonUtils.showToast(Drivers_Activity.this, response.body().status);
                                     }
                                 } else {
                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     //CommonUtils.setSnackbar(tvClassName, getString(R.string.server_not_responding));
                                 }
                             }

                             @Override
                             public void onFailure(Call<DriverJsonResponse> call, Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(Drivers_Activity.this, getString(R.string.please_check_network));
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