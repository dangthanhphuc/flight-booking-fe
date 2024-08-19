package com.example.flightbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.jwt.JWT;
import com.example.flightbooking.auth.AuthManager;
import com.example.flightbooking.databinding.ActivityMyProfileBinding;
import com.example.flightbooking.network.HttpRequest;
import com.example.flightbooking.network.api.UserService;
import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class MyProfileActivity extends AppCompatActivity {

    private ActivityMyProfileBinding binding;
    private AuthManager authManager;

    private UserService userService;
    private UserResponse userResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authManager = new AuthManager(this);
        userService = HttpRequest.createService(UserService.class, this);

        // Lấy token từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("JWT_TOKEN", null);

        if (token != null) {
            // Giải mã JWT token
            JWT jwt = new JWT(token);
            String username = jwt.getClaim("sub").asString();
            getUserByUsername(username);

            // Hiển thị thông tin người dùng
//            TextView txtUserName = findViewById(R.id.txtUserName);
//            TextView txtUserEmail = findViewById(R.id.txtUserEmail);
//
//            txtUserName.setText(userName);
//            txtUserEmail.setText(userEmail);
        }

        addEvents();
    }

    private void getUserByUsername(String username){
        Call<Response<UserResponse>> call = userService.getUserByUsername(username);
        call.enqueue(new Callback<Response<UserResponse>>() {
            @Override
            public void onResponse(@NonNull Call<Response<UserResponse>> call, @NonNull retrofit2.Response<Response<UserResponse>> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    userResponse = response.body().getData();

                    binding.txtFullName.setText(userResponse.getFirstName() + " " + userResponse.getLastName());
                    binding.txtPhoneNumber.setText(userResponse.getPhoneNumber());
                    binding.txtMyProfileID.setText(userResponse.getId().toString());
                    if(userResponse.getPhoneNumber() == null){
                        binding.txtPhoneNumber.setText("*Số điện thoại*");
                    }else {
                        binding.txtPhoneNumber.setText(userResponse.getPhoneNumber());
                    }


                } else {
                    Toast.makeText(MyProfileActivity.this, "Error", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response<UserResponse>> call, @NonNull Throwable throwable) {

            }
        });
    }

    private void addEvents() {
        binding.lnrAccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, ActivityAccountDetail.class);
                startActivity(intent);
            }
        });

        binding.lnrAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, ActivityAccountDetail.class);
                startActivity(intent);
            }
        });

        binding.lnrLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authManager.logout();
            }
        });

        binding.lnrBookMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, BookingManagementActivity.class);
                startActivity(intent);
            }
        });

    }
}