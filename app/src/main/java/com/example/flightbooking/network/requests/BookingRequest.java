package com.example.flightbooking.network.requests;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;

public class BookingRequest {
    @SerializedName("basic_information")
    private List<BasicInformation> basicInformation;

    @SerializedName("flight_id")
    private Long flightId;

    @SerializedName("passenger_id")
    private Long passengerId;

    @SerializedName("seat_class_id")
    private Long seatClassId;

    @Getter
    public static class BasicInformation {

        @SerializedName("first_name")
        private String firstName;

        @SerializedName("last_name")
        private String lastName;

        @SerializedName("date_of_birth")
        private LocalDate dateOfBirth;

        @SerializedName("citizen_identification")
        private String citizenIdentification;

        @SerializedName("total_amount")
        private float totalAmount;
    }
}
