package org.ratmir.project.repository;

import org.ratmir.project.enums.ModerationStatus;
import org.ratmir.project.models.ModerationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModerationRepository extends JpaRepository<ModerationRecord, UUID> {
    List<ModerationRecord> findByBookId(UUID bookId);
    List<ModerationRecord> findByModeratorId(UUID moderatorId);

    List<ModerationRecord> findByStatus(ModerationStatus status);
    Optional<ModerationRecord> findTopByBookIdOrderByCreatedAtDesc(UUID bookId);
}
