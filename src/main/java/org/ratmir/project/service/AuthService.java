package org.ratmir.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.auth.LoginRequestDTO;
import org.ratmir.project.dto.auth.LoginResponseDTO;
import org.ratmir.project.exception.ResourceNotFoundException;
import org.ratmir.project.models.User;
import org.ratmir.project.repository.UserRepository;
import org.ratmir.project.security.JwtService;
import org.ratmir.project.security.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager manager;
    private final UserDetailsServiceImpl userDetailsService;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        log.debug("Login request for user: {}", dto.getUsername());

        // 1. Аутентифицируем через Spring Security
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getUsername(), dto.getPassword()
        ));

        // 2. Загружаем UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());

        // 3. Генерируем токен
        String token = jwtService.generateToken(userDetails);
        return new LoginResponseDTO(token);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            throw new IllegalStateException("No authenticated user found");

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username not found"));
    }
}
