package com.example.helpnearby.services;

import com.example.helpnearby.dto.OrderDto;
import com.example.helpnearby.models.Order;
import com.example.helpnearby.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MessageService messageService;
    private final ModelMapper mapper;

    public OrderService(OrderRepository orderRepository, MessageService messageService, ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.messageService = messageService;
        this.mapper = mapper;
    }

    public List<Order> findAllByCityName(String name) {
        return orderRepository.findAllByCityName(name);
    }

    public Long save(OrderDto order) {
        Order newOrder = mapper.map(order, Order.class);
        messageService.sendMessage();
        return orderRepository.save(newOrder).getId();
    }
}
