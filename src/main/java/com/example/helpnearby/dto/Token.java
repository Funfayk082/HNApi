package com.example.helpnearby.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
    private AccessTokenDto accessToken;
    private RefreshTokenDto refreshToken;
}
