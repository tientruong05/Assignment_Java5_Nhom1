package poly.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.entity.CartEntity;
import poly.edu.dao.CartDAO;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartDAO cartDAO;

    @Autowired
    public CartServiceImpl(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @Override
    public Optional<CartEntity> getCartById(int id) {
        return cartDAO.findById(id);
    }

    @Override
    public List<CartEntity> getAllCarts() {
        return cartDAO.findAll();
    }

    @Override
    public void addCart(CartEntity cart) {
        cartDAO.save(cart);
    }

    @Override
    public void updateCart(CartEntity cart) {
        cartDAO.update(cart);
    }

    @Override
    public void removeCart(int id) {
        cartDAO.delete(id);
    }

    @Override
    public Optional<CartEntity> getCartByUserId(int userId) {
        return cartDAO.findByUserId(userId);
    }
}

