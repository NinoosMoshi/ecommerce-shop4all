package com.ninos.cart_item.repository;

import com.ninos.cart_item.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByProductId(Long productId);

    void deleteAllByCartId(Long cartId);
}
