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

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.apiJsonResponse.DriverCreateJsonResponse;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.requestdata.DriverCreateRequest;
import com.tajway.tajwaycabs.retrofitModel.CarListStatusModel;
import com.tajway.tajwaycabs.retrofitModel.CarStore;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.retrofitwebservices.Apiclient;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    String []imagePathList = new String[1];
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
                    //showAlertDialog();
                ImagePicker.with(AddDiverData_Activity.this)
                        //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(300, 300)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

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
//                }else if(et_model_name_crete.getText().toString().isEmpty()){
//                    et_model_name_crete.setError("Please enter model name");
//                    et_model_name_crete.getFocusable();
                }else if(et_register_number_crete.getText().toString().isEmpty()){
                    et_register_number_crete.setError("Please enter register number");
                    et_register_number_crete.getFocusable();
                }else{
                  //  DriverCreateAPI();
                    postDriverData();
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
    public void postDriverData() {
        HashMap<String, RequestBody> partMap = new HashMap<>();
//
//        status = "1";
//
//        request.setDriver_name(et_full_name_crete.getText().toString());
//        request.setDriver_mobile(et_mobile_crete.getText().toString());
//        request.setDriveralternate_no(et_mobile_crete.getText().toString());
//        request.setLicence_no(et_register_number_crete.getText().toString());

        partMap.put("driver_name", Apiclient.getRequestBodyFromString(et_full_name_crete.getText().toString()));
        partMap.put("driver_mobile", Apiclient.getRequestBodyFromString(et_mobile_crete.getText().toString()));
        partMap.put("driveralternate_no", Apiclient.getRequestBodyFromString(et_mobile_crete.getText().toString()));
       // partMap.put("car_type", Apiclient.getRequestBodyFromString(vehicleNumber));
        partMap.put("licence_no", Apiclient.getRequestBodyFromString(et_register_number_crete.getText().toString()));

//        Log.d("partmap", "postDriverData: "+status);
//        Log.d("partmap", "postDriverData: "+enterModelName);
//        Log.d("partmap", "postDriverData: "+spinnerText);
//        Log.d("partmap", "postDriverData: "+vehicleNumber);
//        Log.d("partmap", "postDriverData: "+registrationYear);
//
//
//        Log.d("partmap", "postDriverData: "+partMap.get("isactive"));
//        Log.d("partmap", "postDriverData: "+partMap.get("car_name"));
//        Log.d("partmap", "postDriverData: "+partMap.get("vehicle_type"));
//        Log.d("partmap", "postDriverData: "+partMap.get("vehicle_reg_no"));
//        Log.d("partmap", "postDriverData: "+partMap.get("registration_year"));


        MultipartBody.Part[] imageArray1 = new MultipartBody.Part[imagePathList.length];

        /*for (int i = 0; i < imageArray1.length; i++) {
            Log.d("response==", "postDriverData: " + imagePathList.get(i));
            File file = new File(imagePathList.get(i));


            try {
                File compressedfile = new Compressor(CarsActivity.this).compressToFile(file);
                RequestBody requestBodyArray = RequestBody.create(MediaType.parse("image/*"), compressedfile);
                imageArray1[i] = MultipartBody.Part.createFormData("image", compressedfile.getName(), requestBodyArray);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("response==", "postDriverData: " + e.toString());
            }
        }*/
        try {
            File file1 = new File(imagePathList[0]);
           // File file2 = new File(imagePathList[1]);
            //File file3 = new File(imagePathList[2]);
            Log.d("file==", "postDriverData: "+file1.exists());
            Log.d("file==", "postDriverData: "+file1.getAbsolutePath());
            Log.d("file==", "postDriverData: "+file1.getName());
//            Log.d("file==", "postDriverData: "+file2.exists());
//            Log.d("file==", "postDriverData: "+file3.exists());
//
//            Log.d("file==", "postDriverData: "+file2.getAbsolutePath());
//            Log.d("file==", "postDriverData: "+file3.getAbsolutePath());
//
//            Log.d("file==", "postDriverData: "+file2.getName());
//            Log.d("file==", "postDriverData: "+file3.getName());


            //
            File compressedfile = new Compressor(AddDiverData_Activity.this).compressToFile(file1);
            RequestBody requestBodyArray = RequestBody.create(MediaType.parse("image/*"), compressedfile);
            imageArray1[0] = MultipartBody.Part.createFormData("image", compressedfile.getName(), requestBodyArray);

            Log.d("file==", "compreseddata: "+compressedfile.getName());
//
//
//            File compressedfile1 = new Compressor(AddDiverData_Activity.this).compressToFile(file2);
//            RequestBody requestBodyArray1 = RequestBody.create(MediaType.parse("image/*"), compressedfile1);
//            imageArray1[1] = MultipartBody.Part.createFormData("file_insurance", compressedfile.getName(), requestBodyArray1);
//
//            File compressedfile2 = new Compressor(CarsActivity.this).compressToFile(file3);
//            RequestBody requestBodyArray2 = RequestBody.create(MediaType.parse("image/*"), compressedfile2);
//            imageArray1[2] = MultipartBody.Part.createFormData("file_permite", compressedfile.getName(), requestBodyArray2);



            //  filePart1 = MultipartBody.Part.createFormData("file_rc", file1.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file1));
            //   filePart2 = MultipartBody.Part.createFormData("file_insurance", file2.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file2));
            //   filePart3 = MultipartBody.Part.createFormData("file_permite", file3.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file3));
        }catch (Exception e){
            Log.d("file", "postDriverData: "+e.getMessage());
        }

        // Log.d("multipart", "postDriverData: "+filePart1);
        // Log.d("multipart", "postDriverData: "+filePart2);
        // Log.d("multipart", "postDriverData: "+filePart3);

        Call<DriverCreateJsonResponse> call = Apiclient.getMyService().apiDriverCreate("Bearer " + sessonManager.getToken(),partMap, imageArray1);
        call.enqueue(new Callback<DriverCreateJsonResponse>() {

            @Override
            public void onResponse(Call<DriverCreateJsonResponse> call, Response<DriverCreateJsonResponse> response) {
                //Toast.makeText(CarsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                //Log.d("response==", "onResponse: " + response.body().getStatus());
                Log.d("responsethis", "onResponse: " + response.code());
                Log.d("responsethis", "onResponse: " + sessonManager.getToken());
                if (response.body().getStatus().equalsIgnoreCase("success")){
                    Toast.makeText(AddDiverData_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AddDiverData_Activity.this,AddDriverActivity.class);
//                    startActivity(intent);
//                    finish();
                    onBackPressed();
                }
                //Log.d("responsethis", "onResponse: " + response.errorBody());
            }

            @Override
            public void onFailure(Call<DriverCreateJsonResponse> call, Throwable t) {
                Log.d("responsethis", "onFailure: " + t.getMessage());
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
        Log.d("check", "onActivityResult: " + resultCode + " " + requestCode + " " + data.toString());
        Uri selected = data.getData();

            if(resultCode == RESULT_OK){
                licenseImage.setImageURI(selected);
                imagePathList[0] = selected.getPath();
                //imagePathList.add(0, selected.getPath());
                Log.d("imagePathList", "onActivityResult: " + selected.getPath());
            }


    }

    private void DriverCreateAPI() {
//        if (CommonUtils.isOnline(AddDiverData_Activity.this)) {
//            final ProgressDialog dialog = ProgressDialog.show(AddDiverData_Activity.this, null, getString(R.string.loading));
//            DriverCreateRequest request = new DriverCreateRequest();
//            request.setDriver_name(et_full_name_crete.getText().toString());
//            request.setDriver_mobile(et_mobile_crete.getText().toString());
//            request.setDriveralternate_no(et_mobile_crete.getText().toString());
//            request.setLicence_no(et_register_number_crete.getText().toString());
//           // request.setCar_type(carType);
//           // request.setModel_name(et_model_name_crete.getText().toString());
//           // request.setRegistration_number(et_register_number_crete.getText().toString());
//            Log.e("API url ---", "request--" + request.toString());
//
////            Call<DriverCreateJsonResponse> call = ApiExecutor.getApiService(AddDiverData_Activity.this).apiDriverCreate("Bearer " + sessonManager.getToken(), request);
//            System.out.println("API url ---" + call.request().url());
//            System.out.println("API request  ---" + new Gson().toJson(request));
//            call.enqueue(new Callback<DriverCreateJsonResponse>() {
//                             @Override
//                             public void onResponse(@NotNull Call<DriverCreateJsonResponse> call, @NotNull Response<DriverCreateJsonResponse> response) {
//                                 String respon = new Gson().toJson(response.body());
//                                 dialog.dismiss();
//                                 Log.d("responCreate", respon);
//                                 System.out.println("MobileVerifyActivity" + "API Data" + new Gson().toJson(response.body()));
//
//                                 if (response.body() != null) {
//                                     if (response.body().status != null && response.body().status.equals("success")) {
//                                         String message = response.body().message;
//                                         Toast.makeText(AddDiverData_Activity.this, message, Toast.LENGTH_SHORT).show();
//                                         Intent intent = new Intent(AddDiverData_Activity.this, MainActivity.class);
//                                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                         startActivity(intent);
//
//
//                                     } else {
//                                         CommonUtils.showToast(AddDiverData_Activity.this, response.body().message);
//
//                                     }
//                                 } else {
//                                     if (dialog.isShowing()) {
//                                         dialog.dismiss();
//                                     }
//                                     // CommonUtils.setSnackbar(tvLogin, getString(R.string.server_not_responding));
//                                 }
//
//
//                             }
//
//                             @Override
//                             public void onFailure(@NotNull Call<DriverCreateJsonResponse> call, @NotNull Throwable t) {
//                                 if (dialog != null && dialog.isShowing()) {
//                                     dialog.dismiss();
//                                 }
//                                 System.out.println("API Data Error : " + t.getMessage());
//                             }
//                         }
//            );
//        } else {
//            CommonUtils.showToastInCenter(AddDiverData_Activity.this, getString(R.string.please_check_network));
//        }
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