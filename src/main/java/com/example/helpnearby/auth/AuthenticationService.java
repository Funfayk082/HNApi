package com.example.helpnearby.auth;

import com.example.helpnearby.config.security.JwtService;
import com.example.helpnearby.dto.AuthUserDto;
import com.example.helpnearby.dto.RefreshTokenDto;
import com.example.helpnearby.dto.RegisterUserDto;
import com.example.helpnearby.dto.Token;
import com.example.helpnearby.exceptions.UserNotFoundException;
import com.example.helpnearby.models.AuthUser;
import com.example.helpnearby.models.User;
import com.example.helpnearby.repositories.AuthUserRepository;
import com.example.helpnearby.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthUserRepository authUserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper mapper;

    public Token register(RegisterUserDto user) {
        var authUser = AuthUser.builder()
                .login(user.getLogin())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole()).build();
        var userSave = User.builder()
                .fullName(user.getFullName())
                .cityName(user.getCityName())
                .authUser(authUser)
                .contactNumber(user.getContactNumber())
                .groupId(user.getGroupId())
                .build();
        authUserRepository.save(authUser);
        userRepository.save(userSave);

        var accessToken = jwtService.generateAccessToken(
                user.getLogin(),
                user.getRole().name()
        );
        var refreshToken = jwtService.generateRefreshToken(
                user.getLogin()
        );
        return new Token(accessToken, refreshToken);
    }

    public Token login(AuthUserDto authUser) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authUser.getLogin(),
                        authUser.getPassword()
                )
        );
        var user = authUserRepository.findAuthUserByLogin(authUser.getLogin()).orElseThrow(
                () -> new UserNotFoundException(authUser.getLogin())
        );
        var accessToken = jwtService.generateAccessToken(
                user.getLogin(),
                user.getRole().name()
        );
        var refreshToken = jwtService.generateRefreshToken(
                user.getLogin()
        );
        return new Token(accessToken, refreshToken);
    }

    public Token refresh(String refreshToken) {
        var login = jwtService.extractLogin(refreshToken);
        var role = jwtService.extractRole(refreshToken);
        var accessToken = jwtService.generateAccessToken(login, role);
        var newRefreshToken = jwtService.generateRefreshToken(login);
        return new Token(accessToken, newRefreshToken);
    }
}
