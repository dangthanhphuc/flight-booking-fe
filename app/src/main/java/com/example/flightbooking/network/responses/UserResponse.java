package com.example.flightbooking.network.responses;

import com.google.gson.annotations.SerializedName;

public class UserResponse
{
    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("phone_number")
    private String phoneNumber;

    private String email;

    @SerializedName("citizen_identification")
    private String citizenIdentification;

}
