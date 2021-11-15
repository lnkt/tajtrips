package com.tajway.tajwaycabs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.retrofitModel.MyBookingModel;

import java.util.ArrayList;

public class MyBookingTripAdapter extends RecyclerView.Adapter<MyBookingTripAdapter.ViewHolder> {
    Context context;
    ArrayList<MyBookingModel.Data.Mybooktrip.Tripcity> arList;

    public MyBookingTripAdapter(Context context, ArrayList<MyBookingModel.Data.Mybooktrip.Tripcity> arList) {
        this.context = context;
        this.arList = arList;

    }


    @NonNull
    @Override
    public MyBookingTripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_trip_city, parent, false);
        return new MyBookingTripAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingTripAdapter.ViewHolder holder, int position) {
        holder.tv_name_trip_city.setText(arList.get(position).getTripCity());

        if(position==arList.size()-1){
            holder.imgSource.setVisibility(View.GONE);
            holder.txtDot1.setVisibility(View.GONE);
            holder.txtDot2.setVisibility(View.GONE);
            holder.imgDest.setVisibility(View.VISIBLE);

        }else {
            holder.imgDest.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_name_trip_city,txtDot1,txtDot2;
        ImageView imgSource,imgDest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDot1 = itemView.findViewById(R.id.txtDot1);
            txtDot2 = itemView.findViewById(R.id.txtDot2);
            tv_name_trip_city = itemView.findViewById(R.id.tv_name_trip_city);
            imgSource = itemView.findViewById(R.id.imgSource);
            imgDest = itemView.findViewById(R.id.imgDest);
        }
    }
}

