package poly.edu.service;

import poly.edu.entity.UserEntity;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserEntity> getAllUsers();
    Optional<UserEntity> getUserById(int id);
    List<UserEntity> searchUsersByName(String fullName);
    List<UserEntity> getUsersByRole(int role);
    Optional<UserEntity> getUserByEmail(String email);
    Optional<UserEntity> getUserByEmailAndPassword(String email, String password);
    void createUser(UserEntity user);
    void updateUser(UserEntity user);
    void deleteUser(int id);
    boolean existsByEmail(String email);
    Optional<UserEntity> findByUsernameAndEmail(String username, String email);
}