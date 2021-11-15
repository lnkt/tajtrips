package com.tajway.tajwaycabs.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tajway.tajwaycabs.Activities.MainActivity;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.adapter.TripCityAdapter;
import com.tajway.tajwaycabs.apiJsonResponse.AcceptJsonResponse;
import com.tajway.tajwaycabs.apiJsonResponse.OneWayTripJonsResponse;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.requestdata.AccecptTripRequest;
import com.tajway.tajwaycabs.requestdata.OnewayTripRequest;
import com.tajway.tajwaycabs.responsedata.OnewayTripResponse;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocalTrip_Fragment extends Fragment {

    RecyclerView RvOneWay;
    ArrayList<OnewayTripResponse> arListOneway;
    OneWayTripAdapter oneWayTripAdapter;
    SessonManager sessonManager;
    TextView tv_norecord_local;
    String trip_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_local_trip_, container, false);

        sessonManager = new SessonManager(getActivity());
        RvOneWay = root.findViewById(R.id.rv_localtrip);
        tv_norecord_local = root.findViewById(R.id.tv_norecord_local);
        arListOneway = new ArrayList<>();
        setRvOneWay();
        OneWayTripAPI();

        return root;
    }


    private void setRvOneWay() {
        RvOneWay.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        RvOneWay.setLayoutManager(layoutManager);
        oneWayTripAdapter = new OneWayTripAdapter(getActivity(), arListOneway);
        RvOneWay.setAdapter(oneWayTripAdapter);

    }


    public class OneWayTripAdapter extends RecyclerView.Adapter<OneWayTripAdapter.ViewHolder> {

        Context context;
        ArrayList<OnewayTripResponse> arList;


        public OneWayTripAdapter(Context context, ArrayList<OnewayTripResponse> arList) {
            this.context = context;
            this.arList = arList;
        }

        @NonNull
        @Override
        public OneWayTripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_one_way_trip, parent, false);
            return new OneWayTripAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OneWayTripAdapter.ViewHolder holder, int position) {
            holder.tv_trip_id_one_way.setText(arList.get(position).getBooking_id());
            holder.tv_trip_type_one_way.setText(arList.get(position).getTrip_type());
            holder.tv_car_type_one_way.setText(arList.get(position).getCab());
            holder.tv_total_kms_one_way.setText(arList.get(position).getTotal_kms());
            holder.tv_total_hours_one_way.setText(arList.get(position).getTotal_hours());
            holder.tv_trip_price_one_way.setText(arList.get(position).getTrip_price());
            holder.tv_extra_kms_one_way.setText(arList.get(position).getExtra_kms());
            holder.tv_extra_hours_one_way.setText(arList.get(position).getExtra_hours());
            holder.tv_start_date_one_way.setText(arList.get(position).getStart_date());
            holder.tv_end_date_one_way.setText(arList.get(position).getEnd_date());
            holder.tv_start_time_one_way.setText(arList.get(position).getStart_time());
            holder.tv_remark_one_way.setText(arList.get(position).getRemarks());

            holder.rv_trip_city_row.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new GridLayoutManager(context, 1);
            holder.rv_trip_city_row.setLayoutManager(linearLayoutManager);


            TripCityAdapter eventListChildAdapter = new TripCityAdapter(context, arList.get(position).getTripcity());
            holder.rv_trip_city_row.setAdapter(eventListChildAdapter);


        }

        @Override
        public int getItemCount() {
            return arList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv_trip_id_one_way, tv_trip_type_one_way, tv_car_type_one_way, tv_total_kms_one_way, tv_total_hours_one_way,
                    tv_trip_price_one_way, tv_extra_kms_one_way, tv_extra_hours_one_way, tv_start_date_one_way, tv_end_date_one_way, tv_start_time_one_way,
                    tv_remark_one_way, TvAccept;
            RecyclerView rv_trip_city_row;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_trip_id_one_way = itemView.findViewById(R.id.tv_trip_id_one_way);
                tv_trip_type_one_way = itemView.findViewById(R.id.tv_trip_type_one_way);
                tv_car_type_one_way = itemView.findViewById(R.id.tv_car_type_one_way);
                rv_trip_city_row = itemView.findViewById(R.id.rv_trip_city_row);
                tv_total_kms_one_way = itemView.findViewById(R.id.tv_total_kms_one_way);
                tv_total_hours_one_way = itemView.findViewById(R.id.tv_total_hours_one_way);
                tv_trip_price_one_way = itemView.findViewById(R.id.tv_trip_price_one_way);
                tv_extra_kms_one_way = itemView.findViewById(R.id.tv_extra_kms_one_way);
                tv_extra_hours_one_way = itemView.findViewById(R.id.tv_extra_hours_one_way);
                tv_start_date_one_way = itemView.findViewById(R.id.tv_start_date_one_way);
                tv_end_date_one_way = itemView.findViewById(R.id.tv_end_date_one_way);
                tv_start_time_one_way = itemView.findViewById(R.id.tv_start_time_one_way);
                tv_remark_one_way = itemView.findViewById(R.id.tv_remark_one_way);
                TvAccept = itemView.findViewById(R.id.tv_accept_one);


                TvAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        trip_id = arList.get(getAdapterPosition()).getId();
                        UpdateProfileAPI();
                    }
                });
            }
        }
    }


    private void OneWayTripAPI() {
        if (CommonUtils.isOnline(getActivity())) {
            final ProgressDialog dialog = ProgressDialog.show(getActivity(), null, getString(R.string.loading));
            OnewayTripRequest request = new OnewayTripRequest();


            Call<OneWayTripJonsResponse> call = ApiExecutor.getApiService(getActivity()).apiOneWay("Bearer " + sessonManager.getToken(), "localtrip");

            Log.d("URLshgghd", String.valueOf(call.request().url()));
            System.out.println("API url ---" + call.request().url());
            System.out.println("API request  ---" + new Gson().toJson(request));
            call.enqueue(new Callback<OneWayTripJonsResponse>() {
                             @Override
                             public void onResponse(Call<OneWayTripJonsResponse> call, Response<OneWayTripJonsResponse> response) {
                                 System.out.println("ViewVisitorType " + "API Data" + new Gson().toJson(response.body()));

                                 String respon = new Gson().toJson(response.body());
                                 Log.d("OneWayTrip", respon);

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().status != null && response.body().status.equals("success")) {

                                         tv_norecord_local.setVisibility(View.GONE);
                                         RvOneWay.setVisibility(View.VISIBLE);

                                         if (response.body().data != null && response.body().data.size() > 0) {
                                             arListOneway.clear();
                                             arListOneway.addAll(response.body().data);
                                             oneWayTripAdapter.notifyDataSetChanged();
                                         } else {
                                             arListOneway.clear();
                                             arListOneway.addAll(response.body().data);
                                             oneWayTripAdapter.notifyDataSetChanged();
                                         }
                                     } else {

                                         tv_norecord_local.setVisibility(View.VISIBLE);
                                         RvOneWay.setVisibility(View.GONE);
                                         //    CommonUtils.showToast(getActivity(), response.body().Message);
                                     }
                                 } else {
                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     //CommonUtils.setSnackbar(tvClassName, getString(R.string.server_not_responding));
                                 }
                             }

                             @Override
                             public void onFailure(Call<OneWayTripJonsResponse> call, Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(getActivity(), getString(R.string.please_check_network));
        }
    }


    private void UpdateProfileAPI() {
        if (CommonUtils.isOnline(getActivity())) {
            final ProgressDialog dialog = ProgressDialog.show(getActivity(), null, getString(R.string.loading));
            AccecptTripRequest request = new AccecptTripRequest();
            request.setTrip_id(trip_id);
            Log.d("trsjhsId", trip_id);

            Call<AcceptJsonResponse> call = ApiExecutor.getApiService(getActivity()).apiAcceptTrip("Bearer " + sessonManager.getToken(), request);
            System.out.println("API url ---" + call.request().url());
            System.out.println("API request  ---" + new Gson().toJson(request));
            call.enqueue(new Callback<AcceptJsonResponse>() {
                             @Override
                             public void onResponse(Call<AcceptJsonResponse> call, Response<AcceptJsonResponse> response) {
                                 String respon = new Gson().toJson(response.body());
                                 dialog.dismiss();
                                 Log.d("responLogin", respon);
                                 System.out.println("MobileVerifyActivity" + "API Data" + new Gson().toJson(response.body()));

                                 if (response.body() != null) {
                                     if (response.body().status != null && response.body().status.equals("success")) {
                                         String message = response.body().message;
                                         Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                         Intent intent = new Intent(getActivity(), MainActivity.class);
                                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                         startActivity(intent);


                                     } else {
                                         CommonUtils.showToast(getActivity(), response.body().message);

                                     }
                                 } else {
                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     // CommonUtils.setSnackbar(tvLogin, getString(R.string.server_not_responding));
                                 }


                             }

                             @Override
                             public void onFailure(Call<AcceptJsonResponse> call, Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(getActivity(), getString(R.string.please_check_network));
        }
    }

}