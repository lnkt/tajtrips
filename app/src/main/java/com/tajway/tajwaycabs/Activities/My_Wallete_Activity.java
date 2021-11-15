package com.tajway.tajwaycabs.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.apiJsonResponse.WalletJsonResponse;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.requestdata.OnewayTripRequest;
import com.tajway.tajwaycabs.responsedata.WalletData;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class My_Wallete_Activity extends AppCompatActivity {

    TextView walleteBalence;
    RecyclerView RvWallet;
    WalletAdapter walletAdapter;
    ArrayList<WalletData> arListWallet;

    SessonManager sessonManager;
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my__wallete_, container, false);*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my__wallete_);
        getSupportActionBar().setTitle("Wallet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sessonManager = new SessonManager(My_Wallete_Activity.this);
        RvWallet = findViewById(R.id.rv_wallet);
        walleteBalence = findViewById(R.id.walleteBalence);
        arListWallet = new ArrayList<>();
        setRvWallet();
        WalletAPI();

    }


    private void setRvWallet() {
        RvWallet.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(My_Wallete_Activity.this, 1);
        RvWallet.setLayoutManager(layoutManager);

        walletAdapter = new WalletAdapter(My_Wallete_Activity.this, arListWallet);
        RvWallet.setAdapter(walletAdapter);

    }


    public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

        ArrayList<WalletData> history;
        Context context;

        public WalletAdapter(Context context, ArrayList<WalletData> history) {
            this.context = context;
            this.history = history;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wallet, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.tv_date_walle.setText(history.get(position).getDate());
            holder.tv_credit_walle.setText(history.get(position).getAmount());
            holder.tv_remark_walle.setText(history.get(position).getDescription());
            holder.tv_cr_db.setText(history.get(position).getType());

        }

        @Override
        public int getItemCount() {
            return history.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv_date_walle, tv_credit_walle, tv_remark_walle, tv_cr_db;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_date_walle = itemView.findViewById(R.id.tv_date_walle);
                tv_credit_walle = itemView.findViewById(R.id.tv_credit_walle);
                tv_remark_walle = itemView.findViewById(R.id.tv_remark_walle);
                tv_cr_db = itemView.findViewById(R.id.tv_cr_db);

            }
        }
    }


    private void WalletAPI() {
        if (CommonUtils.isOnline(My_Wallete_Activity.this)) {
            final ProgressDialog dialog = ProgressDialog.show(My_Wallete_Activity.this, null, getString(R.string.loading));
            OnewayTripRequest request = new OnewayTripRequest();
            Call<WalletJsonResponse> call = ApiExecutor.getApiService(My_Wallete_Activity.this).apiWalletHistory("Bearer " + sessonManager.getToken());

            Log.d("URLshgghd", String.valueOf(call.request().url()));
            System.out.println("API url ---" + call.request().url());
            System.out.println("API request  ---" + new Gson().toJson(request));
            call.enqueue(new Callback<WalletJsonResponse>() {
                             @Override
                             public void onResponse(Call<WalletJsonResponse> call, Response<WalletJsonResponse> response) {
                                 System.out.println("ViewVisitorType " + "API Data" + new Gson().toJson(response.body()));

                                 String respon = new Gson().toJson(response.body());
                                 Log.d("OneWaResponse", respon);

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().status != null && response.body().status.equals("success")) {

                                         String balance = response.body().data.balance;
                                         walleteBalence.setText("Points " + balance);
                                         // Log.d("shgjdbalance", balance);

                                         if (response.body().data.history != null && response.body().data.history.size() > 0) {
                                             arListWallet.clear();
                                             arListWallet.addAll(response.body().data.history);
                                             walletAdapter.notifyDataSetChanged();
                                         } else {
                                             arListWallet.clear();
                                             arListWallet.addAll(response.body().data.history);
                                             walletAdapter.notifyDataSetChanged();
                                         }

                                     } else {
                                         //  tv_norecord_one.setVisibility(View.VISIBLE);
                                         // RvOneWay.setVisibility(View.GONE);
                                         //  CommonUtils.showToast(My_Wallete_Activity.this, response.body().status);
                                     }
                                 } else {
                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     //CommonUtils.setSnackbar(tvClassName, getString(R.string.server_not_responding));
                                 }
                             }

                             @Override
                             public void onFailure(Call<WalletJsonResponse> call, Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(My_Wallete_Activity.this, getString(R.string.please_check_network));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}