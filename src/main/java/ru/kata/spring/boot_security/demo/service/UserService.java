package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User findByEmailWithRoles(String email);

    void saveUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    void adminRedactor(User user, Long id);
}