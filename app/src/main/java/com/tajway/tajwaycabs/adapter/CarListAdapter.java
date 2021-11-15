package com.tajway.tajwaycabs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.retrofitModel.CarLists;

import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarlistViewHolder>{
    Context context;
    List<CarLists.Datum> datumList;



    public CarListAdapter(Context context, List<CarLists.Datum> datumList){
        this.datumList = datumList;
        this.context = context;

    }

    @NonNull
    @Override
    public CarlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carlist_item,parent,false);
        return new CarlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarlistViewHolder holder, int position) {
        holder.vehicleNameValue.setText(datumList.get(position).getVendor_id());
        holder.registrationNumberValue.setText(datumList.get(position).getVehicle_reg_no());
        holder.registrationYearValue.setText(datumList.get(position).getRegistration_year());
        holder.vehicleTypeValue.setText(datumList.get(position).getVehicle_type());
        holder.fuelTypeValue.setText(datumList.get(position).getFuel_type()==null?"unknown":datumList.get(position).getFuel_type());

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    class CarlistViewHolder extends RecyclerView.ViewHolder{

        TextView vehicleNameValue,registrationNumberValue,registrationYearValue,vehicleTypeValue,fuelTypeValue;

        public CarlistViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicleNameValue = itemView.findViewById(R.id.vehicleNameValue);
            registrationNumberValue = itemView.findViewById(R.id.registrationNumberValue);
            registrationYearValue = itemView.findViewById(R.id.registrationYearValue);
            vehicleTypeValue = itemView.findViewById(R.id.vehicleTypeValue);
            fuelTypeValue = itemView.findViewById(R.id.fuelTypeValue);


        }
    }
}
