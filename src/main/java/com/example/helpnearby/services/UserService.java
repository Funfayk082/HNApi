package com.example.helpnearby.services;

import com.example.helpnearby.dto.UserDto;
import com.example.helpnearby.models.User;
import com.example.helpnearby.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final ModelMapper modelMapper ;
    private final UserRepository userRepository;

    public UserService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public Long save(UserDto userDto) {
        return userRepository.save(modelMapper.map(userDto, User.class)).getId();
    }
}
