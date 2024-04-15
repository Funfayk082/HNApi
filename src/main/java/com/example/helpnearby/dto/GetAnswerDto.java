package com.example.helpnearby.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAnswerDto {
    private Long orderId;
    private Long volonteurId;
    private String status;
}
