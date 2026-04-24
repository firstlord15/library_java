package org.ratmir.project.dto.author;

import java.util.UUID;

public record AuthorDetailDTO(
        UUID id,
        String name,
        String surname,
        String patronymic,
        String bio
) {}
