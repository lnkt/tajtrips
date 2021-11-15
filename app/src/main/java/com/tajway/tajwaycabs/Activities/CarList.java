package com.tajway.tajwaycabs.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.adapter.CarListAdapter;
import com.tajway.tajwaycabs.retrofitModel.CarLists;
import com.tajway.tajwaycabs.retrofitwebservices.Apiclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarList extends AppCompatActivity {
    Button addCar;
    RecyclerView carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        addCar = findViewById(R.id.addCar);
        carList = findViewById(R.id.carList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CarList.this,LinearLayoutManager.VERTICAL,false);
        carList.setLayoutManager(linearLayoutManager);
        retriveCarList();
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarList.this,CarsActivity.class);
                startActivity(intent);
                Log.d("hello", "onClick: hello");
            }
        });
    }

    private void retriveCarList() {

        Call<CarLists> call = Apiclient.getMyService().carList();
        call.enqueue(new Callback<CarLists>() {
            @Override
            public void onResponse(Call<CarLists> call, Response<CarLists> response) {
                if (response.body().getStatus().equalsIgnoreCase("success")){
                    List<CarLists.Datum> datumList = response.body().getData();
                    CarListAdapter carListAdapter = new CarListAdapter(CarList.this,datumList);
                    carList.setAdapter(carListAdapter);
                }

            }

            @Override
            public void onFailure(Call<CarLists> call, Throwable t) {

            }
        });

    }

}