package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.exception.IncorrectPasswordException;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repo.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ru.itmentor.spring.boot_security.demo.exception.Error.WRONG_PASSWORD;
import static ru.itmentor.spring.boot_security.demo.model.Role.USER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user, boolean isAdmin) {
        if (user == null) {
            throw new RuntimeException("there is not provided user to save in database! ");
        }
        if (!isAdmin) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.setDefaultRole(user);
        } else {
            user.setPassword(passwordEncoder.encode("1234"));
        }
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(long id) {
        if (id <= 0) {
            throw new RuntimeException("Invalid id! ");
        }
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            throw new RuntimeException("No such user! ");
        }
        return userById;
    }

    @Override
    public void editUser(User user, long id) {
        Optional<User> userById = this.getUserById(user.getId());
        if (userById.isEmpty()) {
            throw new RuntimeException("Invalid user! ");
        }
        User dbUser = userById.get();
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setEmail(user.getEmail());
        if (user.getRoles() != null) {
            dbUser.setRoles(user.getRoles());
        } else {
            dbUser.setRoles(this.setDefaultRole(user));
        }
        userRepository.save(dbUser);
    }

    @Override
    public void deleteUserById(long id) {
        if (id <= 0) {
            throw new RuntimeException("Invalid id! ");
        }
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user, String oldPassword, String newPassword) {
        Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());
        if (userByEmail.isEmpty()) {
            throw new RuntimeException("invalid user! ");
        }
        User existing = userByEmail.get();
        if (!verifyPassword(existing, oldPassword)) {
            throw new IncorrectPasswordException(WRONG_PASSWORD);
        }
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setEmail(user.getEmail());
        if (newPassword != null && !newPassword.isEmpty()) {
            String newEncodedPassword = passwordEncoder.encode(newPassword);
            existing.setPassword(newEncodedPassword);
        }
        userRepository.save(existing);
    }

    private Set<Role> setDefaultRole(User user) {
        Set<Role> defaultRoles = new HashSet<>();
        defaultRoles.add(USER);
        user.setRoles(defaultRoles);
        return defaultRoles;
    }

    private boolean verifyPassword(User user, String oldPass) {
        return passwordEncoder.matches(oldPass, user.getPassword());
    }
}
