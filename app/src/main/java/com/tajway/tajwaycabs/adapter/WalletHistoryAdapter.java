package com.tajway.tajwaycabs.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.font.AppConstants;
import com.tajway.tajwaycabs.retrofitModel.WalletHistoryModel;

import java.util.ArrayList;


public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.ViewHolder> {
    ArrayList<WalletHistoryModel.History> arList;
    Context context;
    Typeface font;

    public WalletHistoryAdapter(Context context, ArrayList<WalletHistoryModel.History> arList) {
        this.context = context;
        this.arList = arList;

       // font = Typeface.createFromAsset(context.getAssets(), AppConstants.APP_FONT);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_balance, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TvOrderId.setText(arList.get(position).refid);
        holder.tvDate.setText(arList.get(position).date);
        holder.TvCard.setText(arList.get(position).type);
        holder.TvAmount.setText("\u20B9 " + arList.get(position).amount);
        holder.TvDescription.setText(arList.get(position).description);


//        holder.TvOrderId.setTypeface(font);
//        holder.TvCard.setTypeface(font);
//        holder.TvAmount.setTypeface(font);
//        holder.TvDescription.setTypeface(font);
//        holder.tvDate.setTypeface(font);


    }

    @Override
    public int getItemCount() {
        return arList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView TvOrderId, TvCard, TvAmount, TvDescription,tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TvOrderId = (TextView) itemView.findViewById(R.id.tv_order_id_wallet_history);
            TvCard = (TextView) itemView.findViewById(R.id.tv_card_wallet_history);
            TvAmount = (TextView) itemView.findViewById(R.id.tv_amount_wallet_history);
            TvDescription = (TextView) itemView.findViewById(R.id.tv_description_wallet_history);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        }
    }
}

