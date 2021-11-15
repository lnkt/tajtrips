package com.tajway.tajwaycabs.retrofitwebservices;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiExecutor {

    private static String baseUrl;
    private static Retrofit retrofit;

    public static ApiService getApiService(Context mContext) {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    if (response.body() != null) {
                       Log.e("body", response.body().toString());
                    }
//                            if (response.code() == 500) {
//
//                                return response;
//                            }
                    return response;
                })
                .addNetworkInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .build();
                    return chain.proceed(request);
                })
                //here we adding Interceptor for full level logging
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        baseUrl = RequestUrl.BASE_URL;
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        return retrofit.create(ApiService.class);

    }
}
