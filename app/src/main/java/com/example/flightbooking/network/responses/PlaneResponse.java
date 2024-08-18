package com.example.flightbooking.network.responses;

import com.google.gson.annotations.SerializedName;

public class PlaneResponse {

    private String name;

    private String type;

    @SerializedName("plane_code")
    private String planeCode;

    @SerializedName("seat_capacity")
    private int seatCapacity;
}
