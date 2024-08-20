package com.example.flightbooking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.auth0.android.jwt.JWT;
import com.example.flightbooking.adapters.BookingAdapter;
import com.example.flightbooking.databinding.ActivityBookingManagementBinding;
import com.example.flightbooking.network.HttpRequest;
import com.example.flightbooking.network.api.BookingService;
import com.example.flightbooking.network.api.UserService;
import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.responses.BookingResponse;
import com.example.flightbooking.network.responses.UserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class BookingManagementActivity extends AppCompatActivity {
    ActivityBookingManagementBinding binding;
    private UserService userService;
    private BookingService bookingService;
    private UserResponse userResponse;

    List<BookingResponse> bookings = new ArrayList<>();

    private BookingAdapter bookingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userService = HttpRequest.createService(UserService.class, this);
        bookingService = HttpRequest.createService(BookingService.class, this);

        // Lấy token từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("JWT_TOKEN", null);

        if (token != null) {
            // Giải mã JWT token
            JWT jwt = new JWT(token);
            String username = jwt.getClaim("sub").asString();
            getUserByUsername(username);
        }


    }

    private void getUserByUsername(String username){
        Call<Response<UserResponse>> call = userService.getUserByUsername(username);
        call.enqueue(new Callback<Response<UserResponse>>() {
            @Override
            public void onResponse(@NonNull Call<Response<UserResponse>> call, @NonNull retrofit2.Response<Response<UserResponse>> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    userResponse = response.body().getData();
                    getBookingByUserId(userResponse.getId());
                } else {
                    Toast.makeText(BookingManagementActivity.this, "Error", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response<UserResponse>> call, @NonNull Throwable throwable) {
                Toast.makeText(BookingManagementActivity.this, "Api fail", Toast.LENGTH_LONG);
            }
        });
    }

    private void getBookingByUserId(Long userId){
        Call<Response<List<BookingResponse>>> call = bookingService.getBookingsByUserId(userId);
        call.enqueue(new Callback<Response<List<BookingResponse>>>() {
            @Override
            public void onResponse(Call<Response<List<BookingResponse>>> call, retrofit2.Response<Response<List<BookingResponse>>> response) {
                if(response.isSuccessful()) {
                    bookings = response.body().getData();
                    initialData();
                }
            }

            @Override
            public void onFailure(Call<Response<List<BookingResponse>>> call, Throwable throwable) {
                Toast.makeText(BookingManagementActivity.this, "Api fail", Toast.LENGTH_LONG);

            }
        });

    }

    private void initialData() {
        bookingAdapter = new BookingAdapter(BookingManagementActivity.this, R.layout.item_flight_booking, bookings);
        binding.lsListBooking.setAdapter(bookingAdapter);
    }
}