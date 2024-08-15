package com.example.flightbooking.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TestService {

    @GET("/specialties")
    Call<List<Specialty>> getSpecialties();

}
