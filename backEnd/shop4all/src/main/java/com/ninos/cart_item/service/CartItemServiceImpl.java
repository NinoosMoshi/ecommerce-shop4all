package com.ninos.cart_item.service;

import com.ninos.cart.dtos.CartDTO;
import com.ninos.cart.entity.Cart;
import com.ninos.cart.repository.CartRepository;
import com.ninos.cart.service.CartService;
import com.ninos.cart_item.entity.CartItem;
import com.ninos.cart_item.repository.CartItemRepository;
import com.ninos.product.dtos.ProductDTO;
import com.ninos.product.entity.Product;
import com.ninos.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;


    @Transactional
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {

        // Validate quantity
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found."));
        ProductDTO productDTO = productService.getProductById(productId);

        Product product = modelMapper.map(productDTO, Product.class);

        CartItem cartItem = null;

        // Look for existing cart item manually (no stream)
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                cartItem = item;
                break;
            }
        }

        // If not found, create new CartItem
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setQuantity(quantity);
        } else {
            // If found, increase the quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        // Update total price
        cartItem.setTotalPrice();

        // Add to cart (optional depending on cart logic)
        cart.addItem(cartItem);

        // Save both entities
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found."));

        CartItem itemToRemove = getCartItemFromCart(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }


    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found."));
        CartItem foundItem = null;

        // Find the item in the cart
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                foundItem = item;
                break;
            }
        }

        // Update item if found
        if (foundItem != null) {
            foundItem.setQuantity(quantity);
            foundItem.setUnitPrice(foundItem.getProduct().getPrice());
            foundItem.setTotalPrice();
        }

        // Recalculate the total amount of the cart
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            totalAmount = totalAmount.add(item.getTotalPrice());
        }

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItemFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found."));
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                return item;
            }
        }
        throw new EntityNotFoundException("CartItem with product ID " + productId + " not found in cart ID " + cartId);
    }
}
