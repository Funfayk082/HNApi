package com.example.helpnearby.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrderDto {
    @NonNull
    private String cityName;
    private String commentary;
    @NonNull
    private String status;
    private String geolocation;
    private String address;
}
