package org.ratmir.project.dto.user;

import java.util.UUID;

public record UserProfileDTO(
    UUID id,
    String username,
    String email,
    String phone
) {}
