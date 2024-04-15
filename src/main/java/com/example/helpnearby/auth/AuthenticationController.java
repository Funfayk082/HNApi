package com.example.helpnearby.auth;

import com.example.helpnearby.dto.AuthUserDto;
import com.example.helpnearby.dto.RefreshTokenDto;
import com.example.helpnearby.dto.RegisterUserDto;
import com.example.helpnearby.dto.Token;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    @ApiResponse(
            responseCode = "201",
            description = "User registered successfully",
            content = @Content(mediaType = "application/json")
    )
    public ResponseEntity<Token> register(@RequestBody RegisterUserDto user) {
        return ResponseEntity.ok(authenticationService.register(user));
    }

    @PostMapping("/login")
    @ApiResponse(
            responseCode = "200",
            description = "User logged in successfully",
            content = @Content(mediaType = "application/json")
    )
    public ResponseEntity<Token> login(@RequestBody AuthUserDto user) {
        return ResponseEntity.ok(authenticationService.login(user));
    }

    @PostMapping("/refresh")
    @ApiResponse(
            responseCode = "200",
            description = "User's token refreshed successfully",
            content = @Content(mediaType = "application/json")
    )
    public ResponseEntity<Token> refresh(@RequestBody RefreshTokenDto token) {
        return ResponseEntity.ok(authenticationService.refresh(token));
    }
}
