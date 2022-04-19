package com.tajway.tajwaycabs.Fragments;



import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.commonutils.AppConstants;
import com.tajway.tajwaycabs.retrofitModel.WalletHistoryModel;
import com.tajway.tajwaycabs.retrofitwebservices.Apiclient;
import com.tajway.tajwaycabs.session.SessonManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class CashBackHistoryFragment extends Fragment {
   SessonManager sessonManager;
    RecyclerView RvCashBack;
    //CashbackAdapter cashbackAdapter;
    ArrayList<WalletHistoryModel.Cashback> arListCashback;
    public CashBackHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cash_back_history, container, false);
        RvCashBack = (RecyclerView) view.findViewById(R.id.rv_cash_back_history);
        sessonManager= new SessonManager(getContext());

//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        Gson gson = new Gson();
//        String json = sharedPrefs.getString("arListCashback", "");
//        Type type = new TypeToken<ArrayList<WalletHistoryModel>>() {}.getType();
//        arListCash = gson.fromJson(json, type);
        hitWalletHistoryApi();
        return view;
    }


//    public class CashbackAdapter extends RecyclerView.Adapter<CashbackAdapter.ViewHolder> {
//
//        Context context;
//        ArrayList<WalletHistoryModel.Cashback> arList;
//        Typeface font;
//
//        public CashbackAdapter(Context context, ArrayList<WalletHistoryModel.Cashback> arList) {
//            this.context = context;
//            this.arList = arList;
//            //font = Typeface.createFromAsset(context.getAssets(), AppConstants.APP_FONT);
//
//        }
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cash_back_history, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//            holder.TvOrderId.setText(arList.get(position).refid);
//            holder.TvCard.setText(arList.get(position).type);
//            holder.TvAmount.setText("\u20B9 " + arList.get(position).amount);
//            holder.TvDescription.setText(arList.get(position).description);
//
//
//            holder.TvOrderId.setTypeface(font);
//            holder.TvCard.setTypeface(font);
//            holder.TvAmount.setTypeface(font);
//            holder.TvDescription.setTypeface(font);
//        }
//
//        @Override
//        public int getItemCount() {
//            return arList.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            TextView TvOrderId, TvCard, TvAmount, TvDescription;
//
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//
//                TvOrderId = (TextView) itemView.findViewById(R.id.tv_order_id_cash_history);
//                TvCard = (TextView) itemView.findViewById(R.id.tv_card_cash_history);
//                TvAmount = (TextView) itemView.findViewById(R.id.tv_amount__cash_history);
//                TvDescription = (TextView) itemView.findViewById(R.id.tv_description__cash_history);
//            }
//        }
//    }

    public void hitWalletHistoryApi(){
        Call<WalletHistoryModel> call1 = Apiclient.getMyService().getWalletHistory("Bearer "+sessonManager.getToken());
       // Call<WalletHistoryModel> call1;
        call1.enqueue(new Callback<WalletHistoryModel>() {
            @Override
            public void onResponse(Call<WalletHistoryModel> call, retrofit2.Response<WalletHistoryModel> response) {
                String res = new Gson().toJson(response.body());
                Log.d("cehkcres", res);
                if (response.isSuccessful()) {
                    WalletHistoryModel walletHistoryModel = response.body();
                    arListCashback =  walletHistoryModel.data.cashback;
                    RvCashBack.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                    RvCashBack.setLayoutManager(layoutManager);
//                    cashbackAdapter = new CashbackAdapter(getActivity(), arListCashback);
//                    RvCashBack.setAdapter(cashbackAdapter);


                } else {
                    Toast.makeText(getActivity(), "" + response, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<WalletHistoryModel> call, Throwable t) {
                Toast.makeText(getActivity(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

}