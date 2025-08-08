package com.ninos.cart.repository;

import com.ninos.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findCartByUserId(Long userId);
}
