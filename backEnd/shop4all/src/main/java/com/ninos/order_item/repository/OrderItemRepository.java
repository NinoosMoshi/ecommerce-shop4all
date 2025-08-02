package com.ninos.order_item.repository;

import com.ninos.order_item.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByProductId(Long productId);
}

