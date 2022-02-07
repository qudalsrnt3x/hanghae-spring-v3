package com.hanghae.hanghaespringv3.controller;

import com.hanghae.hanghaespringv3.dto.OrderRequestDto;
import com.hanghae.hanghaespringv3.dto.OrderResponse;
import com.hanghae.hanghaespringv3.model.Order;
import com.hanghae.hanghaespringv3.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/order/request")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {

        OrderResponse order = orderService.createOrder(orderRequestDto);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getOrders() {
        List<OrderResponse> orders = orderService.getOrders();

        return ResponseEntity.ok(orders);
    }
}
