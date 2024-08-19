package com.example.flightbooking;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flightbooking.databinding.ActivityBookingManagementBinding;

public class BookingManagementActivity extends AppCompatActivity {
    ActivityBookingManagementBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        addEvents();
    }

    private void addEvents() {
    }
}