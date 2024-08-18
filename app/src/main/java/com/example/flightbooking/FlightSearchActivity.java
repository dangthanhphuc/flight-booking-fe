package com.example.flightbooking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flightbooking.databinding.ActivityFlightSearchBinding;
import com.example.flightbooking.databinding.BottomSheetLayoutDepartureBinding;
import com.example.flightbooking.databinding.BottomSheetLayoutDestinationBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class FlightSearchActivity extends AppCompatActivity {

    private ActivityFlightSearchBinding binding;
    private String[] departures = {"Hà Nội, Việt Nam (HAN)", "TP Hồ Chí Minh, Việt Nam (SGN)", "Đà Nẵng,Việt Nam (DAD)"};
    private String[] destinations = {"Phú Quốc, Việt Nam (PQC)", "Hà Nội, Việt Nam (HAN)", "Hải Phòng, Việt Nam (HPH)", "Côn Đảo, Việt Nam (VCS)"};

    private String selectedDeparture;
    private String selectedDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlightSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.txtDepartureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        binding.txtDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDeparture();
            }
        });

        binding.txtDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDestination();
            }
        });

        binding.imvSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapDepartureAndDestination();
            }
        });

        binding.btnLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlightSearchActivity.this, LookupInformationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showBottomSheetDeparture() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FlightSearchActivity.this);

        BottomSheetLayoutDepartureBinding bottomSheetBinding = BottomSheetLayoutDepartureBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(FlightSearchActivity.this, android.R.layout.simple_list_item_1, getFilteredDepartures());
        bottomSheetBinding.lvDeparture.setAdapter(adapter);

        bottomSheetBinding.lvDeparture.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = getFilteredDepartures()[position];
            binding.txtDeparture.setText(selectedItem);
            selectedDeparture = selectedItem;
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void showBottomSheetDestination() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FlightSearchActivity.this);

        BottomSheetLayoutDestinationBinding bottomSheetBinding = BottomSheetLayoutDestinationBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(FlightSearchActivity.this, android.R.layout.simple_list_item_1, getFilteredDestinations());
        bottomSheetBinding.lvDestination.setAdapter(adapter);

        bottomSheetBinding.lvDestination.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = getFilteredDestinations()[position];
            binding.txtDestination.setText(selectedItem);
            selectedDestination = selectedItem;
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void swapDepartureAndDestination() {
        String departure = binding.txtDeparture.getText().toString();
        String destination = binding.txtDestination.getText().toString();

        // Swap values
        binding.txtDeparture.setText(destination);
        binding.txtDestination.setText(departure);

        // Update selected values
        selectedDeparture = destination;
        selectedDestination = departure;
    }

    private String[] getFilteredDepartures() {
        return filterArray(departures, selectedDestination);
    }

    private String[] getFilteredDestinations() {
        return filterArray(destinations, selectedDeparture);
    }

    private String[] filterArray(String[] originalArray, String itemToFilterOut) {
        if (itemToFilterOut == null || itemToFilterOut.isEmpty()) {
            return originalArray;
        }

        ArrayList<String> filteredList = new ArrayList<>();
        for (String item : originalArray) {
            if (!item.equals(itemToFilterOut)) {
                filteredList.add(item);
            }
        }
        return filteredList.toArray(new String[0]);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    // Setting the selected date in the TextView
                    binding.txtDepartureTime.setText(String.format("%02d/%02d/%d", dayOfMonth, month1 + 1, year1));
                },
                year,
                month,
                day
        );
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }
}

