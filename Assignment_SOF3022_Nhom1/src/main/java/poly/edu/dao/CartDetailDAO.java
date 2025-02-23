package poly.edu.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import poly.edu.entity.*;
import java.util.List;
import java.util.Optional;

public interface CartDetailDAO {
    Optional<CartDetailEntity> findById(int id);
    List<CartDetailEntity> findAll();
    List<CartDetailEntity> findByCartId(int cartId);
    Optional<CartDetailEntity> findByCartIdAndProductId(int cartId, int productId);
    void save(CartDetailEntity cartDetail);
    void update(CartDetailEntity cartDetail);
    void delete(int id);
}

@Transactional
@Repository
class CartDetailDAOImpl implements CartDetailDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CartDetailEntity> findById(int id) {
        CartDetailEntity cartDetail = entityManager.find(CartDetailEntity.class, id);
        return Optional.ofNullable(cartDetail);
    }

    @Override
    public List<CartDetailEntity> findAll() {
        return entityManager.createQuery("SELECT cd FROM CartDetailEntity cd", CartDetailEntity.class).getResultList();
    }

    @Override
    public List<CartDetailEntity> findByCartId(int cartId) {
        String jpql = "SELECT cd FROM CartDetailEntity cd WHERE cd.cart.id = :cartId";
        return entityManager.createQuery(jpql, CartDetailEntity.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }

    @Override
    public Optional<CartDetailEntity> findByCartIdAndProductId(int cartId, int productId) {
        String jpql = "SELECT cd FROM CartDetailEntity cd WHERE cd.cart.id = :cartId AND cd.product.id = :productId";
        try {
            CartDetailEntity cartDetail = entityManager.createQuery(jpql, CartDetailEntity.class)
                    .setParameter("cartId", cartId)
                    .setParameter("productId", productId)
                    .getSingleResult();
            return Optional.of(cartDetail);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(CartDetailEntity cartDetail) {
        entityManager.persist(cartDetail);
    }

    @Override
    public void update(CartDetailEntity cartDetail) {
        entityManager.merge(cartDetail);
    }

    @Override
    public void delete(int id) {
        CartDetailEntity cartDetail = entityManager.find(CartDetailEntity.class, id);
        if (cartDetail != null) {
            entityManager.remove(cartDetail);
        }
    }
}

