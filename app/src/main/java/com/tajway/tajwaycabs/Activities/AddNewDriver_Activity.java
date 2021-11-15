package com.tajway.tajwaycabs.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tajway.tajwaycabs.R;

public class AddNewDriver_Activity extends AppCompatActivity {
    TextView tv_submitDriverNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_driver_);

        tv_submitDriverNumber = findViewById(R.id.tv_submitDriverNumber);
        tv_submitDriverNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewDriver_Activity.this,Otp_Activity.class);
                String intentData = "addNewDriverActivity";
                intent.putExtra("addNewDriverActivity",intentData);
                startActivity(intent);
            }
        });
    }
}