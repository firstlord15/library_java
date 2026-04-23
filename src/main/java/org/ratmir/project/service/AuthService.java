package org.ratmir.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.enums.Role;
import org.ratmir.project.models.User;
import org.ratmir.project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public User getCurrentUser() {
        // 🔧 ВРЕМЕННАЯ ЗАГЛУШКА для тестирования
        // TODO: заменить на SecurityContextHolder.getContext().getAuthentication()
        //       когда добавим JWT авторизацию

        log.warn("Using test user stub - replace with real authentication!");
        return userRepository.findByUsername("test_user")
                .orElseGet(() -> {
                    log.info("Creating test user for development");
                    User testUser = new User();
                    testUser.setUsername("test_user");
                    testUser.setPasswordHash(encoder.encode("password"));
                    testUser.setEmail("test@test.com");
                    testUser.setRole(Role.USER);
                    return userRepository.save(testUser);
                });
    }
}
