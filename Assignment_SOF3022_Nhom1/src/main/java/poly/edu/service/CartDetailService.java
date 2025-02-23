package poly.edu.service;

import poly.edu.entity.CartDetailEntity;
import java.util.List;
import java.util.Optional;

public interface CartDetailService {
    Optional<CartDetailEntity> getCartDetailById(int id);
    List<CartDetailEntity> getCartDetailsByCartId(int cartId);
    Optional<CartDetailEntity> findByCartAndProduct(int cartId, int productId);
    void saveOrUpdate(CartDetailEntity cartDetail);
    void updateCartDetail(CartDetailEntity cartDetail);
    void removeCartDetail(int id);
}