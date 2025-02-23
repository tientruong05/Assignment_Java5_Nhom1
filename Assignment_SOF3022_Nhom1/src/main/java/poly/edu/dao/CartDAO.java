package poly.edu.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import poly.edu.entity.*;
import java.util.List;
import java.util.Optional;

public interface CartDAO {
    Optional<CartEntity> findById(int id);
    List<CartEntity> findAll();
    void save(CartEntity cart);
    void update(CartEntity cart);
    void delete(int id);
    Optional<CartEntity> findByUserId(int userId);
}

@Transactional
@Repository
class CartDAOImpl implements CartDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CartEntity> findById(int id) {
        CartEntity cart = entityManager.find(CartEntity.class, id);
        return Optional.ofNullable(cart);
    }

    @Override
    public List<CartEntity> findAll() {
        return entityManager.createQuery("SELECT c FROM CartEntity c", CartEntity.class).getResultList();
    }

    @Override
    public void save(CartEntity cart) {
        entityManager.persist(cart);
    }

    @Override
    public void update(CartEntity cart) {
        entityManager.merge(cart);
    }

    @Override
    public void delete(int id) {
        CartEntity cart = entityManager.find(CartEntity.class, id);
        if (cart != null) {
            entityManager.remove(cart);
        }
    }

    @Override
    public Optional<CartEntity> findByUserId(int userId) {
        String jpql = "SELECT c FROM CartEntity c WHERE c.user.id = :userId";
        try {
            CartEntity cart = entityManager.createQuery(jpql, CartEntity.class)
                .setParameter("userId", userId)
                .getSingleResult();
            return Optional.of(cart);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}

