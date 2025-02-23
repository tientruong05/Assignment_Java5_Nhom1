package poly.edu.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import poly.edu.entity.*;
import java.util.List;

public interface CategoryDAO {
    List<CategoryEntity> findByStatus(int status);
    List<CategoryEntity> findByNameContaining(String name);
    CategoryEntity findById(int id);
    List<CategoryEntity> findAll();
    void save(CategoryEntity category);
    void update(CategoryEntity category);
    void delete(int id);
}

@Transactional
@Repository
class CategoryDAOImpl implements CategoryDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CategoryEntity> findByStatus(int status) {
        String jql = "SELECT c FROM CategoryEntity c WHERE c.status = :status";
        return entityManager.createQuery(jql, CategoryEntity.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public List<CategoryEntity> findByNameContaining(String name) {
        String jql = "SELECT c FROM CategoryEntity c WHERE c.name LIKE :name";
        return entityManager.createQuery(jql, CategoryEntity.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public CategoryEntity findById(int id) {
        return entityManager.find(CategoryEntity.class, id);
    }

    @Override
    public List<CategoryEntity> findAll() {
        String jql = "SELECT c FROM CategoryEntity c";
        return entityManager.createQuery(jql, CategoryEntity.class).getResultList();
    }

    @Override
    public void save(CategoryEntity category) {
        entityManager.persist(category);
    }

    @Override
    public void update(CategoryEntity category) {
        entityManager.merge(category);
    }

    @Override
    public void delete(int id) {
        CategoryEntity category = entityManager.find(CategoryEntity.class, id);
        if (category != null) {
            entityManager.remove(category);
        }
    }
}
