package com.tajway.tajwaycabs.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.common.internal.service.Common;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.retrofitModel.CarStore;
import com.tajway.tajwaycabs.retrofitwebservices.Apiclient;
import com.tajway.tajwaycabs.session.SessonManager;
import com.tajway.tajwaycabs.util.ApiFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    ImageView rc, insurance, permit, gallery, camera;
    Button submit;
    EditText edt_vehicleNumber, edt_enterModelName, edt_registrationYear;
    Spinner spinner;
    File photoFile, compressedFileRc,compressedFileInsurance,compressedFilePermit;
    Uri photoUri;
    MultipartBody.Part filePart1,filePart2,filePart3;
    //List<String> imagePathList = new ArrayList<>();
    String []imagePathList = new String[3];
    static final int REQUEST_IMAGE_CAPTURE = 2;
    int count;
    String mCurrentMPath, status, vehicleNumber, enterModelName, registrationYear, spinnerText;
    SessonManager sessonManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);


        //hooks
        rc = findViewById(R.id.rcImage);
        insurance = findViewById(R.id.insuranceImage);
        permit = findViewById(R.id.permitImage);
        submit = findViewById(R.id.submitCarDetails);
        edt_vehicleNumber = findViewById(R.id.et_vehicleNumber);
        edt_enterModelName = findViewById(R.id.et_modelName);
        edt_registrationYear = findViewById(R.id.et_registrationYear);

        spinner = findViewById(R.id.carType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CarsActivity.this, R.array.cartype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        sessonManager = new SessonManager(CarsActivity.this);


        rc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                //imageCapture();
                ImagePicker.with(CarsActivity.this)
                        //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(300, 300)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(10);


            }
        });
        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(CarsActivity.this)
                                         //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(300, 300)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(11);
                //imageCapture();
                count = 1;
            }
        });
        permit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(CarsActivity.this)
                                           //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(300, 300)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(12);

                //imageCapture();
                count = 2;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_vehicleNumber.getText().toString().isEmpty()) {
                    edt_vehicleNumber.setError("Please enter vehicle number");
                    edt_vehicleNumber.requestFocus();
                } else if (edt_enterModelName.getText().toString().isEmpty()) {
                    edt_enterModelName.setError("Please enter model name");
                    edt_enterModelName.requestFocus();
                } else if (edt_registrationYear.getText().toString().isEmpty()) {
                    edt_registrationYear.setError("Please enter registration year");
                    edt_registrationYear.requestFocus();

                } else {
                    vehicleNumber = edt_vehicleNumber.getText().toString();
                    registrationYear = edt_registrationYear.getText().toString();
                    enterModelName = edt_enterModelName.getText().toString();
                    postDriverData();
                }

            }
        });


    }

    public void postDriverData() {
        HashMap<String, RequestBody> partMap = new HashMap<>();

        status = "1";


        partMap.put("isactive", Apiclient.getRequestBodyFromString(status));
        partMap.put("car_name", Apiclient.getRequestBodyFromString(enterModelName));
        partMap.put("car_type", Apiclient.getRequestBodyFromString(spinnerText));
        partMap.put("vehicle_number", Apiclient.getRequestBodyFromString(vehicleNumber));
        partMap.put("registration_year", Apiclient.getRequestBodyFromString(registrationYear));

        Log.d("partmap", "postDriverData: "+status);
        Log.d("partmap", "postDriverData: "+enterModelName);
        Log.d("partmap", "postDriverData: "+spinnerText);
        Log.d("partmap", "postDriverData: "+vehicleNumber);
        Log.d("partmap", "postDriverData: "+registrationYear);


        Log.d("partmap", "postDriverData: "+partMap.get("isactive"));
        Log.d("partmap", "postDriverData: "+partMap.get("car_name"));
        Log.d("partmap", "postDriverData: "+partMap.get("vehicle_type"));
        Log.d("partmap", "postDriverData: "+partMap.get("vehicle_reg_no"));
        Log.d("partmap", "postDriverData: "+partMap.get("registration_year"));


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
            File file2 = new File(imagePathList[1]);
            File file3 = new File(imagePathList[2]);
            Log.d("file==", "postDriverData: "+file1.exists());
            Log.d("file==", "postDriverData: "+file2.exists());
            Log.d("file==", "postDriverData: "+file3.exists());
            Log.d("file==", "postDriverData: "+file1.getAbsolutePath());
            Log.d("file==", "postDriverData: "+file2.getAbsolutePath());
            Log.d("file==", "postDriverData: "+file3.getAbsolutePath());
            Log.d("file==", "postDriverData: "+file1.getName());
            Log.d("file==", "postDriverData: "+file2.getName());
            Log.d("file==", "postDriverData: "+file3.getName());


            //
            File compressedfile = new Compressor(CarsActivity.this).compressToFile(file1);
            RequestBody requestBodyArray = RequestBody.create(MediaType.parse("image/*"), compressedfile);
            imageArray1[0] = MultipartBody.Part.createFormData("file_rc", compressedfile.getName(), requestBodyArray);

            Log.d("file==", "compreseddata: "+compressedfile.getName());


            File compressedfile1 = new Compressor(CarsActivity.this).compressToFile(file2);
            RequestBody requestBodyArray1 = RequestBody.create(MediaType.parse("image/*"), compressedfile1);
            imageArray1[1] = MultipartBody.Part.createFormData("file_insurance", compressedfile.getName(), requestBodyArray1);

            File compressedfile2 = new Compressor(CarsActivity.this).compressToFile(file3);
            RequestBody requestBodyArray2 = RequestBody.create(MediaType.parse("image/*"), compressedfile2);
            imageArray1[2] = MultipartBody.Part.createFormData("file_permite", compressedfile.getName(), requestBodyArray2);



          //  filePart1 = MultipartBody.Part.createFormData("file_rc", file1.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file1));
          //   filePart2 = MultipartBody.Part.createFormData("file_insurance", file2.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file2));
          //   filePart3 = MultipartBody.Part.createFormData("file_permite", file3.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file3));
        }catch (Exception e){
            Log.d("file", "postDriverData: "+e.getMessage());
        }

       // Log.d("multipart", "postDriverData: "+filePart1);
       // Log.d("multipart", "postDriverData: "+filePart2);
       // Log.d("multipart", "postDriverData: "+filePart3);

        Call<CarStore> call = Apiclient.getMyService().addCar("Bearer " + sessonManager.getToken(),partMap, imageArray1);
        call.enqueue(new Callback<CarStore>() {

            @Override
            public void onResponse(Call<CarStore> call, Response<CarStore> response) {
                //Toast.makeText(CarsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                //Log.d("response==", "onResponse: " + response.body().getStatus());
                Log.d("responsethis", "onResponse: " + response.code());
                Log.d("responsethis", "onResponse: " + sessonManager.getToken());
                if (response.body().getStatus().equalsIgnoreCase("success")){
                    Toast.makeText(CarsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CarsActivity.this,CarList.class);
                    startActivity(intent);
                    finish();
                    //onBackPressed();
                }
                //Log.d("responsethis", "onResponse: " + response.errorBody());
            }

            @Override
            public void onFailure(Call<CarStore> call, Throwable t) {
                Log.d("responsethis", "onFailure: " + t.getMessage());
            }
        });
    }

    public void imageCapture() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CarsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_layout, null);
        alertDialog.setCancelable(false);
        alertDialog.setView(dialogView);
        camera = dialogView.findViewById(R.id.camera);
        gallery = dialogView.findViewById(R.id.gallery);
        AlertDialog alertDialogProfile = alertDialog.create();
        alertDialogProfile.show();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Default Request Code is ImagePicker.REQUEST_CODE


                try {
                    dispatchTakePictureIntent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        startActivityForResult(pickphoto, 1);
    }

    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(CarsActivity.this.getPackageManager()) != null) {
            try {
                photoFile = createFile();
                Log.d("checkexcesdp", String.valueOf(photoFile));
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.d("checkexcep", ex.getMessage());
            }
            photoFile = createFile();
            photoUri = FileProvider.getUriForFile(CarsActivity.this, CarsActivity.this.getPackageName() + ".provider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePictureIntent, 1);
        }

//        try {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            Log.d("camera","working");
//        } catch (ActivityNotFoundException e) {
//            // display error state to the user
//            Log.e("error",e.toString());
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("check", "onActivityResult: " + resultCode + " " + requestCode + " " + data.toString());
        Uri selected = data.getData();
        if (requestCode == 10) {
            if(resultCode == RESULT_OK){
                rc.setImageURI(selected);
                imagePathList[0] = selected.getPath();
                //imagePathList.add(0, selected.getPath());
                Log.d("imagePathList", "onActivityResult: " + selected.getPath());
            }

        }
        if (requestCode == 11) {
            if(resultCode == RESULT_OK) {
                insurance.setImageURI(selected);
                //imagePathList.add(1, selected.getPath());
                imagePathList[1] = selected.getPath();
            }
        }
        if (requestCode == 12) {
            if(resultCode == RESULT_OK) {
                permit.setImageURI(selected);
                //imagePathList.add(2, selected.getPath());
                imagePathList[2] = selected.getPath();
            }
        }


//        switch (requestCode){
//            case 1:
//
//                if (resultCode == RESULT_OK) {
//                    Uri selected  = data.getData();
//                    if(count==0){
//                        rc.setImageURI(selected);
//                        imagePathList.add(0,selected.getPath());
//                        Log.d("imagePathList", "onActivityResult: "+selected.getPath());
//                    }
//                    if(count==1){
//                        insurance.setImageURI(selected);
//                        imagePathList.add(1,selected.getPath());
//                    }
//                    if(count==2){
//                        permit.setImageURI(selected);
//                        imagePathList.add(2,selected.getPath());
//                    }
//
//                }
//                break;
//            case 2:
//                if (resultCode == RESULT_OK) {
//                    Bundle extras = data.getExtras();
//                    Bitmap imageBitmap = (Bitmap) extras.get("data");
//                   // Bitmap b = CommonUtils.getRoundedCornerBitmap(imageBitmap,10);
//                    if(count==0){
//                        rc.setImageBitmap(imageBitmap);
//                        imagePathList.add(0,imageBitmap.toString());
//
//                    }
//                    if(count==1){
//                        insurance.setImageBitmap(imageBitmap);
//                        imagePathList.add(1,imageBitmap.toString());
//                    }
//                    if(count==2){
//                        permit.setImageBitmap(imageBitmap);
//                        imagePathList.add(2,imageBitmap.toString());
//                    }
//
//                }
//                break;
//
//
//        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Integer nid = ((int) id);
        switch (nid) {
            case 0:
                spinnerText = "Sedan";
                break;
            case 1:
                spinnerText = "SUV";
                break;
            case 2:
                spinnerText = "HatchBack";
                break;
        }
        Log.d("spinner", "onItemSelected: " + spinnerText);
        Log.d("spinner2", "onItemSelected: " + String.valueOf(id));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        spinnerText = "Sedan";

    }

    private File createFile() throws IOException {
        String imageFileName = "GOOGLES" + System.currentTimeMillis();
        String storageDir = Environment.getExternalStorageDirectory() + "/skImages";
        Log.d("storagepath===", storageDir);
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();

        File image = new File(storageDir + "/" + imageFileName + ".jpg");
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentMPath = image.getAbsolutePath();
        return image;
    }
}