package com.example.flightbooking.network.responses;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse
{
    private Long id;

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
