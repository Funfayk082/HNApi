package com.example.helpnearby.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CompleteAnswerDto {
    private Long orderId ;
    private Long volonteurId;
    private Long answerId;
}
