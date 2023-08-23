package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void saveUser(User user, boolean isAdmin);
    List<User> getAllUsers();
    Optional<User> getUserById(long id);
    void editUser(User user, long id);
    void deleteUserById(long id);
    void updateUser(User user, String oldPassword, String newPassword);
}
