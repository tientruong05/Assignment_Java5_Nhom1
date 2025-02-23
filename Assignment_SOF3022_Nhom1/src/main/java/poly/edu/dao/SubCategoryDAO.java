package poly.edu.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import poly.edu.entity.*;
import java.util.List;

public interface SubCategoryDAO {
    List<SubCategoryEntity> findByCategory_Id(int categoryId);
    List<SubCategoryEntity> findByStatus(int status);
    SubCategoryEntity findById(int id);
    List<SubCategoryEntity> findAll();
    void save(SubCategoryEntity subCategory);
    void update(SubCategoryEntity subCategory);
    void delete(int id);
}

@Transactional
@Repository
class SubCategoryDAOImpl implements SubCategoryDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SubCategoryEntity> findByCategory_Id(int categoryId) {
        String jql = "SELECT s FROM SubCategoryEntity s WHERE s.category.id = :categoryId";
        return entityManager.createQuery(jql, SubCategoryEntity.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public List<SubCategoryEntity> findByStatus(int status) {
        String jql = "SELECT s FROM SubCategoryEntity s WHERE s.status = :status";
        return entityManager.createQuery(jql, SubCategoryEntity.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public SubCategoryEntity findById(int id) {
        return entityManager.find(SubCategoryEntity.class, id);
    }

    @Override
    public List<SubCategoryEntity> findAll() {
        return entityManager.createQuery("SELECT s FROM SubCategoryEntity s", SubCategoryEntity.class).getResultList();
    }

    @Override
    public void save(SubCategoryEntity subCategory) {
        entityManager.persist(subCategory);
    }

    @Override
    public void update(SubCategoryEntity subCategory) {
        entityManager.merge(subCategory);
    }

    @Override
    public void delete(int id) {
        SubCategoryEntity subCategory = entityManager.find(SubCategoryEntity.class, id);
        if (subCategory != null) {
            entityManager.remove(subCategory);
        }
    }
}

