package org.ratmir.project.dto.user;

import org.ratmir.project.enums.Role;

import java.util.UUID;

public record UserDetailDTO (
        UUID id,
        String username,
        String email,
        String phone,
        Role role
) {}