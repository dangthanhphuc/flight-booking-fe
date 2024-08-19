package com.example.flightbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.flightbooking.databinding.ActivityRegisterBinding;
import com.example.flightbooking.network.HttpRequest;
import com.example.flightbooking.network.api.UserService;
import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.requests.RegisterRequest;
import com.example.flightbooking.network.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userService = HttpRequest.createService(UserService.class, this);

        addEvent();
    }

    private void registerUser(RegisterRequest registerRequest) {
        Call<Response<UserResponse>> call = userService.register(registerRequest);
        call.enqueue(new Callback<Response<UserResponse>>() {
            @Override
            public void onResponse(Call<Response<UserResponse>> call, retrofit2.Response<Response<UserResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<UserResponse>> call, Throwable throwable) {
                Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void addEvent() {
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterRequest registerRequest = RegisterRequest.builder()
                        .username(binding.edtTk.getText().toString())
                        .password(binding.edtMk.getText().toString())
                        .retypePassword(binding.edtNMk.getText().toString())
                        .firstName(binding.edtFirstName.getText().toString())
                        .lastName(binding.edtLastname.getText().toString())
                        .build();
                registerUser(registerRequest);
            }
        });
    }



}