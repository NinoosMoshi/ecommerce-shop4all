package com.ninos.cart_item.controller;

import com.ninos.cart.dtos.CartDTO;
import com.ninos.cart.service.CartService;
import com.ninos.cart_item.service.CartItemService;
import com.ninos.user.dtos.UserDTO;
import com.ninos.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ninos.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cartItems")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;
    private final UserService userService;
    private final CartService cartService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart( Long cartId,
                                                      @RequestParam Long productId,
                                                      @RequestParam int quantity) {

        UserDTO user = userService.getUserById(cartId);
        CartDTO cart = cartService.initializeNewCartForUser(user);
        cartItemService.addItemToCart(cartId, productId, quantity);
        return ResponseEntity.ok(new ApiResponse("Item added successfully!", null));
    }


    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long itemId) {
        cartItemService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.ok(new ApiResponse("Item deleted successfully!", null));
    }


    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateCartItemQuantity(@PathVariable Long cartId,
                                                              @PathVariable Long itemId,
                                                              @RequestParam int quantity) {
        cartItemService.updateItemQuantity(cartId, itemId, quantity);
        return ResponseEntity.ok(new ApiResponse("Item updated successfully!", null));
    }

}
