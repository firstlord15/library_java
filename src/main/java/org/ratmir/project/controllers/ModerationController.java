package org.ratmir.project.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.book.BookDetailDTO;
import org.ratmir.project.dto.moderation.ModerationActionDTO;
import org.ratmir.project.dto.moderation.ModerationRecordDTO;
import org.ratmir.project.service.AuthService;
import org.ratmir.project.service.ModerationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moderation")
public class ModerationController {
    private final AuthService authService;
    private final ModerationService service;

    @GetMapping("/pending")
    public ResponseEntity<List<BookDetailDTO>> getModerationRecords() {
        log.info("GET /api/moderation/pending");
        List<BookDetailDTO> moderations = service.getPendingBooks();
        if (moderations.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(moderations);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ModerationRecordDTO>> getHistoryByBookId(@PathVariable UUID bookId) {
        log.info("GET /api/moderation/book/{}", bookId);
        return ResponseEntity.ok(service.getHistoryByBook(bookId));
    }

    @GetMapping("/book/{bookId}/last")
    public ResponseEntity<ModerationRecordDTO> getHistoryByBookIdLast(@PathVariable UUID bookId) {
        log.info("GET /api/moderation/book/{}/last", bookId);
        return ResponseEntity.ok(service.getLastRecordByBook(bookId));
    }

    @GetMapping("/moderator/{moderatorId}")
    public ResponseEntity<List<ModerationRecordDTO>> getHistoryByModeratorId(@PathVariable UUID moderatorId) {
        log.info("GET /api/moderation/moderator/{}", moderatorId);
        return ResponseEntity.ok(service.getHistoryByModerator(moderatorId));
    }

    @PostMapping("/{bookId}/approve")
    public ResponseEntity<ModerationRecordDTO> approveBook(@PathVariable UUID bookId, @RequestBody ModerationActionDTO dto) {
        log.info("POST /api/moderation/{}/approve", bookId);
        return ResponseEntity.ok(service.approveBook(
                authService.getCurrentUser().getId(), bookId, dto.getComment()
        ));
    }

    @PostMapping("/{bookId}/reject")
    public ResponseEntity<ModerationRecordDTO> rejectBook(@PathVariable UUID bookId, @RequestBody ModerationActionDTO dto) {
        log.info("POST /api/moderation/{}/reject", bookId);
        return ResponseEntity.ok(service.rejectBook(
                authService.getCurrentUser().getId(), bookId, dto.getComment()
        ));
    }
}
