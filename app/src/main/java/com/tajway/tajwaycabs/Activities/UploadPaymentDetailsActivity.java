package com.tajway.tajwaycabs.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.retrofitwebservices.ApiService;
import com.tajway.tajwaycabs.retrofitwebservices.RequestUrl;
import com.tajway.tajwaycabs.session.SessonManager;
import com.tajway.tajwaycabs.util.ApiFactory;
import com.tajway.tajwaycabs.util.Helper;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.media.MediaRecorder.VideoSource.CAMERA;


public class UploadPaymentDetailsActivity extends AppCompatActivity {

    ImageView upld_img;
    Button btn_SubmitPaymentDetail;
    int PICK_IMAGE_MULTIPLE = 1;
    File photoFile;
    Uri photoUri;
    String mCurrentMPath;
    ArrayList<String> imagePathList = new ArrayList<>();
    Bitmap bitmap = null;
    private String photoPath;
    String imageEncoded;
    EditText edtTransaction_id, edtAmount, edtDate;
    SessonManager sessonManager;
    ImageView img_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upload_payment_details_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //  View v = inflater.inflate(R.layout.fragment_upload_payment_details_, container, false);
        askForPermissioncamera(Manifest.permission.CAMERA, CAMERA);
        upld_img = findViewById(R.id.upld_img);
        edtTransaction_id = findViewById(R.id.edtTransaction_id);
        edtAmount = findViewById(R.id.edtAmount);
        edtDate = findViewById(R.id.edtDate);
        img_camera = findViewById(R.id.img_camera);
        btn_SubmitPaymentDetail = findViewById(R.id.btn_SubmitPaymentDetail);
        sessonManager = new SessonManager(UploadPaymentDetailsActivity.this);


        btn_SubmitPaymentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTransaction_id.getText().toString().isEmpty()) {
                    edtTransaction_id.setError("Can't be blank");
                    edtTransaction_id.requestFocus();
                } else if (edtAmount.getText().toString().isEmpty()) {
                    edtAmount.setError("Can't be blank");
                    edtAmount.requestFocus();
                } else if (edtDate.getText().toString().isEmpty()) {
                    edtDate.setError("Can't be blank");
                    edtDate.requestFocus();
                } else {
                    hitApiSubmitDetail();
                }

            }
        });
        upld_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
            }
        });


        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(UploadPaymentDetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String month, day;
                                if ((monthOfYear + 1) < 10) {
                                    month = "0" + (monthOfYear + 1);
                                } else {
                                    month = "" + (monthOfYear + 1);
                                }
                                if (dayOfMonth < 10) {
                                    day = "0" + dayOfMonth;
                                } else {
                                    day = "" + dayOfMonth;
                                }

                                edtDate.setText(year + "-" + month + "-" + day);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    public void hitApiSubmitDetail() {
        //   Log.d("sfdwfsdsfd",""+lat+"  "+lang);
        final ProgressDialog dialog = ProgressDialog.show(UploadPaymentDetailsActivity.this, null, getString(R.string.loading));


        HashMap<String, RequestBody> partMap = new HashMap<>();
        partMap.put("transaction_id", ApiFactory.getRequestBodyFromString(edtTransaction_id.getText().toString()));
        partMap.put("amount", ApiFactory.getRequestBodyFromString(edtAmount.getText().toString()));
        partMap.put("transaction_date", ApiFactory.getRequestBodyFromString(edtDate.getText().toString()));


        Log.d("partMap", "postDriverData: "+partMap.get("transaction_id"));
        Log.d("partMap", "postDriverData: "+partMap.get("amount"));
        Log.d("partMap", "postDriverData: "+partMap.get("transaction_date"));


        MultipartBody.Part[] imageArray1 = new MultipartBody.Part[imagePathList.size()];

        for (int i = 0; i < imageArray1.length; i++) {
            File file = new File(imagePathList.get(i));
            try {
                File compressedfile = new Compressor(UploadPaymentDetailsActivity.this).compressToFile(file);
                RequestBody requestBodyArray = RequestBody.create(MediaType.parse("image/*"), compressedfile);
                imageArray1[i] = MultipartBody.Part.createFormData("image", compressedfile.getName(), requestBodyArray);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + sessonManager.getToken());

        ApiService iApiServices = ApiFactory.createRetrofitInstance(RequestUrl.BASE_URL).create(ApiService.class);
        iApiServices.hitUpdateDetail(headers, imageArray1, partMap)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                        dialog.dismiss();
                    JsonObject jsonObject = response.body();
                        Log.d("responeapi..", String.valueOf(jsonObject));
                        String code = jsonObject.get("status").getAsString();


                        if (code.equals("success")) {
                            Toast.makeText(UploadPaymentDetailsActivity.this, "Information Saved successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UploadPaymentDetailsActivity.this, MainActivity.class));
                        } else {
                            String msg = jsonObject.get("msg").getAsString();
                            Toast.makeText(UploadPaymentDetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(UploadPaymentDetailsActivity.this, "fail" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        //   progressDialog.dismiss();
                        Log.d("data_error", t.getMessage());

                    }
                });
    }

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(UploadPaymentDetailsActivity.this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if (ActivityCompat.checkSelfPermission(UploadPaymentDetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UploadPaymentDetailsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE_MULTIPLE);

                } else {
                    img_camera.setVisibility(View.GONE);

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 786);
                }


            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if (ActivityCompat.checkSelfPermission(UploadPaymentDetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UploadPaymentDetailsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE_MULTIPLE);

                } else {
                    try {
                        img_camera.setVisibility(View.GONE);
                        takeCameraImg();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        myAlertDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == RESULT_OK && requestCode == 1)) {

            try {
                rotateImage();
            } catch (Exception e) {
                e.printStackTrace();

            }


        } else if ((requestCode == 786)) {
            selectFromGallery(data);
        }

    }


    private void takeCameraImg() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(UploadPaymentDetailsActivity.this.getPackageManager()) != null) {
            try {
                photoFile = createFile();
                Log.d("checkexcesdp", String.valueOf(photoFile));
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.d("checkexcep", ex.getMessage());
            }
            photoFile = createFile();
            photoUri = FileProvider.getUriForFile(UploadPaymentDetailsActivity.this, UploadPaymentDetailsActivity.this.getPackageName() + ".provider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePictureIntent, 1);
        }

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

    public void rotateImage() throws IOException {

        try {
            String photoPath = photoFile.getAbsolutePath();

            imagePathList.add(photoPath);
            bitmap = MediaStore.Images.Media.getBitmap(UploadPaymentDetailsActivity.this.getContentResolver(), photoUri);
            bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);

            if (Build.VERSION.SDK_INT > 23) {
                bitmap = handleSamplingAndRotationBitmap(UploadPaymentDetailsActivity.this, photoUri);

            } else {
                bitmap = MediaStore.Images.Media.getBitmap(UploadPaymentDetailsActivity.this.getContentResolver(), photoUri);
                bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);

            }

            upld_img.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void selectFromGallery(Intent data) {
        if (data != null) {
            try {

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (data.getClipData() != null) {
                    int imageCount = data.getClipData().getItemCount();
                    for (int i = 0; i < imageCount; i++) {
                        Uri mImageUri = data.getClipData().getItemAt(i).getUri();
                        photoPath = Helper.pathFromUri(UploadPaymentDetailsActivity.this, mImageUri);
                        imagePathList.add(photoPath);


                        // Get the cursor
                        Cursor cursor1 = UploadPaymentDetailsActivity.this.getContentResolver().query(mImageUri,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor1.moveToFirst();

                        int columnIndex1 = cursor1.getColumnIndex(filePathColumn[0]);
                        imageEncoded = cursor1.getString(columnIndex1);

                        if (Build.VERSION.SDK_INT > 23) {
                            Log.d("inelswe", "inelse");
                            bitmap = handleSamplingAndRotationBitmap(UploadPaymentDetailsActivity.this, mImageUri);

                        } else {
                            Log.d("inelse", "inelse");
                            bitmap = MediaStore.Images.Media.getBitmap(UploadPaymentDetailsActivity.this.getContentResolver(), mImageUri);
                            bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);

                        }


                        //   deedBitMap = MediaStore.Images.Media.getBitmap(UploadPaymentDetailsActivity.this.getContentResolver(), mImageUri);
                        cursor1.close();

                        upld_img.setImageBitmap(bitmap);


                    }
                } else {
                    Uri mImageUri = data.getData();
                    photoPath = Helper.pathFromUri(UploadPaymentDetailsActivity.this, mImageUri);
                    imagePathList.add(photoPath);

                    // Get the cursor
                    Cursor cursor1 = UploadPaymentDetailsActivity.this.getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor1.moveToFirst();

                    int columnIndex1 = cursor1.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor1.getString(columnIndex1);


                    if (Build.VERSION.SDK_INT > 23) {
                        Log.d("inelswe", "inelse");
                        bitmap = handleSamplingAndRotationBitmap(UploadPaymentDetailsActivity.this, mImageUri);

                    } else {
                        Log.d("inelse", "inelse");
                        bitmap = MediaStore.Images.Media.getBitmap(UploadPaymentDetailsActivity.this.getContentResolver(), mImageUri);
                        bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);

                    }

                    //  deedBitMap = MediaStore.Images.Media.getBitmap(UploadPaymentDetailsActivity.this.getContentResolver(), mImageUri);

                    cursor1.close();
                    upld_img.setImageBitmap(bitmap);


                }


            } catch (Exception e) {

                e.printStackTrace();
            }
        }

    }


    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(context, img, selectedImage);
        return img;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei = null;
        if (Build.VERSION.SDK_INT > 23) {
            ei = new ExifInterface(input);
        }


        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }


    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    private void askForPermissioncamera(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(UploadPaymentDetailsActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(UploadPaymentDetailsActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(UploadPaymentDetailsActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(UploadPaymentDetailsActivity.this, new String[]{permission}, requestCode);
            }
        } else {
//            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}