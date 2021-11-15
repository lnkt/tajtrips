package com.tajway.tajwaycabs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.retrofitModel.InfoStatusModel;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    Context context;
    ArrayList<InfoStatusModel.Data.Gallery> arList;

    public InfoAdapter(Context context, ArrayList<InfoStatusModel.Data.Gallery> arList) {
        this.context = context;
        this.arList = arList;

    }

    @NonNull
    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_info, parent, false);
        return new InfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoAdapter.ViewHolder holder, int position) {
        Glide.with(context).load("http://jhojhu.com/"+arList.get(position).getImage()).into(holder.img);
       // Picasso.get().load(arList.get(position).getImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return arList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);

        }
    }
}