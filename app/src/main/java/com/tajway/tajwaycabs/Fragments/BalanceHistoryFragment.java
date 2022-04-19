package com.tajway.tajwaycabs.Fragments;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.adapter.WalletHistoryAdapter;
import com.tajway.tajwaycabs.retrofitModel.WalletHistoryModel;
import com.tajway.tajwaycabs.retrofitwebservices.Apiclient;
import com.tajway.tajwaycabs.session.SessonManager;
import com.tajway.tajwaycabs.util.Progressbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class BalanceHistoryFragment extends Fragment {
   SessonManager sessonManager;
    Progressbar progressbar;
    RecyclerView RvBalanceHistory;
    ArrayList<WalletHistoryModel.History> arListBalance;
    ArrayList<WalletHistoryModel.Cashback> arListCashback;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_balance_history, container, false);
        RvBalanceHistory = (RecyclerView) view.findViewById(R.id.rv_balance_history);

        sessonManager = new SessonManager(getContext());
        progressbar = new Progressbar();

        //arListBalance = new ArrayList<>();
        hitWalletHistoryApi();
        return view;
    }

    public void hitWalletHistoryApi(){
        progressbar.showProgress(getActivity());
        Call<WalletHistoryModel> call1 = Apiclient.getMyService().getWalletHistory("Bearer " +sessonManager.getToken());
        call1.enqueue(new Callback<WalletHistoryModel>() {
            @Override
            public void onResponse(Call<WalletHistoryModel> call, retrofit2.Response<WalletHistoryModel> response) {
                String res = new Gson().toJson(response.body());
                progressbar.hideProgress();
                Log.d("cehkcres", res);
                if (response.isSuccessful()) {
                    WalletHistoryModel walletHistoryModel = response.body();
                    arListBalance = (ArrayList<WalletHistoryModel.History>) walletHistoryModel.data.history;
                    arListCashback =  walletHistoryModel.data.cashback;

                    RvBalanceHistory.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                    RvBalanceHistory.setLayoutManager(layoutManager);
                    WalletHistoryAdapter walletHistoryAdapter = new WalletHistoryAdapter(getActivity(), arListBalance);
                    RvBalanceHistory.setAdapter(walletHistoryAdapter);

//                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                    SharedPreferences.Editor editor = sharedPrefs.edit();
//                    Gson gson = new Gson();
//                    String json = gson.toJson(arListCashback);
//                    editor.putString("arListCashback", json);
//                    editor.commit();

                } else {
                    Toast.makeText(getActivity(), "" + response, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<WalletHistoryModel> call, Throwable t) {
                Toast.makeText(getActivity(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
                progressbar.hideProgress();
            }
        });
    }




}