package com.example.ecom.services;

import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.example.ecom.Repositories.CartRepository;
import com.example.ecom.Repositories.ProductRepository;
import com.example.ecom.model.Cart;
import com.example.ecom.model.CartItem;
import com.example.ecom.model.Product;
import com.example.ecom.model.User;

@Service
public class CartService {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;

    public CartService(CartRepository cartRepo, ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    public Cart getCart(Long userId) {
        return cartRepo.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(new User());
                    cart.getUser().setId(userId);
                    cart.setValue(0);
                    return cartRepo.save(cart);
                });
    }

    public Cart addItem(Long userId, Long productId, int qty) {
        Cart cart = getCart(userId);
        Product product = productRepo.findById(productId).orElseThrow();
        Iterator<CartItem> it = cart.getItems().iterator();
        while (it.hasNext()) {
            CartItem item = it.next();
            if (item.getProduct().getId().equals(productId)) {

                if (qty == 0) {
                    cart.setValue(cart.getValue() - (item.getQuantity() * item.getProduct().getPrice()));
                    it.remove();
                } else {
                    item.setQuantity(item.getQuantity() + qty);

                    if (item.getQuantity() <= 0) {
                        it.remove();
                    }
                    cart.setValue(cart.getValue() + (qty * item.getProduct().getPrice()));
                }

                return cartRepo.save(cart);
            }
        }

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(qty);
        cart.setValue(cart.getValue() + (qty * product.getPrice()));
        cart.getItems().add(item);
        return cartRepo.save(cart);
    }

    public Cart removeItem(Long userId, Long cartItemId) {
        Cart cart = cartRepo.findByUserId(userId).orElseThrow();

        Iterator<CartItem> it = cart.getItems().iterator();
        double deducted = 0;
        while (it.hasNext()) {
            CartItem item = it.next();
            if (item.getId().equals(cartItemId)) {
                deducted = item.getQuantity() * item.getProduct().getPrice();
                it.remove();
                break;
            }
        }

        cart.setValue(cart.getValue() - deducted);
        return cartRepo.save(cart);
    }

    public void clearCart(Cart cart) {
        cart.getItems().clear();
        cartRepo.save(cart);
    }
}
