package com.example.flightbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flightbooking.auth.AuthManager;
import com.example.flightbooking.databinding.ActivityMyProfileBinding;

public class MyProfileActivity extends AppCompatActivity {

    private ActivityMyProfileBinding binding;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authManager = new AuthManager(this);

        addEvents();
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