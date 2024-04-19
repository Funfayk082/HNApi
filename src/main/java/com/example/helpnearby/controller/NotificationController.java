package com.example.helpnearby.controller;

import com.example.helpnearby.dto.MessageDto;
import com.example.helpnearby.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

//    @Autowired
//    MessageService messageService;
//
//    @PostMapping
//    public ResponseEntity<?> sendMessage(@RequestBody MessageDto message) {
//        return ResponseEntity.ok(messageService.sendMessage(message));
//    }
}
