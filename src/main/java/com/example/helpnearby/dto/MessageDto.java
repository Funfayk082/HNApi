package com.example.helpnearby.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MessageDto {
    private String recipientToken;
    private String title;
    private String body;
    private String image;
    private Map<String, String> data;
}
