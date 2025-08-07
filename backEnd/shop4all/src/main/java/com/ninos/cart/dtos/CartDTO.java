package com.ninos.cart.dtos;

import com.ninos.cart_item.dtos.CartItemDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDTO {
    private Long cartId;
    private Set<CartItemDTO> items;
    private BigDecimal totalAmount;
}
