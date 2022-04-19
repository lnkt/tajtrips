package com.tajway.tajwaycabs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.tajway.tajwaycabs.R;

public class OrderSuccessfully_Activity extends AppCompatActivity {
    TextView txtOrderId;
    Button btnOkay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_successfully_);

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Order Successfully" + "</font>")));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtOrderId=findViewById(R.id.text_successfull_orderid);
        btnOkay=findViewById(R.id.btn_okkay);

        txtOrderId.setText("Order Id :- "+getIntent().getStringExtra("ref_id"));

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderSuccessfully_Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
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