package com.tajway.tajwaycabs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.retrofitModel.DriverAccountingModel;

import java.io.File;
import java.util.List;

public class DriveAccountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<DriverAccountingModel> list;

    public DriveAccountAdapter(Context context, List<DriverAccountingModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_driver_accounting, parent, false);
        viewHolder = new ViewHolderArtVide(viewItem);

        return viewHolder;
    }

    int count = 0;

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolderArtVide viewHolderArtVide = (ViewHolderArtVide) holder;
        viewHolderArtVide.tvParticular.setText(list.get(position).getParticular());
        viewHolderArtVide.tvRemark.setText("Remark- " + list.get(position).getRemarks());
        Log.e("onClick", "value-toa-" + list.get(position).getDebit());
        if (list.get(position).getCardType().equalsIgnoreCase("Debit")) {
            viewHolderArtVide.tvDebit.setText("" + list.get(position).getDebit());
            viewHolderArtVide.tvCredit.setText("-");
            viewHolderArtVide.tvNone.setText("-");
        } else if (list.get(position).getCardType().equalsIgnoreCase("Credit")) {
            viewHolderArtVide.tvCredit.setText(list.get(position).getCredit());
            viewHolderArtVide.tvDebit.setText("-");
            viewHolderArtVide.tvNone.setText("-");
        } else if (list.get(position).getCardType().equalsIgnoreCase("None")) {
            viewHolderArtVide.tvNone.setText(list.get(position).getNone());
            viewHolderArtVide.tvCredit.setText("-");
            viewHolderArtVide.tvDebit.setText("-");
        }
        viewHolderArtVide.tvTotalAmount.setText(list.get(position).getTotalAmount());
        // viewHolderArtVide.tvRemark.setText(list.get(position).getRemarks());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolderArtVide extends RecyclerView.ViewHolder {
        private TextView tvParticular, tvCredit, tvDebit, tvNone, tvTotalAmount, tvRemark;

        public ViewHolderArtVide(@NonNull View convertView) {
            super(convertView);
            tvParticular = convertView.findViewById(R.id.tvParticular);
            tvDebit = convertView.findViewById(R.id.tvDebit);
            tvCredit = convertView.findViewById(R.id.tvCredit);
            tvNone = convertView.findViewById(R.id.tvNone);
            tvTotalAmount = convertView.findViewById(R.id.tvTotalAmount);
            tvRemark = convertView.findViewById(R.id.tvRemarks);
        }

    }


}


