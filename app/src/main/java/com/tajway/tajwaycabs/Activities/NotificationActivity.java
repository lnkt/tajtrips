package com.tajway.tajwaycabs.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.retrofitModel.NotificationModel;
import com.tajway.tajwaycabs.retrofitwebservices.ApiService;
import com.tajway.tajwaycabs.retrofitwebservices.RequestUrl;
import com.tajway.tajwaycabs.session.SessonManager;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationActivity extends AppCompatActivity {

    SessonManager sharedPreference;
    ProgressBar progressbar;
    RecyclerView rvNotification;
    ArrayList<NotificationModel.Data.Notification> listNotification = new ArrayList<>();

//    final ProgressDialog dialog = ProgressDialog.show(NotificationActivity.this, null, getString(R.string.loading));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Notifications" + "</font>")));
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvNotification = findViewById(R.id.recycler_notification);

        sharedPreference = SessonManager.getInstance(this);



        hitApi();
    }

    private void hitApi() {
        final ProgressDialog dialog = ProgressDialog.show(NotificationActivity.this, null, getString(R.string.loading));
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RequestUrl.BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                ))
                        .build())
                .build();
        ApiService api = retrofit.create(ApiService.class);
        Call<NotificationModel> call = api.getNotification("Bearer " +sharedPreference.getToken());

        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if (response.isSuccessful()) {
                    Log.d("sjdfnjkdsf", new Gson().toJson(response.body()));
                    dialog.dismiss();
                    NotificationModel model = response.body();
                    NotificationModel.Data notificationData = model.getData();
                    listNotification = notificationData.getNotifications();
                    setRv();
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(NotificationActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setRv() {
        rvNotification.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(NotificationActivity.this, 1);
        rvNotification.setLayoutManager(layoutManager);
        rvNotification.setAdapter(new NotificationAdapter(listNotification, NotificationActivity.this));
    }

    public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
        ArrayList<NotificationModel.Data.Notification> mData;
        Context mcontext;

        public NotificationAdapter(ArrayList<NotificationModel.Data.Notification> mData, Context mcontext) {
            this.mData = mData;
            this.mcontext = mcontext;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater minflater = LayoutInflater.from(mcontext);
            view = minflater.inflate(R.layout.row_notification, parent, false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, final int position) {

            holder.txtTitleNotification.setText(mData.get(position).getTitle());
            holder.txtDescNotificaion.setText(mData.get(position).getDescription());
            holder.txt_Notification_time.setText(mData.get(position).getCreatedAt());

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txtDescNotificaion, txtTitleNotification, txt_Notification_time;

            public ViewHolder(@NonNull final View itemView) {
                super(itemView);

                txtTitleNotification = itemView.findViewById(R.id.txtTitleNotification);
                txtDescNotificaion = itemView.findViewById(R.id.txtDescNotificaion);
                txt_Notification_time = itemView.findViewById(R.id.txt_Notification_time);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
}