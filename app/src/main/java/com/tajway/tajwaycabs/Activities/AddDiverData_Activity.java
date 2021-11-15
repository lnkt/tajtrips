package com.tajway.tajwaycabs.Activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.apiJsonResponse.DriverCreateJsonResponse;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.requestdata.DriverCreateRequest;
import com.tajway.tajwaycabs.retrofitModel.CarListStatusModel;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDiverData_Activity extends AppCompatActivity {

    Spinner spinner1;
    Button btn_add_Done;
    EditText et_full_name_crete, et_mobile_crete, et_alternative_mobile_crete, et_model_name_crete, et_register_number_crete;
    SessonManager sessonManager;
    String carType;
    ImageView licenseImage, camera,gallery;
    ArrayList<CarListStatusModel.Data.Car> carArrayList = new ArrayList<>();
    static final int REQUEST_IMAGE_CAPTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diver_data_);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        sessonManager = new SessonManager(AddDiverData_Activity.this);
        spinner1 = findViewById(R.id.spinner1);
        btn_add_Done = findViewById(R.id.btn_add_Done);
        et_full_name_crete = findViewById(R.id.et_full_name_crete);
        et_mobile_crete = findViewById(R.id.et_mobile_crete);
        et_alternative_mobile_crete = findViewById(R.id.et_alternative_mobile_crete);
        et_model_name_crete = findViewById(R.id.et_model_name_crete);
        et_register_number_crete = findViewById(R.id.et_licence_number_create);
        licenseImage = findViewById(R.id.driverLicenseImage);

        hitCarListApi();

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                carType = carArrayList.get(i).getCarName();
//                Log.d("carType", carType);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        licenseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showAlertDialog();
            }
        });


        btn_add_Done.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //et_full_name_crete, et_mobile_crete, et_alternative_mobile_crete, et_model_name_crete, et_register_number_crete
                if(et_full_name_crete.getText().toString().isEmpty()){
                    et_full_name_crete.setError("Please enter name");
                    et_full_name_crete.getFocusable();
                }else if(et_mobile_crete.getText().toString().isEmpty()){
                    et_mobile_crete.setError("Please enter mobile Number");
                    et_mobile_crete.getFocusable();
                }else if(et_alternative_mobile_crete.getText().toString().isEmpty()){
                    et_alternative_mobile_crete.setError("Please enter alternative mobile Number");
                    et_alternative_mobile_crete.getFocusable();
                }else if(et_model_name_crete.getText().toString().isEmpty()){
                    et_model_name_crete.setError("Please enter model name");
                    et_model_name_crete.getFocusable();
                }else if(et_register_number_crete.getText().toString().isEmpty()){
                    et_register_number_crete.setError("Please enter register number");
                    et_register_number_crete.getFocusable();
                }else{
                    DriverCreateAPI();
                }
            }
        });


    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog  =  new AlertDialog.Builder(AddDiverData_Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_layout,null);
        alertDialog.setCancelable(false);
        alertDialog.setView(dialogView);
        camera =dialogView.findViewById(R.id.camera);
        gallery=dialogView.findViewById(R.id.gallery);
        AlertDialog alertDialogProfile = alertDialog.create();
        alertDialogProfile.show();
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                alertDialogProfile.cancel();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureFromGallery();
                alertDialogProfile.cancel();
            }
        });
    }

    private void takePictureFromGallery() {
        Intent pickphoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickphoto.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(pickphoto,1);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            Log.d("camera","working");
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            Log.e("error",e.toString());
        }
    }

    private void hitCarListApi() {

        if (CommonUtils.isOnline(AddDiverData_Activity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(AddDiverData_Activity.this, null, getString(R.string.loading));

            Call<CarListStatusModel> call = ApiExecutor.getApiService(AddDiverData_Activity.this).carList("Bearer " + sessonManager.getToken());

            call.enqueue(new Callback<CarListStatusModel>() {
                             @Override
                             public void onResponse(@NotNull Call<CarListStatusModel> call, @NotNull Response<CarListStatusModel> response) {
                                 // System.out.println("ViewVisitorType " + "API Data" + new Gson().toJson(response.body()));

                                 String respon = new Gson().toJson(response.body());
                                 Log.d("OneWaResponse", respon);

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().getStatus() != null && response.body().getStatus().equals("success")) {

                                         carArrayList = response.body().getData().getCars();
                                         ArrayList<String> list = new ArrayList<>();
                                         for (int i = 0; i < carArrayList.size(); i++) {
                                             list.add(carArrayList.get(i).getCarName());
                                         }

                                         ArrayAdapter<String> adapter = new ArrayAdapter(AddDiverData_Activity.this, android.R.layout.simple_spinner_dropdown_item, list);
                                         spinner1.setAdapter(adapter);

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
                             public void onFailure(@NotNull Call<CarListStatusModel> call, @NotNull Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(AddDiverData_Activity.this, getString(R.string.please_check_network));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selected  = data.getData();
                    licenseImage.setImageURI(selected);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    licenseImage.setImageBitmap(imageBitmap);
                }

        }
    }

    private void DriverCreateAPI() {
        if (CommonUtils.isOnline(AddDiverData_Activity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(AddDiverData_Activity.this, null, getString(R.string.loading));
            DriverCreateRequest request = new DriverCreateRequest();
            request.setDriver_name(et_full_name_crete.getText().toString());
            request.setDriver_mobile(et_mobile_crete.getText().toString());
            request.setDriveralternate_no(et_mobile_crete.getText().toString());
            request.setCar_type(carType);
            request.setModel_name(et_model_name_crete.getText().toString());
            request.setRegistration_number(et_register_number_crete.getText().toString());
            Log.e("API url ---", "request--" + request.toString());

            Call<DriverCreateJsonResponse> call = ApiExecutor.getApiService(AddDiverData_Activity.this).apiDriverCreate("Bearer " + sessonManager.getToken(), request);
            System.out.println("API url ---" + call.request().url());
            System.out.println("API request  ---" + new Gson().toJson(request));
            call.enqueue(new Callback<DriverCreateJsonResponse>() {
                             @Override
                             public void onResponse(@NotNull Call<DriverCreateJsonResponse> call, @NotNull Response<DriverCreateJsonResponse> response) {
                                 String respon = new Gson().toJson(response.body());
                                 dialog.dismiss();
                                 Log.d("responCreate", respon);
                                 System.out.println("MobileVerifyActivity" + "API Data" + new Gson().toJson(response.body()));

                                 if (response.body() != null) {
                                     if (response.body().status != null && response.body().status.equals("success")) {
                                         String message = response.body().message;
                                         Toast.makeText(AddDiverData_Activity.this, message, Toast.LENGTH_SHORT).show();
                                         Intent intent = new Intent(AddDiverData_Activity.this, MainActivity.class);
                                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                         startActivity(intent);


                                     } else {
                                         CommonUtils.showToast(AddDiverData_Activity.this, response.body().message);

                                     }
                                 } else {
                                     if (dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     // CommonUtils.setSnackbar(tvLogin, getString(R.string.server_not_responding));
                                 }


                             }

                             @Override
                             public void onFailure(@NotNull Call<DriverCreateJsonResponse> call, @NotNull Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(AddDiverData_Activity.this, getString(R.string.please_check_network));
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