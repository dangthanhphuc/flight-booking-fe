package com.example.flightbooking.network.api;

import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.responses.FlightResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlightService {

    @GET("/flights/search")
    Call<Response<List<FlightResponse>>> getFlightsFollowPlace(
            @Query("from_airport_id") Long fromAirportId,
            @Query("to_airport_id") Long toAirportId
    );

    @GET("/flights")
    Call<Response<List<FlightResponse>>> getFlights();

}
