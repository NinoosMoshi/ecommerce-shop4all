package com.ninos.order.service;

import com.ninos.cart.entity.Cart;
import com.ninos.cart.repository.CartRepository;
import com.ninos.cart.service.CartService;
import com.ninos.enums.OrderStatus;
import com.ninos.order.dtos.OrderDTO;
import com.ninos.order.entity.Order;
import com.ninos.order.repository.OrderRepository;
import com.ninos.order_item.entity.OrderItem;
import com.ninos.product.entity.Product;
import com.ninos.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;


    @Transactional
    @Override
    public OrderDTO placeOrder(Long userId) {
        Cart cart = cartRepository.findCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return modelMapper.map(savedOrder, OrderDTO.class);
    }


    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        List<Order> orderList = orderRepository.findOrdersByUserId(userId);
        return orderList.stream().map((order) -> modelMapper.map(order, OrderDTO.class)).toList();
    }



    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }




    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);

            return new OrderItem(order, product, cartItem.getUnitPrice(), cartItem.getQuantity());
        }).toList();
    }

//    private List<OrderItem> createOrderItems(Order order, Cart cart) {
//        return cart.getItems().stream().map(cartItem -> {
//            Product product = cartItem.getProduct();
//            product.setInventory(product.getInventory() - cartItem.getQuantity());
//            productRepository.save(product);
//            return new OrderItem(
//                    order,
//                    product,
//                    cartItem.getUnitPrice(),
//                    cartItem.getQuantity());
//        }).toList();
//    }







    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
