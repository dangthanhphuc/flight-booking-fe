package com.example.flightbooking.network.api;

import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.requests.LoginRequest;
import com.example.flightbooking.network.requests.RegisterRequest;
import com.example.flightbooking.network.responses.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @POST("/identity/create-users")
    Call<Response<UserResponse>> register(@Body RegisterRequest request);

    @POST("/auth/log-in")
    Call<Response<String>> login(@Body LoginRequest loginRequest);

    @GET("/identity/get-users-by-username/{username}")
    Call<Response<UserResponse>> getUserByUsername(@Path("username") String username);

}
