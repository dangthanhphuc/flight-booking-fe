package com.example.flightbooking.network.api;

import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.requests.BookingRequest;
import com.example.flightbooking.network.responses.BookingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingService {
    @POST("/booking")
    Call<Response<List<BookingResponse>>> booking(
            @Body BookingRequest bookingRequest
    );

    @GET("/booking/{userId}")
    Call<Response<List<BookingResponse>>> getBookingsByUserId(
            @Path("userId") Long userId
    );
}
