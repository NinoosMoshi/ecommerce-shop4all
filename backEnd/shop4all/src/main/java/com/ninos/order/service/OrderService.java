package com.ninos.order.service;

import com.ninos.order.dtos.OrderDTO;
import java.util.List;

public interface OrderService {

    OrderDTO placeOrder(Long userId);
    List<OrderDTO> getUserOrders(Long userId);

}
