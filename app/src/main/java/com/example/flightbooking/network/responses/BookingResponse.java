package com.example.flightbooking.network.responses;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingResponse {
    @SerializedName("booking_code")
    private String bookingCode;

    private LocalDateTime date;

    private String status; // Chuyển về String

    @SerializedName("total_amount")
    private float totalAmount;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("date_of_birth")
    private LocalDate dateOfBirth;

    private FlightResponse flight;

    private UserResponse user;

    @SerializedName("seat_class")
    private SeatClassResponse seatClass;
}
