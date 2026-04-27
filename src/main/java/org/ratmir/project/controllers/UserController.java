package org.ratmir.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.user.CreateUserDTO;
import org.ratmir.project.dto.user.UpdateUserDTO;
import org.ratmir.project.dto.user.UserProfileDTO;
import org.ratmir.project.dto.user.UserPublicDTO;
import org.ratmir.project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserPublicDTO>> getAllUsers() {
        log.info("GET /api/users");
        List<UserPublicDTO> users = service.getAllUsers();
        if (users.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPublicDTO> getUserById(@PathVariable UUID id) {
        log.info("GET /api/users/{}", id);
        return ResponseEntity.ok(service.getUserById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<UserPublicDTO> getUser(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email
    ) {
        if (username != null) {
            log.info("GET api/users/search?username={}", username);
            return ResponseEntity.ok(service.getUserByUsername(username));
        }
        if (email != null) {
            log.info("GET api/users/search?email={}", email);
            return ResponseEntity.ok(service.getUserByEmail(email));
        }

        throw new IllegalArgumentException("Provide username or email");
    }

    @PostMapping
    public ResponseEntity<UserProfileDTO> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        log.info("POST /api/users - username: {}", createUserDTO.getUsername());
        UserProfileDTO dto = service.createUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO>  updateUser(@RequestBody @Valid UpdateUserDTO updateUserDTO, @PathVariable UUID id) {
        log.info("PUT /api/users/{}", id);
        UserProfileDTO dto = service.updateUser(id, updateUserDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        log.info("DELETE /api/users/{}", id);
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
