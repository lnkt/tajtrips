package com.tajway.tajwaycabs.retrofitwebservices;

import androidx.annotation.NonNull;

import com.tajway.tajwaycabs.apiJsonResponse.AcceptJsonResponse;
import com.tajway.tajwaycabs.apiJsonResponse.DriverCreateJsonResponse;
import com.tajway.tajwaycabs.apiJsonResponse.DriverJsonResponse;
import com.tajway.tajwaycabs.apiJsonResponse.LoginJsonResponse;
import com.tajway.tajwaycabs.apiJsonResponse.OTPJsonResponse;
import com.tajway.tajwaycabs.apiJsonResponse.OneWayTripJonsResponse;
import com.tajway.tajwaycabs.apiJsonResponse.ProfileJsonResponse;
import com.tajway.tajwaycabs.apiJsonResponse.UpdateBankDetailsJsonResponse;
import com.tajway.tajwaycabs.apiJsonResponse.UpdatePancardDetailsJsonResponse;
import com.tajway.tajwaycabs.apiJsonResponse.UpdateProfileJonsResponse;
import com.tajway.tajwaycabs.apiJsonResponse.WalletJsonResponse;
import com.tajway.tajwaycabs.requestdata.AccecptTripRequest;
import com.tajway.tajwaycabs.requestdata.DriverCreateRequest;
import com.tajway.tajwaycabs.requestdata.LoginRequest;
import com.tajway.tajwaycabs.requestdata.OTPRequest;
import com.tajway.tajwaycabs.requestdata.UpdateBanlDetailsRequest;
import com.tajway.tajwaycabs.requestdata.UpdatePancardRequest;
import com.tajway.tajwaycabs.requestdata.UpdateProfileRequest;
import com.tajway.tajwaycabs.retrofitModel.CarListStatusModel;
import com.tajway.tajwaycabs.retrofitModel.CarLists;
import com.tajway.tajwaycabs.retrofitModel.CarStore;
import com.tajway.tajwaycabs.retrofitModel.DriverList;
import com.tajway.tajwaycabs.retrofitModel.GoingHomeServiceModel;
import com.tajway.tajwaycabs.retrofitModel.GoingHomeTripList;
import com.tajway.tajwaycabs.retrofitModel.InfoStatusModel;
import com.tajway.tajwaycabs.retrofitModel.MyBookingModel;
import com.google.gson.JsonObject;
import com.tajway.tajwaycabs.retrofitModel.NotificationModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiService {

    @NonNull
    @POST("login-with-otp")
    Call<LoginJsonResponse> apiLogin(@Body LoginRequest login);


    @NonNull
    @POST("verify-otp")
    Call<OTPJsonResponse> apiOTP(@Body OTPRequest otp);


    @NonNull
    @GET("profile")
    Call<ProfileJsonResponse> apiProfile(@Header("Authorization") String token);


    @NonNull
    @POST("update-bank-details")
    Call<UpdateBankDetailsJsonResponse> apiUpdateBankDetail(@Header("Authorization") String token, @Body UpdateBanlDetailsRequest Update);


    @NonNull
    @POST("update-pancard-details")
    Call<UpdatePancardDetailsJsonResponse> apiUpdatePanCardDetail(@Header("Authorization") String token, @Body UpdatePancardRequest Update);

    @NonNull
    @POST("update-profile")
    Call<UpdateProfileJonsResponse> apiUpdateProfile(@Header("Authorization") String token, @Body UpdateProfileRequest Update);


    @NonNull
    @GET("trip-list")
    Call<OneWayTripJonsResponse> apiOneWay(@Header("Authorization") String token, @Query("type") String type);


    @NonNull
    @POST("accept-trip")
    Call<AcceptJsonResponse> apiAcceptTrip(@Header("Authorization") String token, @Body AccecptTripRequest accept);


    @NonNull
    @GET("wallet-history")
    Call<WalletJsonResponse> apiWalletHistory(@Header("Authorization") String token);

    @NonNull
    @GET("driver-list")
    Call<DriverJsonResponse> apiDriverList(@Header("Authorization") String token);

    @NonNull
    @GET("notifications")
    Call<NotificationModel> getNotification(@Header("Authorization") String token);

    @NonNull
    @POST("driver-create")
    Call<DriverCreateJsonResponse> apiDriverCreate(@Header("Authorization") String token, @Body DriverCreateRequest accept);

    @NonNull
    @GET("mybooking-trip")
    Call<MyBookingModel> apiMyBookingList(@Header("Authorization") String token, @Query("type") String type);

    @NonNull
    @GET("rejected-booking")
    Call<MyBookingModel> apiRejectBookingList(@Header("Authorization") String token, @Query("type") String type);

    @NonNull
    @GET("completed-booking")
    Call<MyBookingModel> apiCompleteBookingList(@Header("Authorization") String token, @Query("type") String type);

    @FormUrlEncoded
    @POST("http://jhojhu.com/api/gallery_list")
    Call<InfoStatusModel> apiInfoList(@Header("Authorization") String token, @Field("type") String type);

    @NonNull
    @GET("carlist")
    Call<CarListStatusModel> carList(@Header("Authorization") String token);

    @NonNull
    @GET("triplist")
    Call<GoingHomeTripList> apiTripList(@Header("Authorization") String token);

    @FormUrlEncoded
    @NonNull
    @POST("goinghome-service")
    Call<GoingHomeServiceModel> postGoingHome(@Header("Authorization") String token, @Field("trip_type") String trip_type,
                                              @Field("from_city") String from_city, @Field("to_city") String to_city,
                                              @Field("inciusive_fair") String inciusive_fair, @Field("from_date") String from_date,
                                              @Field("from_time") String from_time, @Field("to_date") String to_date,
                                              @Field("to_time") String to_time, @Field("car_type") String car_type);

    @Multipart
    @POST("upload-payment")
    Call<JsonObject> hitUpdateDetail(@HeaderMap Map<String, String> token, @Part MultipartBody.Part[] images, @PartMap() Map<String, RequestBody> partMap);


    @FormUrlEncoded
    @POST("http://jhojhu.com/api/driveraccount_save")
    Call<InfoStatusModel> apiDriverAccounting(@Field("booking_id") String booking_id, @Field("driver_id") String driver_id, @Field("Destination") String Destination,
                                              @Field("amount") String amount, @Field("txn_type") String txn_type, @Field("txn_date") String txn_date, @Field("remarks") String remarks,
                                              @Field("user_id") String user_id, @Field("Source") String Source);




    // changed link:http://jhojhu.com/api/driver
    @FormUrlEncoded
    @POST("http://avaskm.jhojhu.com/api/driver")
    Call<InfoStatusModel> isDriver(@Field("mobile") String mobile);


    @FormUrlEncoded
    @POST("http://jhojhu.com/Api/booking_id")
    Call<InfoStatusModel> completedBookingId(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("http://jhojhu.com/Api/driverAccountingAll")
    Call<InfoStatusModel> accountingViewAll(@Field("user_id") String user_id);

    @GET("car-lists")
    Call<CarLists> carList();

    @GET("list-driver")
    Call<DriverList> driverList();

    @Multipart
    @POST("car-store")
    Call<CarStore> addCar(@PartMap() Map<String, RequestBody> partMap,@Part MultipartBody.Part fileRc,@Part MultipartBody.Part fileInsurance,@Part MultipartBody.Part filePermit);
}
