package com.example.flightbooking.interceptors;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private SharedPreferences sharedPreferences;

    public AuthInterceptor(Context context) {
        // Lấy SharedPreferences để truy cập token
        this.sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Lấy token từ SharedPreferences
        String token = sharedPreferences.getString("JWT_TOKEN", null);

        // Nếu token tồn tại, thêm nó vào header của request
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();

        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        }

        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
