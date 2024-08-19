package com.example.flightbooking;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flightbooking.databinding.ActivityFlightSearchBinding;
import com.example.flightbooking.databinding.BottomSheetLayoutDepartureBinding;
import com.example.flightbooking.databinding.BottomSheetLayoutDestinationBinding;
import com.example.flightbooking.network.HttpRequest;
import com.example.flightbooking.network.api.AirportService;
import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.responses.AirportResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;

public class FlightSearchActivity extends AppCompatActivity {

    private ActivityFlightSearchBinding binding;
    private String[] departures = {"Hà Nội, Việt Nam (HAN)", "TP Hồ Chí Minh, Việt Nam (SGN)", "Đà Nẵng,Việt Nam (DAD)"};
    private String[] destinations = {"Phú Quốc, Việt Nam (PQC)", "Hà Nội, Việt Nam (HAN)", "Hải Phòng, Việt Nam (HPH)", "Côn Đảo, Việt Nam (VCS)"};

    private AirportService airportService;
    private List<String> nameAirports;
    private int departureIndex = -1;
    private int arrivalIndex = -1;

    private String selectedDeparture;
    private String selectedDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlightSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        airportService = HttpRequest.createService(AirportService.class, this);

        getAirports();
        addEvents();
    }

    private void getAirports() {
        Call<Response<List<AirportResponse>>> call = airportService.getAirports();
        call.enqueue(new Callback<Response<List<AirportResponse>>>() {
            @Override
            public void onResponse(@NonNull Call<Response<List<AirportResponse>>> call, @NonNull retrofit2.Response<Response<List<AirportResponse>>> response) {
                if(response.isSuccessful()) {
                    List<AirportResponse> airportResponses = response.body().getData();
                    nameAirports = airportResponses.stream()
                            .map(airport -> airport.getCountry() + " (" + airport.getCountryCode() + ")")
                            .collect(Collectors.toList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response<List<AirportResponse>>> call, @NonNull Throwable throwable) {
                Toast.makeText(FlightSearchActivity.this, "Failled", Toast.LENGTH_SHORT).show();
            }
        });
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
//                private int departureIndex = 1;
//                private int arrivalIndex = 1;

                startActivity(intent);
            }
        });
    }

    private void showBottomSheetDeparture() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FlightSearchActivity.this);

        BottomSheetLayoutDepartureBinding bottomSheetBinding = BottomSheetLayoutDepartureBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());

//        List<String> showList = new ArrayList<>(nameAirports);
//        if(arrivalIndex != -1){
//            showList.remove(arrivalIndex + 1);
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(FlightSearchActivity.this, android.R.layout.simple_list_item_1, nameAirports);
        bottomSheetBinding.lvDeparture.setAdapter(adapter);

        bottomSheetBinding.lvDeparture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                departureIndex = position;
                binding.txtDeparture.setText(nameAirports.get(position));
                bottomSheetDialog.dismiss();
            }
          
        });

        bottomSheetDialog.show();
    }

    private void showBottomSheetDestination() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FlightSearchActivity.this);

        BottomSheetLayoutDestinationBinding bottomSheetBinding = BottomSheetLayoutDestinationBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());

//        List<String> showList = new ArrayList<>(nameAirports);
//        if(departureIndex != -1){
//            showList.remove(departureIndex);
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(FlightSearchActivity.this, android.R.layout.simple_list_item_1, nameAirports);
        bottomSheetBinding.lvDestination.setAdapter(adapter);

        bottomSheetBinding.lvDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                arrivalIndex = position;
                binding.txtDestination.setText(nameAirports.get(position));
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }

    private void swapDepartureAndDestination() {
        String departure = binding.txtDeparture.getText().toString();
        String destination = binding.txtDestination.getText().toString();

        binding.txtDeparture.setText(destination);
        binding.txtDestination.setText(departure);

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

