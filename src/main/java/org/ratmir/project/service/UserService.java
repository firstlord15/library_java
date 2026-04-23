package org.ratmir.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.user.CreateUserDTO;
import org.ratmir.project.dto.user.UpdateUserDTO;
import org.ratmir.project.dto.user.UserPublicDTO;
import org.ratmir.project.enums.Role;
import org.ratmir.project.exception.IllegalArgumentException;
import org.ratmir.project.exception.ResourceNotFoundException;
import org.ratmir.project.mapper.UserMapper;
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
    private final UserMapper mapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<UserPublicDTO> getAllUsers() {
        log.debug("Fetching all users");
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public UserPublicDTO getUserById(UUID id) {
        log.debug("Fetching user by id: {}", id);
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return mapper.toDTO(user);
    }

    public UserPublicDTO getUserByUsername(String username) {
        log.debug("Fetching user by username: {}", username);
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        return mapper.toDTO(user);
    }

    public UserPublicDTO getUserByEmail(String email) {
        log.debug("Fetching user by email: {}", email);
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return mapper.toDTO(user);
    }

    public void deleteUser(UUID id) {
        log.debug("Deleting user by id: {}", id);
        repository.deleteById(id);
    }

    @Transactional
    public UserPublicDTO createUser(CreateUserDTO dto) {
        if (repository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken: " + dto.getUsername());
        }

        User user = mapper.fromCreatedUser(dto);
        user.setRole(Role.USER);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        User saved = repository.save(user);
        log.debug("User saved with id: {}", saved.getId());
        return mapper.toDTO(saved);
    }

    @Transactional
    public UserPublicDTO updateUser(UUID id, UpdateUserDTO dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        mapper.updateFromDTO(dto, user);
        User updated = repository.save(user);

        log.debug("User updated with username: {}", updated.getUsername());
        return mapper.toDTO(updated);
    }

    @Transactional
    public void changePassword(UUID id, String oldPassword, String newPassword) {
        User currentUser = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!passwordEncoder.matches(oldPassword, currentUser.getPasswordHash())) {
            throw new IllegalArgumentException("Wrong old password");
        }

        if (oldPassword.equals(newPassword)) {
            throw new IllegalArgumentException("New password must be different from the old one");
        }

        currentUser.setPasswordHash(passwordEncoder.encode(newPassword));
        repository.save(currentUser);
        log.debug("Password changed for user: {}", currentUser.getUsername());
    }
}
