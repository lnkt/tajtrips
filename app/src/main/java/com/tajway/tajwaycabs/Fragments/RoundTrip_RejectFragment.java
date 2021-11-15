package com.tajway.tajwaycabs.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.adapter.MyBookingTripAdapter;
import com.tajway.tajwaycabs.commonutils.CommonUtils;
import com.tajway.tajwaycabs.retrofitModel.MyBookingModel;
import com.tajway.tajwaycabs.retrofitwebservices.ApiExecutor;
import com.tajway.tajwaycabs.session.SessonManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RoundTrip_RejectFragment extends Fragment {


    RecyclerView rvTripCity;
    ArrayList<MyBookingModel.Data.Mybooktrip> list = new ArrayList<>();
    View view;
    SessonManager sessonManager;
    TextView tv_norecord_local;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_round_trip_reject, container, false);
            rvTripCity = view.findViewById(R.id.rvTripCity);
            tv_norecord_local = view.findViewById(R.id.tv_norecord_local);

            sessonManager = new SessonManager(getContext());
            RoundTripRejectApi();
        }


        return view;
    }

    private void RoundTripRejectApi() {

        if (CommonUtils.isOnline(getActivity())) {
            final ProgressDialog dialog = ProgressDialog.show(getActivity(), null, getString(R.string.loading));

            Call<MyBookingModel> call = ApiExecutor.getApiService(getActivity()).apiRejectBookingList("Bearer " + sessonManager.getToken(),"roundtrip");

            call.enqueue(new Callback<MyBookingModel>() {
                             @Override
                             public void onResponse(Call<MyBookingModel> call, Response<MyBookingModel> response) {
                                 // System.out.println("ViewVisitorType " + "API Data" + new Gson().toJson(response.body()));

                                 String respon = new Gson().toJson(response.body());
                                 //   Log.d("OneWaResponse", respon);

                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 if (response.body() != null) {
                                     if (response.body().getStatus() != null && response.body().getStatus().equals("success")) {

                                         if (response.body().getData() != null) {
                                             list = response.body().getData().getMybooktrip();
                                             setRvData();
                                         } else {
                                             tv_norecord_local.setVisibility(View.VISIBLE);
                                             rvTripCity.setVisibility(View.GONE);
                                         }
                                     } else {
                                         tv_norecord_local.setVisibility(View.VISIBLE);
                                         rvTripCity.setVisibility(View.GONE);

                                     }
                                 } else {
                                     if (dialog != null && dialog.isShowing()) {
                                         dialog.dismiss();
                                     }
                                     //CommonUtils.setSnackbar(tvClassName, getString(R.string.server_not_responding));
                                 }
                             }

                             @Override
                             public void onFailure(Call<MyBookingModel> call, Throwable t) {
                                 if (dialog != null && dialog.isShowing()) {
                                     dialog.dismiss();
                                 }
                                 // System.out.println("API Data Error : " + t.getMessage());
                             }
                         }
            );
        } else {
            CommonUtils.showToastInCenter(getActivity(), getString(R.string.please_check_network));
        }
    }

    private void setRvData() {
        rvTripCity.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rvTripCity.setLayoutManager(layoutManager);
        rvTripCity.setAdapter(new MyBookAdapter(getActivity(), list));
    }

    public class MyBookAdapter extends RecyclerView.Adapter<MyBookAdapter.ViewHolder> {

        Context context;
        ArrayList<MyBookingModel.Data.Mybooktrip> arList;


        public MyBookAdapter(Context context, ArrayList<MyBookingModel.Data.Mybooktrip> arList) {
            this.context = context;
            this.arList = arList;
        }

        @NonNull
        @Override
        public MyBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_one_way_trip, parent, false);
            return new MyBookAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyBookAdapter.ViewHolder holder, int position) {
            holder.tv_trip_id_one_way.setText(arList.get(position).getBooking_id());
            holder.tv_trip_type_one_way.setText(arList.get(position).getTrip_type());
            holder.tv_car_type_one_way.setText(arList.get(position).getCab());
            holder.tv_total_kms_one_way.setText(arList.get(position).getTotalKms());
            holder.tv_total_hours_one_way.setText(arList.get(position).getTotalHours());
            holder.tv_trip_price_one_way.setText(arList.get(position).getTripPrice());
            holder.tv_extra_kms_one_way.setText(arList.get(position).getExtraKms());
            holder.tv_extra_hours_one_way.setText(arList.get(position).getExtraHours());
            holder.tv_start_date_one_way.setText(arList.get(position).getStartDate());
            holder.tv_start_time_one_way.setText(arList.get(position).getStartTime());
            holder.tv_remark_one_way.setText(arList.get(position).getRemarks());
            holder.tv_end_date_one_way.setText(arList.get(position).getEndDate());

            holder.rv_trip_city_row.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new GridLayoutManager(context, 1);
            holder.rv_trip_city_row.setLayoutManager(linearLayoutManager);


            MyBookingTripAdapter eventListChildAdapter = new MyBookingTripAdapter(context, arList.get(position).getTripcity());
            holder.rv_trip_city_row.setAdapter(eventListChildAdapter);


        }

        @Override
        public int getItemCount() {
            return arList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv_trip_id_one_way, tv_trip_type_one_way, tv_car_type_one_way, tv_total_kms_one_way, tv_total_hours_one_way,
                    tv_trip_price_one_way, tv_extra_kms_one_way, tv_extra_hours_one_way, tv_start_date_one_way, tv_start_time_one_way,tv_end_date_one_way,
                    tv_remark_one_way,TvAccept;
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
                tv_start_date_one_way = itemView.findViewById(R.id.tv_start_date_one_way);
                tv_start_time_one_way = itemView.findViewById(R.id.tv_start_time_one_way);
                tv_remark_one_way = itemView.findViewById(R.id.tv_remark_one_way);
                tv_end_date_one_way = itemView.findViewById(R.id.tv_end_date_one_way);

                TvAccept = itemView.findViewById(R.id.tv_accept_one);
                TvAccept.setVisibility(View.GONE);



            }
        }
    }

}