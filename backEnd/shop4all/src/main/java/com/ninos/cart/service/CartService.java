package com.ninos.cart.service;

import com.ninos.cart.dtos.CartDTO;
import com.ninos.user.dtos.UserDTO;
import java.math.BigDecimal;

public interface CartService {
    CartDTO getCartById(Long cartId);
    CartDTO getCartByUserId(Long userId);
    void clearCart(Long cartId);
    CartDTO initializeNewCartForUser(UserDTO userDTO);
    BigDecimal getTotalPrice(Long cartId);
}
