package com.example.flightbooking.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.flightbooking.LoginActivity;
import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.requests.LoginRequest;
import com.example.flightbooking.network.HttpRequest;
import com.example.flightbooking.network.api.UserService;

import retrofit2.Call;
import retrofit2.Callback;

public class AuthManager {

    private UserService userService;
    private Context context;

    public AuthManager(Context context) {
        this.context = context;
        this.userService = HttpRequest.createService(UserService.class, context);
    }

    public void login(String username, String password, final AuthCallback callback) {
        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<Response<String>> call = userService.login(loginRequest);
        call.enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(@NonNull Call<Response<String>> call, @NonNull retrofit2.Response<Response<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getData();
                    saveToken(token);
                    callback.onSuccess(); // Gọi callback thành công
                } else {
                    callback.onFailure(); // Gọi callback thất bại
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response<String>> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void logout() {
        // Xóa token khỏi SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("JWT_TOKEN");
        editor.apply();

        // Thông báo người dùng đã đăng xuất
        Toast.makeText(context, "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show();

        // Chuyển hướng người dùng về màn hình đăng nhập
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("JWT_TOKEN", token);
        editor.apply();
    }



    public interface AuthCallback {
        void onSuccess();
        void onFailure();
    }
}
