package com.ninos.cart_item.dtos;

import com.ninos.product.dtos.ProductDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private ProductDTO product;
}
