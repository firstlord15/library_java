package org.ratmir.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.models.User;
import org.ratmir.project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        log.info("GET All Users");
        return repository.findAll();
    }

    public User getUserById(UUID id) {
        log.info("GET User by ID {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User getUserByUsername(String username) {
        log.info("GET User by username {}", username);
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public User getUserByEmail(String email) {
        log.info("GET User by email {}", email);
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User createUser(User user) {
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken: " + user.getUsername());
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        log.info("Create User {}", user.getUsername());
        return repository.save(user);
    }

    public void deleteUser(UUID id) {
        log.info("Delete User {}", id);
        repository.deleteById(id);
    }

    public User updateUser(UUID id, User user) {
        User currentUser = getUserById(id);
        currentUser.setEmail(user.getEmail());
        currentUser.setPhone(user.getPhone());

        log.info("Update User {}", user.getId());
        return repository.save(currentUser);
    }

    public void changePassword(UUID id, String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            throw new RuntimeException("New password must be different from the old one");
        }

        User currentUser = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (!passwordEncoder.matches(oldPassword, currentUser.getPasswordHash())) {
            throw new RuntimeException("Wrong old password");
        }

        currentUser.setPasswordHash(passwordEncoder.encode(newPassword));
        log.info("Change Password {}", currentUser.getUsername());
        repository.save(currentUser);
    }
}
