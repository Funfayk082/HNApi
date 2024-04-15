package com.example.helpnearby.services;

import com.example.helpnearby.dto.CompleteAnswerDto;
import com.example.helpnearby.dto.GetAnswerDto;
import com.example.helpnearby.models.Answer;
import com.example.helpnearby.models.Order;
import com.example.helpnearby.repositories.AnswerRepository;
import com.example.helpnearby.repositories.OrderRepository;
import com.example.helpnearby.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public AnswerService(AnswerRepository answerRepository, OrderRepository orderRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long save(GetAnswerDto dto) {
        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(
                () -> new RuntimeException("Order not found"));
        order.setStatus("Принят");

        return answerRepository.save(Answer.builder()
                .order(order)
                .user(userRepository.findById(dto.getVolonteurId()).orElseThrow(
                        () -> new RuntimeException("User not found")
                ))
                .status("В работе")
                .build()).getAnswerId();
    }

    public List<GetAnswerDto> findAll() {
        return answerRepository.findAll().stream().map(answer ->
                GetAnswerDto.builder()
                        .orderId(answer.getOrder().getId())
                        .volonteurId(answer.getUser().getId())
                        .status(answer.getStatus())
                        .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    public void completeAnswer(CompleteAnswerDto dto) {
        orderRepository.findById(dto.getOrderId()).ifPresent(order -> {
                    order.setStatus("Выполнен");
                }
        );
        answerRepository.findById(dto.getAnswerId()).ifPresent(answer -> {
                    answer.setStatus("Выполнен");
                }
        );
    }
}
