package com.example.flightbooking.network.responses;

import com.google.gson.annotations.SerializedName;

public class SeatClassResponse {
    private String name;

    @SerializedName("total_weight")
    private int totalWeight;

    @SerializedName("priority_checkin")
    private int priorityCheckin;

    @SerializedName("select_seat")
    private int selectedSeat;
}
