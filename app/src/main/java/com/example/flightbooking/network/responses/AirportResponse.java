package com.example.flightbooking.network.responses;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponse {

    private Long id;

    @SerializedName("country_code")
    private String countryCode;

    private String name;

    private String country;

    private String city;

    private String location;

    private int coefficient;
}
