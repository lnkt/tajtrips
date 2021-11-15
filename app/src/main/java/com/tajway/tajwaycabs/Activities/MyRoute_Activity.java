package com.tajway.tajwaycabs.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.retrofitModel.GoingHomeTripList;
import com.tajway.tajwaycabs.retrofitModel.GoingHomeServiceModel;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRoute_Activity extends AppCompatActivity {

    Spinner spinner1;
    SessonManager sessonManager;
    ArrayList<GoingHomeTripList.Data.Trip> list = new ArrayList<>();
    EditText edtCarType, edtFromCity, edtToCity, edtInclusive, edtFromTime, edtFromDate, edtToTime, edtToDate;
    Button btn_routSubmit;
    String tripType;


  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_route_, container, false);*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_route_);
        getSupportActionBar().setTitle("Going Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    sessonManager = new SessonManager(MyRoute_Activity.this);
        spinner1 = findViewById(R.id.spinner1);
        edtCarType = findViewById(R.id.edtCarType);
        edtFromCity = findViewById(R.id.edtFromCity);
        edtToCity = findViewById(R.id.edtToCity);
        edtInclusive = findViewById(R.id.edtInclusive);
        edtFromTime = findViewById(R.id.edtFromTime);
        edtFromDate = findViewById(R.id.edtFromDate);
        edtToTime = findViewById(R.id.edtToTime);
        edtToDate = findViewById(R.id.edtToDate);
        btn_routSubmit = findViewById(R.id.btn_routSubmit);


        hitApi();

        onClick();
     //   return v;
    }

    private void onClick() {
        edtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateDialog(0);

            }
        });
        edtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateDialog(1);

            }
        });

        edtFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeDialog(0);

            }
        });
        edtToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeDialog(1);

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tripType = list.get(position).getName();
              //  Log.d("adsdasas",tripType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_routSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tripType.equals("Select Trip type")){
                    Toast.makeText(MyRoute_Activity.this, "Please Select Trip type", Toast.LENGTH_SHORT).show();
                }
                else if(edtCarType.getText().toString().isEmpty()){
                    edtCarType.setError("Please Enter Car type");
                    edtCarType.requestFocus();
                }
                else if(edtFromCity.getText().toString().isEmpty()){
                    edtFromCity.setError("Please Enter From City");
                    edtFromCity.requestFocus();
                }
                else if(edtToCity.getText().toString().isEmpty()){
                    edtToCity.setError("Please Enter To City");
                    edtToCity.requestFocus();
                }
                else if(edtInclusive.getText().toString().isEmpty()){
                    edtInclusive.setError("Please Enter All Inclusive");
                    edtInclusive.requestFocus();
                }
                else if(edtFromTime.getText().toString().isEmpty()){
                    edtFromTime.setError("Please Enter From Time");
                    edtFromTime.requestFocus();
                }
                else if(edtFromDate.getText().toString().isEmpty()){
                    edtFromDate.setError("Please Enter From Date");
                    edtFromDate.requestFocus();
                }
                else if(edtToTime.getText().toString().isEmpty()){
                    edtToTime.setError("Please Enter To Time");
                    edtToTime.requestFocus();
                }
                else if(edtToDate.getText().toString().isEmpty()){
                    edtToDate.setError("Please Enter To Date");
                    edtToDate.requestFocus();
                }
                else {
                     hitSubmitApi();
                }
            }
        });
    }

    private void hitSubmitApi() {

        if (CommonUtils.isOnline(MyRoute_Activity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(MyRoute_Activity.this, null, getString(R.string.loading));

            Call<GoingHomeServiceModel> call = ApiExecutor.getApiService(MyRoute_Activity.this).postGoingHome("Bearer " + sessonManager.getToken(),tripType,edtFromCity.getText().toString(),
                    edtToCity.getText().toString(),edtInclusive.getText().toString(),edtFromDate.getText().toString(),edtFromTime.getText().toString(),
                    edtToDate.getText().toString(),edtToTime.getText().toString(),edtCarType.getText().toString());


            call.enqueue(new Callback<GoingHomeServiceModel>() {
                             @Override
                             public void onResponse(Call<GoingHomeServiceModel> call, Response<GoingHomeServiceModel> response) {
                                 // System.out.println("ViewVisitorType " + "API Data" + new Gson().toJson(response.body()));

                                 String respon = new Gson().toJson(response.body());
          //                       Log.d("OneWaResponse", respon);
                                 GoingHomeServiceModel model = response.body();

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().getStatus() != null && response.body().getStatus().equals("success")) {

                                         Toast.makeText(MyRoute_Activity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                     } else {
                                         if (dialog != null && dialog.isShowing()) {
                                             dialog.dismiss();
                                         }
                                         //Log.d("sdsasadsda",response.message());
                                         Toast.makeText(MyRoute_Activity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             }
                             @Override
                             public void onFailure(Call<GoingHomeServiceModel> call, Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                               //  System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(MyRoute_Activity.this, getString(R.string.please_check_network));
        }
    }


    private void dateDialog(final int requestCode){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(MyRoute_Activity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String month,day;
                        if((monthOfYear + 1)<10){
                            month ="0"+(monthOfYear + 1);
                        }else {
                            month =""+(monthOfYear + 1);
                        }
                        if(dayOfMonth<10){
                            day = "0"+dayOfMonth;
                        }else {
                            day = ""+dayOfMonth;
                        }

                        if(requestCode==0){
                            edtFromDate.setText(year + "-" + month + "-" + day);
                        }else {
                            edtToDate.setText(year + "-" + month + "-" + day);
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timeDialog(final int recuestCode){
        final Calendar c = Calendar.getInstance();
       final int mHour = c.get(Calendar.HOUR_OF_DAY);
       int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(MyRoute_Activity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {


                        String am_pm = "";

                        Calendar datetime = Calendar.getInstance();
                        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        datetime.set(Calendar.MINUTE, minute);

                        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                            am_pm = "AM";
                        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                            am_pm = "PM";

                        String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ?"12":datetime.get(Calendar.HOUR)+"";

                        int hour = Integer.parseInt(strHrsToShow);
                        if(hour<10){
                            strHrsToShow = "0"+hour;
                        }

                        if(recuestCode==0){
                            //  edtFromTime.setText(hourOfDay + ":" + minute);
                            edtFromTime.setText(strHrsToShow+":"+datetime.get(Calendar.MINUTE)+" "+am_pm );


                        }else {
                            edtToTime.setText(strHrsToShow+":"+datetime.get(Calendar.MINUTE)+" "+am_pm );
                        }

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void hitApi() {
        GoingHomeTripList.Data.Trip model = new GoingHomeTripList.Data.Trip();
        model.setId(0);
        model.setName("Select Trip type");
        list.add(0, model);

        if (CommonUtils.isOnline(MyRoute_Activity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(MyRoute_Activity.this, null, getString(R.string.loading));

            Call<GoingHomeTripList> call = ApiExecutor.getApiService(MyRoute_Activity.this).apiTripList("Bearer " + sessonManager.getToken());

            call.enqueue(new Callback<GoingHomeTripList>() {
                             @Override
                             public void onResponse(Call<GoingHomeTripList> call, Response<GoingHomeTripList> response) {
                                 // System.out.println("ViewVisitorType " + "API Data" + new Gson().toJson(response.body()));

                                 String respon = new Gson().toJson(response.body());
                                 Log.d("OneWaResponse", respon);

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().getStatus() != null && response.body().getStatus().equals("success")) {

                                         if (response.body().getData() != null) {
                                             list.addAll(response.body().getData().getTrips());

                                             ArrayList<String> listData = new ArrayList<>();

                                             for (int i = 0; i < list.size(); i++) {
                                                 listData.add(list.get(i).getName());
                                             }

                                             ArrayAdapter<String> adapter = new ArrayAdapter(MyRoute_Activity.this, android.R.layout.simple_spinner_dropdown_item, listData);
                                             spinner1.setAdapter(adapter);

                                         } else {

//                                             tv_norecord_local.setVisibility(View.VISIBLE);
//                                             rvInfo.setVisibility(View.GONE);
                                         }
                                     } else {
//                                         tv_norecord_local.setVisibility(View.VISIBLE);
//                                         rvInfo.setVisibility(View.GONE);
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
                             public void onFailure(Call<GoingHomeTripList> call, Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(MyRoute_Activity.this, getString(R.string.please_check_network));
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