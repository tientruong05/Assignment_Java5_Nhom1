package poly.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.dao.CartDetailDAO;
import poly.edu.entity.CartDetailEntity;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    private CartDetailDAO cartDetailDAO;

    @Override
    public Optional<CartDetailEntity> getCartDetailById(int id) {
        return cartDetailDAO.findById(id);
    }

    @Override
    public List<CartDetailEntity> getCartDetailsByCartId(int cartId) {
        return cartDetailDAO.findByCartId(cartId);
    }

    @Override
    public Optional<CartDetailEntity> findByCartAndProduct(int cartId, int productId) {
        return cartDetailDAO.findByCartIdAndProductId(cartId, productId);
    }

    @Override
    public void saveOrUpdate(CartDetailEntity cartDetail) {
        if (cartDetail.getId() == 0) {
            cartDetailDAO.save(cartDetail);
        } else {
            cartDetailDAO.update(cartDetail);
        }
    }

    @Override
    public void updateCartDetail(CartDetailEntity cartDetail) {
        cartDetailDAO.update(cartDetail);
    }

    @Override
    public void removeCartDetail(int id) {
        cartDetailDAO.delete(id);
    }
}