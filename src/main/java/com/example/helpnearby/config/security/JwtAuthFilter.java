package com.example.helpnearby.config.security;

import com.example.helpnearby.models.AuthUser;
import com.example.helpnearby.models.Role;
import com.example.helpnearby.services.AuthUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AuthUserService authUserService;


    @Override
    protected void doFilterInternal
            (
                    @NonNull HttpServletRequest request,
                    @NonNull HttpServletResponse response,
                    @NonNull FilterChain filterChain
            ) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        final String jwt;
        final String login;
        final String role;

        if (header == null ||!header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = header.substring(7);

        if(SecurityContextHolder.getContext().getAuthentication() == null) {
            if(jwtService.validateToken(jwt)) {
                login = jwtService.extractLogin(jwt);
                final UserDetails authUser = authUserService.getUserByLogin(login);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        authUser,
                        null,
                        authUser.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
