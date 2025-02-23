package poly.edu.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.dao.UserDAO;
import poly.edu.entity.UserEntity;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public Optional<UserEntity> getUserById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userDAO.findAllUsers();
    }

    @Override
    public List<UserEntity> searchUsersByName(String fullName) {
        return userDAO.findByFullNameContaining(fullName);
    }

    @Override
    public List<UserEntity> getUsersByRole(int role) {
        return userDAO.findByRole(role);
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> getUserByEmailAndPassword(String email, String password) {
        return userDAO.findByEmailAndPassword(email, password);
    }

    @Override
    public void createUser(UserEntity user) {
        userDAO.save(user);
    }

    @Override
    public void updateUser(UserEntity user) {
        userDAO.update(user);
    }

    @Override
    public void deleteUser(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByUsernameAndEmail(String username, String email) {
        return userDAO.findByUsernameAndEmail(username, email);
    }
}