package com.tajway.tajwaycabs.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.tajway.tajwaycabs.R;

public class CreateBooking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner triptype;
    String[] tripTypeString = {"One Way Trip", "Round Trip", "Local Trip"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking);
        triptype = findViewById(R.id.tripType);
        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tripTypeString);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        triptype.setAdapter(adapterCity);
        triptype.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}