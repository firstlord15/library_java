package org.ratmir.project.dto.user;

import java.util.UUID;

public record UserPublicDTO (
    UUID id,
    String username,
    String email,
    String phone
) {}
