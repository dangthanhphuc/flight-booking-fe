package com.example.flightbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flightbooking.databinding.ActivityFindFlightBinding;

import java.util.concurrent.atomic.AtomicReference;

public class FindFlightActivity extends AppCompatActivity {
    ActivityFindFlightBinding binding;

    ActivityResultLauncher<Intent> activityResultLauncher ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFindFlightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        showCalendar();
    }

    private void showCalendar() {
        binding.txtDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindFlightActivity.this, ShowActivityCalendar.class);

                AtomicReference<String> selectDate;

                // ActivityResultLauncher để nhận kết quả từ ShowActivityCalendar

            }
        });
    }
}