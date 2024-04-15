package com.example.helpnearby.dto;

import com.example.helpnearby.models.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserDto {
    private String login;
    private String password;
    private Role role;
    private String fullName;
    private String cityName;
    private String contactNumber;
    private Long groupId;
}
