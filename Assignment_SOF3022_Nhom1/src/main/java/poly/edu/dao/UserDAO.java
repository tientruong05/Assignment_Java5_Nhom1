package poly.edu.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import poly.edu.entity.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO {
    Optional<UserEntity> findById(int id);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    long countUsers();
    List<UserEntity> findAllUsers();
    List<UserEntity> findByFullNameContaining(String fullName);
    List<UserEntity> findByRole(int role);
    void deleteByEmail(String email);
    void save(UserEntity user);
    void update(UserEntity user);
    void deleteById(int id);
    Optional<UserEntity> findByUsernameAndEmail(String username, String email);
	void saveAndFlush(UserEntity newUser);
}

@Repository
@Transactional
 class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserEntity> findById(int id) {
        UserEntity user = entityManager.find(UserEntity.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        String jql = "SELECT u FROM UserEntity u WHERE u.email = :email";
        return entityManager.createQuery(jql, UserEntity.class)
                .setParameter("email", email)
                .getResultList()
                .stream().findFirst();
    }

    @Override
    public Optional<UserEntity> findByEmailAndPassword(String email, String password) {
        String jql = "SELECT u FROM UserEntity u WHERE u.email = :email AND u.password = :password";
        return entityManager.createQuery(jql, UserEntity.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList()
                .stream().findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        String jql = "SELECT COUNT(u) FROM UserEntity u WHERE u.email = :email";
        Long count = entityManager.createQuery(jql, Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public long countUsers() {
        String jql = "SELECT COUNT(u) FROM UserEntity u";
        return entityManager.createQuery(jql, Long.class).getSingleResult();
    }

    @Override
    public List<UserEntity> findAllUsers() {
        String jql = "SELECT u FROM UserEntity u";
        return entityManager.createQuery(jql, UserEntity.class).getResultList();
    }

    @Override
    public List<UserEntity> findByFullNameContaining(String fullName) {
        String jql = "SELECT u FROM UserEntity u WHERE u.fullName LIKE :fullName";
        return entityManager.createQuery(jql, UserEntity.class)
                .setParameter("fullName", "%" + fullName + "%")
                .getResultList();
    }

    @Override
    public List<UserEntity> findByRole(int role) {
        String jql = "SELECT u FROM UserEntity u WHERE u.role = :role";
        return entityManager.createQuery(jql, UserEntity.class)
                .setParameter("role", role)
                .getResultList();
    }

    @Override
    public void deleteByEmail(String email) {
        String jql = "DELETE FROM UserEntity u WHERE u.email = :email";
        entityManager.createQuery(jql)
                .setParameter("email", email)
                .executeUpdate();
    }

    @Override
    public void save(UserEntity user) {
        entityManager.persist(user);
    }

    @Override
    public void update(UserEntity user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteById(int id) {
        UserEntity user = entityManager.find(UserEntity.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public Optional<UserEntity> findByUsernameAndEmail(String username, String email) {
        String jql = "SELECT u FROM UserEntity u WHERE u.username = :username AND u.email = :email";
        return entityManager.createQuery(jql, UserEntity.class)
                .setParameter("username", username)
                .setParameter("email", email)
                .getResultList()
                .stream().findFirst();
    }

	@Override
	public void saveAndFlush(UserEntity newUser) {
		// TODO Auto-generated method stub
		
	}
}
