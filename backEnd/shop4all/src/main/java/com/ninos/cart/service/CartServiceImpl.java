package com.ninos.cart.service;

import com.ninos.cart.dtos.CartDTO;
import com.ninos.cart.entity.Cart;
import com.ninos.cart.repository.CartRepository;
import com.ninos.cart_item.repository.CartItemRepository;
import com.ninos.user.dtos.UserDTO;
import com.ninos.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;



    @Override
    public CartDTO getCartById(Long cartId) {
        Cart cart = getCart(cartId);
        return getCartDTO(cart);
    }


    @Override
    public CartDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findCartByUserId(userId);
        return getCartDTO(cart);
    }


    @Override
    public void clearCart(Long cartId) {
        Cart cart = getCart(cartId);
        cartItemRepository.deleteAllByCartId(cartId);
        cart.clearCart();
        cartRepository.deleteById(cartId);
    }


    @Override
    public CartDTO initializeNewCartForUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        Cart cart = Optional.ofNullable(cartRepository.findCartByUserId(user.getId())).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
        return getCartDTO(cart);
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotalAmount();
    }


    private Cart getCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart with id: " + cartId + " not found!"));
        return cart;
    }


    private CartDTO getCartDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }



}
