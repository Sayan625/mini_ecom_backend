package com.example.ecom.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecom.Repositories.CartRepository;
import com.example.ecom.Repositories.OrderRepository;
import com.example.ecom.model.Cart;
import com.example.ecom.model.CartItem;
import com.example.ecom.model.OrderItem;
import com.example.ecom.model.Order;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;

    public OrderService(OrderRepository orderRepo, CartRepository cartRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
    }

    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    public List<Order> getByUserId(Long userId) {
        return orderRepo.findByUserId(userId);
    }

    public Order placeOrder(Long userId) {
        Cart cart = cartRepo.findByUserId(userId).orElseThrow();

        List<CartItem> cartItems = cart.getItems();
        

        Order order = new Order();
        order.setUserId(cart.getUser().getId());
        order.setPayment("PENDING");
        
        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;

        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            total+=(ci.getProduct().getPrice()*ci.getQuantity());
            oi.setValue(ci.getProduct().getPrice()*ci.getQuantity());
            orderItems.add(oi);
        }
        order.setItems(new ArrayList<>(orderItems));
        order.setTotalAmount(total);
        cart.getItems().clear();
        cart.setValue(0);
        cartRepo.save(cart);


        return orderRepo.save(order);
    }
}
