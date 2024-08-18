package com.example.flightbooking.network.api;

import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.responses.AirportResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AirportService {

    @GET("/airports")
    Call<Response<List<AirportResponse>>> getAirports();

}
