package com.example.helpnearby.controller;

import com.example.helpnearby.dto.OrderDto;
import com.example.helpnearby.services.OrderService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    @ApiResponse(
            responseCode = "200",
            description = "Get all orders by city",
            content = @Content(mediaType = "application/json")
    )
    public ResponseEntity<?> getOrderByCityName(@RequestParam String name) {
        return ResponseEntity.ok().body(orderService.findAllByCityName(name));
    }

    @PostMapping("/order")
    @ApiResponse(
            responseCode = "201",
            description = "Save order",
            content = @Content(mediaType = "application/json")
    )
    public ResponseEntity<?> hello(@RequestBody OrderDto order) {
        return ResponseEntity.ok().body(orderService.save(order));
    }

}
