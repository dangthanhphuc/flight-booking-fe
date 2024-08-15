package com.example.flightbooking.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    private static Retrofit retrofit;
    private final static String API_URL = "http://10.150.1.202:8080";

    private static Retrofit getClient() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static <T> T createService(Class<T> serviceClass) {
        Retrofit retrofit = getClient();
        return retrofit.create(serviceClass);
    }
}
