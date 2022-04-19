package com.tajway.tajwaycabs.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.tajway.tajwaycabs.R;

public class CreateBooking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner triptype;
    String[] tripTypeString = {"One Way Trip", "Round Trip", "Local Trip"};
    String name,sourcename;
    EditText clientName,mobileNumber,mobileNumbertwo,carType,Source,destination,traveldays,tripStartDate,tripStartTime,tripEndDate,tripEndTime,address,tripAmount,perKMAmount,advancePayment,PaymentStatus,totalKms,totalHours,vendorPrice,ExtraKms,ExtraHours,PanelRemarks;
    Button createBooking;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking);
        builder = new AlertDialog.Builder(this);
        createBooking = findViewById(R.id.createBooking);
        triptype = findViewById(R.id.tripType);
        clientName = findViewById(R.id.clientName);
        mobileNumber = findViewById(R.id.mobileNumber);
        mobileNumbertwo = findViewById(R.id.mobileNumbertwo);
        Source = findViewById(R.id.Source);
        carType = findViewById(R.id.carType);
        destination = findViewById(R.id.destination);
        traveldays = findViewById(R.id.traveldays);
        tripStartDate = findViewById(R.id.tripStartDate);
        tripStartTime = findViewById(R.id.tripStartTime);
        tripEndDate = findViewById(R.id.tripEndDate);
        tripEndTime = findViewById(R.id.tripEndTime);
        address = findViewById(R.id.address);
        tripAmount = findViewById(R.id.tripAmount);
        perKMAmount = findViewById(R.id.perKMAmount);
        advancePayment = findViewById(R.id.advancePayment);
        PaymentStatus = findViewById(R.id.PaymentStatus);
        totalHours = findViewById(R.id.totalHours);
        vendorPrice = findViewById(R.id.vendorPrice);
        ExtraKms = findViewById(R.id.ExtraKms);
        ExtraHours = findViewById(R.id.ExtraHours);
        PanelRemarks = findViewById(R.id.PanelRemarks);
        createBooking.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             name=clientName.getText().toString();
             name=Source.getText().toString();
             sourcename=clientName.getText().toString();
             name=clientName.getText().toString();
             name=clientName.getText().toString();
             name=clientName.getText().toString();
             name=clientName.getText().toString();
             if(mobileNumber.getText().toString().isEmpty()){
                 mobileNumber.setError("Please Enter Mobile number");
                 mobileNumber.requestFocus();
             }
             else if(mobileNumber.getText().toString().length()!=10){
                 mobileNumber.setError("Mobile number should be 10 digit");
                 mobileNumber.requestFocus();
             }else if (name.isEmpty()){
                 clientName.setError( "enter name..." );
             }
             else if (sourcename.isEmpty())
             {
                 Source.setError( "Please enter the sourcename");

             }else if (carType.getText().toString().isEmpty()){
                 carType.setError("Please enter the cartype");
             }else if (destination.getText().toString().isEmpty()){
                 destination.setError("Please enter the destination");
             }else if (traveldays.getText().toString().isEmpty()){
                 traveldays.setError("Please enter the traveldays");
             }else if (tripStartDate.getText().toString().isEmpty()){
                 tripStartDate.setError("Please enter the tripStartDate");
             }else if (tripEndTime.getText().toString().isEmpty()){
                 tripEndTime.setError("Please enter the tripEndTime");
             }else if (address.getText().toString().isEmpty()){
                 address.setError("Please enter the address");
             }else if (tripAmount.getText().toString().isEmpty()){
                 tripAmount.setError("Please enter the tripAmount");
             }else if (perKMAmount.getText().toString().isEmpty()){
                 perKMAmount.setError("Please enter the perKMAmount");
             }else if (advancePayment.getText().toString().isEmpty()){
                 advancePayment.setError("Please enter the advancePayment");
             }else if (PaymentStatus.getText().toString().isEmpty()){
                 PaymentStatus.setError("Please enter the PaymentStatus");
             }else if (totalHours.getText().toString().isEmpty()){
                 totalHours.setError("Please enter the totalHours");
             }else if (vendorPrice.getText().toString().isEmpty()){
                 vendorPrice.setError("Please enter the vendorPrice");
             }else if (ExtraKms.getText().toString().isEmpty()){
                 ExtraKms.setError("Please enter the ExtraKms");
             }else if (PanelRemarks.getText().toString().isEmpty()){
                 PanelRemarks.setError("Please enter the PanelRemarks");
             }else if (mobileNumbertwo.getText().toString().length()!=10){
                 mobileNumbertwo.setError("Please Enter Mobile number");
                 mobileNumbertwo.requestFocus();
             }else if(mobileNumbertwo.getText().toString().length()!=10){
                 mobileNumbertwo.setError("Mobile number should be 10 digit");
                 mobileNumbertwo.requestFocus();
             }else if  (triptype.getSelectedItem().toString().trim().equals("Select one")) {
                 Toast.makeText(CreateBooking.this, "Error", Toast.LENGTH_SHORT).show();
             }
             else {
                 dialogbox();
             }
         }
            private void dialogbox() {
                builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
                builder.setMessage("We will detect 500 points from your wallet")
                        .setCancelable(false)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Toast.makeText(getApplicationContext(),"You choose Continue",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"You choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Create Booking");
                alert.show();
}
        });
        //adapter
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
    public void createBooking(){

    }

}