package com.example.helpnearby.exceptions;

import com.example.helpnearby.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(FirebaseMessagingException.class)
    public ResponseEntity<ErrorResponseDto> handleFirebaseMessagingException(FirebaseMessagingException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
