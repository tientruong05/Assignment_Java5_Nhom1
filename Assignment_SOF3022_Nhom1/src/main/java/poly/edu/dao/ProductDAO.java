package poly.edu.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import poly.edu.entity.*;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    Optional<ProductEntity> findById(int id);
    List<ProductEntity> findByNameContaining(String name);
    List<ProductEntity> findAll();
    void save(ProductEntity product);
    void update(ProductEntity product);
    void delete(int id);
}

@Transactional
@Repository
class ProductDAOImpl implements ProductDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ProductEntity> findById(int id) {
        ProductEntity product = entityManager.find(ProductEntity.class, id);
        return Optional.ofNullable(product);
    }

    @Override
    public List<ProductEntity> findByNameContaining(String name) {
        return entityManager.createQuery("SELECT p FROM ProductEntity p WHERE p.name LIKE :name", ProductEntity.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<ProductEntity> findAll() {
        return entityManager.createQuery("SELECT p FROM ProductEntity p", ProductEntity.class).getResultList();
    }

    @Override
    public void save(ProductEntity product) {
        entityManager.persist(product);
    }

    @Override
    public void update(ProductEntity product) {
        entityManager.merge(product);
    }

    @Override
    public void delete(int id) {
        ProductEntity product = entityManager.find(ProductEntity.class, id);
        if (product != null) {
            entityManager.remove(product);
        }
    }
}