package com.ninos.order.controller;

import com.ninos.order.dtos.OrderDTO;
import com.ninos.order.service.OrderService;
import com.ninos.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;


    @PostMapping("/user/order")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam Long userId) {
        OrderDTO orderDTO = orderService.placeOrder(userId);
        return ResponseEntity.ok().body(new ApiResponse("Order Placed successfully!", orderDTO));
    }


    @GetMapping("/user/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        List<OrderDTO> orderDTOS = orderService.getUserOrders(userId);
        return ResponseEntity.ok().body(new ApiResponse("Success!", orderDTOS));
    }

}
