package com.ninos.cart.controller;

import com.ninos.cart.dtos.CartDTO;
import com.ninos.cart.service.CartService;
import com.ninos.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @GetMapping("/user/{userId}/cart")
    public ResponseEntity<ApiResponse> getUserCart(@PathVariable Long userId) {
        CartDTO userCart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(new ApiResponse("Success", userCart));
    }


    @DeleteMapping("/cart/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new ApiResponse("clear cart successfully!", null));
    }

}
