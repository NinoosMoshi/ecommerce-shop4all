package com.ninos.order.dtos;

import com.ninos.order_item.dtos.OrderItemDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Long userId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDTO> items;
}
