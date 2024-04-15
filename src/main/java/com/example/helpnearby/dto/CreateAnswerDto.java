package com.example.helpnearby.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateAnswerDto {
    @NonNull
    private OrderDto order;
    @NonNull
    private UserDto volonteur;
}
