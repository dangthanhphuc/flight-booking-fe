package com.example.flightbooking;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flightbooking.adapters.LookupInformationAdapter;
import com.example.flightbooking.databinding.ActivityLookupInformationBinding;
import com.example.flightbooking.network.HttpRequest;
import com.example.flightbooking.network.api.AirportService;
import com.example.flightbooking.network.api.FlightService;
import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.responses.AirportResponse;
import com.example.flightbooking.network.responses.FlightResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LookupInformationActivity extends AppCompatActivity {

    private FlightService flightService;
    private LookupInformationAdapter lookupInformationAdapter;
    private ActivityLookupInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLookupInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        flightService = HttpRequest.createService(FlightService.class, this);
        getFlights();
    }

    private void getFlights() {
            Call<Response<List<FlightResponse>>> call  = flightService.getFlights();
            call.enqueue(new Callback<Response<List<FlightResponse>>>() {

                @Override
                public void onResponse(Call<Response<List<FlightResponse>>> call, retrofit2.Response<Response<List<FlightResponse>>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Xử lý dữ liệu trả về
                        // Thay lại layout hiển thị
                        lookupInformationAdapter = new LookupInformationAdapter(LookupInformationActivity.this, R.layout.navigation_item, response.body().getData());
//                        binding.grdView.setAdapter(navigationItemAdapter);
                    } else {
                        Log.e("Error", "Request failed");
                    }
                }

                @Override
                public void onFailure(Call<Response<List<FlightResponse>>> call, Throwable throwable) {
                    call.timeout();
                }

            });
    }

}