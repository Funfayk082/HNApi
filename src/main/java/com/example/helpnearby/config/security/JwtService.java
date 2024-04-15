package com.example.helpnearby.config.security;

import com.example.helpnearby.dto.AccessTokenDto;
import com.example.helpnearby.dto.RefreshTokenDto;
import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface JwtService {
    String extractLogin(String token);
    AccessTokenDto generateAccessToken(String login, String role);

    RefreshTokenDto generateRefreshToken(String login);

    <T> T extractClaim(String jwtToken, Function<Claims, T> function);

    String extractRole(String jwtToken);

    boolean validateToken(String jwtToken);
}
