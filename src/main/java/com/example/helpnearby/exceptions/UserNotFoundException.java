package com.example.helpnearby.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super("Не найден пользователь: " + message);
    }
}
