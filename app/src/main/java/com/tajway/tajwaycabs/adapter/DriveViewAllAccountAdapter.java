package com.tajway.tajwaycabs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.protobuf.FloatValue;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.retrofitModel.DriverAccountingModel;
import com.tajway.tajwaycabs.retrofitModel.InfoStatusModel;

import java.util.List;

public class DriveViewAllAccountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<InfoStatusModel.Data> list;

    public DriveViewAllAccountAdapter(Context context, List<InfoStatusModel.Data> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_driver_accounting_view_all, parent, false);
        viewHolder = new ViewHolderArtVide(viewItem);

        return viewHolder;
    }

    int count = 0;

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolderArtVide viewHolderArtVide = (ViewHolderArtVide) holder;
        viewHolderArtVide.tvBookingId.setText(list.get(position).getBooking_id());
        viewHolderArtVide.tvSource.setText("Source- " + list.get(position).getSource());
        viewHolderArtVide.tvDestination.setText("Destination- " + list.get(position).getDestination());
        viewHolderArtVide.tvRemarks.setText("Remark- " + list.get(position).getRemarks());
        String value = list.get(position).getAmount().substring(1, list.get(position).getAmount().length());
        if (!list.get(position).getAmount().contains("-")) {
            Log.e("onBindViewHolder", "cr--" + value);
            viewHolderArtVide.tvCredit.setText(list.get(position).getAmount());
            viewHolderArtVide.tvDebit.setText("-");
            viewHolderArtVide.tvNone.setText("-");
        } else {
            Log.e("onBindViewHolder", "dr--" + value);
            viewHolderArtVide.tvDebit.setText(value);
            viewHolderArtVide.tvCredit.setText("-");
            viewHolderArtVide.tvNone.setText("-");
        }

        if (list.get(position).getAmount().contains("-")) {

            viewHolderArtVide.tvTotalAmount.setText(value + " dr.");
        } else {
            viewHolderArtVide.tvTotalAmount.setText(list.get(position).getAmount() + " cr.");
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolderArtVide extends RecyclerView.ViewHolder {
        private TextView tvBookingId, tvSource, tvDestination, tvCredit, tvDebit, tvTotalAmount, tvRemarks, tvNone;

        public ViewHolderArtVide(@NonNull View convertView) {
            super(convertView);
            tvBookingId = convertView.findViewById(R.id.tvBookingId);
            tvSource = convertView.findViewById(R.id.tvSource);
            tvDestination = convertView.findViewById(R.id.tvDestination);
            tvRemarks = convertView.findViewById(R.id.tvRemarks);
            tvTotalAmount = convertView.findViewById(R.id.tvTotalAmount);
            tvCredit = convertView.findViewById(R.id.tvCredit);
            tvDebit = convertView.findViewById(R.id.tvDebit);
            tvNone = convertView.findViewById(R.id.tvNone);

        }

    }


}


