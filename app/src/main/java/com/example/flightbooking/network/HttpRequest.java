package com.example.flightbooking.network;

import android.content.Context;

import com.example.flightbooking.adapters.LocalDateAdapter;
import com.example.flightbooking.adapters.LocalDateTimeAdapter;
import com.example.flightbooking.interceptors.AuthInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {

    private static Retrofit retrofit;

    private final static String API_URL = "http://192.168.139.247:8080";

    private static Retrofit getClient(Context context) {
        if(retrofit == null){

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Đăng ký adapter
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context))
                    .connectTimeout(60, TimeUnit.SECONDS)  // Thời gian chờ kết nối (connect timeout)
                    .readTimeout(60, TimeUnit.SECONDS)     // Thời gian chờ đọc dữ liệu (read timeout)
                    .writeTimeout(60, TimeUnit.SECONDS)    // Thời gian chờ ghi dữ liệu (write timeout)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }


    public static <T> T createService(Class<T> serviceClass, Context context) {
        Retrofit retrofit = getClient(context);
        return retrofit.create(serviceClass);
    }
}
