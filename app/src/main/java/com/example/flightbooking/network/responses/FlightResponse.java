package com.example.flightbooking.network.responses;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class FlightResponse {
    @SerializedName("flight_number")
    private String flightNumber;

    @SerializedName("departure_date_time")
    private LocalDateTime departureDateTime;

    @SerializedName("arrival_date_time")
    private LocalDateTime arrivalDateTime;

    private String status; // Chuyển về String

    @SerializedName("economy_class")
    private int economyClass;

    @SerializedName("business_class")
    private int businessClass;

    @SerializedName("special_class")
    private int specialClass;

    @SerializedName("economy_price")
    private int economyPrice;

    @SerializedName("business_price")
    private int businessPrice;

    @SerializedName("special_price")
    private int specialPrice;

    @SerializedName("from_airport")
    private AirportResponse fromAirport;

    @SerializedName("to_airport")
    private AirportResponse toAirport;

    @SerializedName("plane")
    private PlaneResponse plane;
}
