package org.ratmir.project.dto.moderation;

import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.UUID;

public record ModerationRecordDTO (
        UUID id,
        UUID bookId,
        String bookTitle,
        UUID moderatorId,
        String moderatorUsername,
        String comment,
        LocalDateTime createdAt
) {}
