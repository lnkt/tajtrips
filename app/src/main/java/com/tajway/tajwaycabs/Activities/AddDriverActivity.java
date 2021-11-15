package com.tajway.tajwaycabs.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tajway.tajwaycabs.R;

public class AddDriverActivity extends AppCompatActivity {

    Button btn_submitDriverNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        btn_submitDriverNumber = findViewById(R.id.btn_submitDriverNumber);


        btn_submitDriverNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddDriverActivity.this, Otp_Activity.class);
                String intentData = "addNewDriverActivity";
                intent.putExtra("addNewDriverActivity",intentData);
                startActivity(intent);
            }
        });
    }
}