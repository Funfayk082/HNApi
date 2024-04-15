package com.example.helpnearby.config;

import com.example.helpnearby.config.security.JwtAuthFilter;
import com.example.helpnearby.config.security.JwtAuthenticationEntryPoint;
import com.example.helpnearby.config.security.JwtAuthenticationProvider;
import com.example.helpnearby.exceptions.UserNotFoundException;
import com.example.helpnearby.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(
        prePostEnabled = true
)
@Configuration
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final AuthUserRepository authUserRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/api/order").permitAll()
                                .requestMatchers("/api/register").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/api/login").permitAll()
                                .anyRequest().authenticated()

                )
                .exceptionHandling(handling -> handling.authenticationEntryPoint(jwtAuthenticationEntryPoint()))
                .authenticationProvider(jwtAuthenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (login) -> authUserRepository.findAuthUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList(request.getHeader("Origin")));
            config.setAllowedMethods(List.of("POST", "GET", "OPTIONS", "PUT", "DELETE", "PATCH", "HEAD"));
            config.setAllowedHeaders(List.of("Content-Type", "Authorization"));
            config.setAllowCredentials(true);
            return config;
        };
    }
}
