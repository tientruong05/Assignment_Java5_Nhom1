package poly.edu.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import poly.edu.entity.*;
import java.util.List;
import java.util.Optional;

	public interface OrderDAO {
	    Optional<OrderEntity> findById(int id);
	    List<OrderEntity> findAll();
	    void save(OrderEntity order);
	    void update(OrderEntity order);
	    void delete(int id);
	}

	@Transactional
	@Repository
	class OrderDAOImpl implements OrderDAO {
	    @PersistenceContext
	    private EntityManager entityManager;

	    @Override
	    public Optional<OrderEntity> findById(int id) {
	        OrderEntity order = entityManager.find(OrderEntity.class, id);
	        return Optional.ofNullable(order);
	    }

	    @Override
	    public List<OrderEntity> findAll() {
	        return entityManager.createQuery("SELECT o FROM OrderEntity o", OrderEntity.class).getResultList();
	    }

	    @Override
	    public void save(OrderEntity order) {
	        entityManager.persist(order);
	    }

	    @Override
	    public void update(OrderEntity order) {
	        entityManager.merge(order);
	    }

	    @Override
	    public void delete(int id) {
	        OrderEntity order = entityManager.find(OrderEntity.class, id);
	        if (order != null) {
	            entityManager.remove(order);
	        }
	    }
	}
