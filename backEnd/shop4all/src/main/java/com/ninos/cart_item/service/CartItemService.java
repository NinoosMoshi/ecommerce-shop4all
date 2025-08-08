package com.ninos.cart_item.service;

import com.ninos.cart_item.entity.CartItem;

public interface CartItemService {

    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);
    CartItem getCartItemFromCart(Long cartId, Long productId);

}
