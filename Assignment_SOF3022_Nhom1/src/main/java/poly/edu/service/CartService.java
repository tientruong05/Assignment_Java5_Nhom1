package poly.edu.service;

import java.util.List;
import java.util.Optional;
import poly.edu.entity.CartEntity;

public interface CartService {
    Optional<CartEntity> getCartById(int id);
    Optional<CartEntity> getCartByUserId(int userId);
    List<CartEntity> getAllCarts();
    void addCart(CartEntity cart);
    void updateCart(CartEntity cart);
    void removeCart(int id);
}

