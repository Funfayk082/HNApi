package com.example.helpnearby.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    private String fullName;
    private String cityName;
    private Long groupId;
    private String groupName;
    private String fbToken;
}
