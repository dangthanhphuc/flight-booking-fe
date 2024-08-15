package com.example.flightbooking.network.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Specialty {

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;
}
