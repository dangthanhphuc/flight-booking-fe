package com.example.flightbooking.network.responses;

import com.google.gson.annotations.SerializedName;

public class AirportResponse {

    private Long id;

    @SerializedName("airport_code")
    private String airportCode;

    private String name;

    private String country;

    private String city;

    private String location;

    private int coefficient;
}
