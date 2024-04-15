package com.example.helpnearby.services;

import com.example.helpnearby.models.Role;
import com.example.helpnearby.repositories.AuthUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {
    private final ModelMapper modelMapper;
    private final AuthUserRepository authUserRepository;

    public AuthUserService(ModelMapper modelMapper, AuthUserRepository authUserRepository) {
        this.modelMapper = modelMapper;
        this.authUserRepository = authUserRepository;
    }

    public UserDetails getUserByLogin(String login) {
        return modelMapper.map(
                authUserRepository.findAuthUserByLogin(login)
                        .orElseThrow(() -> new UnableToSendNotificationException(login)),
                UserDetails.class
        );
    }
}
