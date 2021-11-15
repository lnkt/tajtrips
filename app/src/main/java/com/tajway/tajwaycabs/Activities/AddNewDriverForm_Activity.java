package com.tajway.tajwaycabs.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.tajway.tajwaycabs.R;

public class AddNewDriverForm_Activity extends AppCompatActivity {

    ImageView img_First, img_Second, img_third, img_fourth,img_fifth;
    TextView tv_text1,tv_text2,tv_text3,tv_text4;
    Button btn_SubmitDriverDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_driver_form_);


        img_First = findViewById(R.id.img_First);
        img_Second = findViewById(R.id.img_Second);
        img_third = findViewById(R.id.img_third);
        img_fourth = findViewById(R.id.img_fourth);
        img_fifth = findViewById(R.id.img_fifth);
        tv_text1 = findViewById(R.id.tv_text1);
        tv_text2 = findViewById(R.id.tv_text2);
        tv_text3 = findViewById(R.id.tv_text3);
        tv_text4 = findViewById(R.id.tv_text4);
        btn_SubmitDriverDetail = findViewById(R.id.btn_SubmitDriverDetail);

        btn_SubmitDriverDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewDriverForm_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });








        img_First.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(AddNewDriverForm_Activity.this, img_First);
                popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Camera:
                                try {
                                    Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(i, 1);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case R.id.Gallery:

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_PICK);
                                startActivityForResult(intent, 5);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


        img_Second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 2);*/


                PopupMenu popupMenu = new PopupMenu(AddNewDriverForm_Activity.this, img_Second);
                popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Camera:
                                try {
                                    Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(i, 2);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case R.id.Gallery:

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_PICK);
                                startActivityForResult(intent, 6);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

        });


        img_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 3);*/


                PopupMenu popupMenu = new PopupMenu(AddNewDriverForm_Activity.this, img_third);
                popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Camera:
                                try {
                                    Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(i, 3);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case R.id.Gallery:

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_PICK);
                                startActivityForResult(intent, 7);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });




        img_fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 4);*/


                PopupMenu popupMenu = new PopupMenu(AddNewDriverForm_Activity.this, img_fourth);
                popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Camera:
                                try {
                                    Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(i, 4);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case R.id.Gallery:

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_PICK);
                                startActivityForResult(intent, 8);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }




    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bitmap userImage1 = (Bitmap) data.getExtras().get("data");
                    Bundle extras = data.getExtras();
                    userImage1 = (Bitmap) extras.get("data");
                    img_First.setImageBitmap(userImage1);
                    tv_text1.setVisibility(View.GONE);
                }//if resultCode Case 1
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Bitmap userImage2 = (Bitmap) data.getExtras().get("data");
                    Bundle extras = data.getExtras();
                    userImage2 = (Bitmap) extras.get("data");
                    img_Second.setImageBitmap(userImage2);
                    tv_text2.setVisibility(View.GONE);
                }
                break;

            case 3:
                if (resultCode == RESULT_OK) {
                    Bitmap userImage3 = (Bitmap) data.getExtras().get("data");
                    Bundle extras = data.getExtras();
                    userImage3 = (Bitmap) extras.get("data");
                    img_third.setImageBitmap(userImage3);
                    tv_text3.setVisibility(View.GONE);
                }
                break;
            case 4:
                if (resultCode == RESULT_OK) {
                    Bitmap userImage4 = (Bitmap) data.getExtras().get("data");
                    Bundle extras = data.getExtras();
                    userImage4 = (Bitmap) extras.get("data");
                    img_fourth.setImageBitmap(userImage4);
                    tv_text4.setVisibility(View.GONE);
                }
                break;
            case 5:
                if (resultCode == RESULT_OK) {

                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    img_First.setImageURI(selectedImage);
                    tv_text1.setVisibility(View.GONE);
                }
                break;
            case 6:
                if (resultCode == RESULT_OK) {

                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    img_Second.setImageURI(selectedImage);
                    tv_text2.setVisibility(View.GONE);
                }
                break;

            case 7:
                if (resultCode == RESULT_OK) {
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    img_third.setImageURI(selectedImage);
                    tv_text3.setVisibility(View.GONE);
                }
                break;
            case 8:
                if (resultCode == RESULT_OK) {

                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    img_fourth.setImageURI(selectedImage);
                    tv_text4.setVisibility(View.GONE);
                }
                break;
        }
    }




}