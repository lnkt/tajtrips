package com.tajway.tajwaycabs.retrofitwebservices;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class Apiclient {
    public static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                return retrofit;
    }
    public static ApiService getMyService(){
        ApiService api =getRetrofit().create(ApiService.class);
        return api;
    }
//    RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"),strRequestBody);
//    Call<ResponseBody> call = getMyService().update(requestBody);

    public static okhttp3.RequestBody getRequestBodyFromString(String string) {
        return okhttp3.RequestBody.create(okhttp3.MediaType.parse("text/plain"), string);
    }
}
