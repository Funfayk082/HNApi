package com.example.helpnearby.config.security;

import com.example.helpnearby.dto.AccessTokenDto;
import com.example.helpnearby.dto.RefreshTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${access-token-life-time-seconds}")
    private Integer ACCESS_TOKEN_LIFE_TIME;
    @Value("${refresh-token-life-time-seconds}")
    private Integer REFRESH_TOKEN_LIFE_TIME;
    @Value("${access-token-secret-key}")
    private String ACCESS_TOKEN_SECRET_KEY;
    @Value("${refresh-token-secret-key}")
    private String REFRESH_TOKEN_SECRET_KEY;

    @Override
    public String extractLogin(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public AccessTokenDto generateAccessToken(String login, String role) {
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("role", role);
        Date now = new Date();
        Date kill = new Date(now.getTime() + (ACCESS_TOKEN_LIFE_TIME * 1000));
        return new AccessTokenDto(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(kill)
                        .signWith(
                                Keys.hmacShaKeyFor(
                                        Decoders.BASE64.decode(ACCESS_TOKEN_SECRET_KEY)
                                )
                        )
                        .compact(),
                kill
        );
    }

    @Override
    public RefreshTokenDto generateRefreshToken(String login) {
        Claims claims = Jwts.claims().setSubject(login);
        Date now = new Date();
        Date kill = new Date(now.getTime() + REFRESH_TOKEN_LIFE_TIME * 1000);
        return new RefreshTokenDto(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(kill)
                        .signWith(Keys.hmacShaKeyFor(
                                Decoders.BASE64.decode(REFRESH_TOKEN_SECRET_KEY)))
                        .compact(),
                kill
        );
    }

    @Override
    public <T> T extractClaim(String jwtToken, Function<Claims, T> function) {
        return function.apply(extractAllClaims(jwtToken));
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(ACCESS_TOKEN_SECRET_KEY)))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    @Override
    public String extractRole(String jwtToken) {
        return extractAllClaims(jwtToken).get("role", String.class);
    }

    @Override
    public boolean validateToken(String jwtToken) {
        return !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpirationDate(jwtToken).before(new Date());
    }

    private Date extractExpirationDate(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }
}
