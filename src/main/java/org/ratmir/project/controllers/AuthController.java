package org.ratmir.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.auth.LoginRequestDTO;
import org.ratmir.project.dto.auth.LoginResponseDTO;
import org.ratmir.project.dto.user.CreateUserDTO;
import org.ratmir.project.dto.user.UserProfileDTO;
import org.ratmir.project.service.AuthService;
import org.ratmir.project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> Login(@Valid @RequestBody LoginRequestDTO dto) {
        log.info("POST /api/auth/login - username: {}", dto.getUsername());
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<UserProfileDTO> register(@Valid @RequestBody CreateUserDTO dto) {
        log.info("POST /api/auth/register - username: {}", dto.getUsername());
        UserProfileDTO user = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
