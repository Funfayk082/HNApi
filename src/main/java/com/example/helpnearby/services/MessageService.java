package com.example.helpnearby.services;

import com.example.helpnearby.dto.MessageDto;
import com.example.helpnearby.repositories.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final FirebaseMessaging firebaseMessaging;

    private final UserRepository userRepository;

    public MessageService(FirebaseMessaging firebaseMessaging, UserRepository userRepository) {
        this.firebaseMessaging = firebaseMessaging;
        this.userRepository = userRepository;
    }

    public String sendMessage() {
        String topic = "orders";
        Message message = Message.builder()
                .putData("info", "Данные на сервере обновлены")
                .setTopic(topic)
                .build();
        try {
            return FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new com.example.helpnearby.exceptions.FirebaseMessagingException(e.getMessage());
        }
    }

//    public String sendMessage(MessageDto messageDto) {
//        Notification notification = Notification
//                .builder()
//                .setTitle(messageDto.getTitle())
//                .setBody(messageDto.getBody())
//                .setImage(messageDto.getImage())
//                .build();
//
//        Message message = Message
//                .builder()
//                .setToken(messageDto.getRecipientToken())
//                .setNotification(notification)
//                .putAllData(messageDto.getData())
//                .build();
//
//        try {
//            firebaseMessaging.send(message);
//            return "success";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "error";
//        }
//    }
}
