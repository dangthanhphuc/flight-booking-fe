package com.example.flightbooking.network.api;

import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.models.Specialty;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TestService {

    @GET("/specialties")
    Call<Response<List<Specialty>>> getSpecialties();

}
