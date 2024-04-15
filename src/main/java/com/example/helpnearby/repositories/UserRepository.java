package com.example.helpnearby.repositories;

import com.example.helpnearby.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAuthUser_AuthUserId(Long authUserId);
}
