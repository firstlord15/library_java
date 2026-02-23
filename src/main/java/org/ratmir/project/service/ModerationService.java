package org.ratmir.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.enums.ModerationStatus;
import org.ratmir.project.models.Book;
import org.ratmir.project.models.ModerationRecord;
import org.ratmir.project.models.User;
import org.ratmir.project.repository.BookRepository;
import org.ratmir.project.repository.ModerationRepository;
import org.ratmir.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModerationService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModerationRepository repository;

    public ModerationRecord approveBook(UUID moderatorId, UUID bookId, String comment) {
        Book book = getBookOrThrow(bookId);
        User moderator = getModeratorOrThrow(moderatorId);

        book.setStatus(ModerationStatus.APPROVED);
        bookRepository.save(book);

        ModerationRecord moderationRecord = new ModerationRecord();
        moderationRecord.setBook(book);
        moderationRecord.setModerator(moderator);
        moderationRecord.setComment(comment);
        moderationRecord.setStatus(ModerationStatus.APPROVED);
        moderationRecord.setCreatedAt(LocalDateTime.now());

        log.info("APPROVE Book: {} by moderator: {}", bookId, moderatorId);
        return repository.save(moderationRecord);
    }

    public ModerationRecord rejectBook(UUID moderatorId, UUID bookId, String comment) {
        Book book = getBookOrThrow(bookId);
        User moderator = getModeratorOrThrow(moderatorId);

        book.setStatus(ModerationStatus.REJECTED);
        bookRepository.save(book);

        ModerationRecord moderationRecord = new ModerationRecord();
        moderationRecord.setBook(book);
        moderationRecord.setModerator(moderator);
        moderationRecord.setComment(comment);
        moderationRecord.setStatus(ModerationStatus.REJECTED);
        moderationRecord.setCreatedAt(LocalDateTime.now());

        log.info("REJECT Book: {} by moderator: {}", bookId, moderatorId);
        return repository.save(moderationRecord);
    }

    public List<ModerationRecord> getHistoryByBook(UUID bookId) {
        log.info("Get History By Book: {}", bookId);
        return repository.findByBookId(bookId);
    }

    public List<ModerationRecord> getHistoryByModerator(UUID moderatorId) {
        log.info("Get History By Moderator: {}", moderatorId);
        return repository.findByModeratorId(moderatorId);
    }

    public ModerationRecord getLastRecordByBook(UUID bookId) {
        log.info("Get Last Record By Book: {}", bookId);
        return repository.findTopByBookIdOrderByCreatedAtDesc(bookId)
                .orElseThrow(() -> new RuntimeException("No moderation records for book: " + bookId));
    }

    public List<Book> getPendingBooks() {
        log.info("Get Pending Books");
        return bookRepository.findByStatus(ModerationStatus.PENDING);
    }

    private Book getBookOrThrow(UUID bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found: " + bookId));
    }

    private User getModeratorOrThrow(UUID moderatorId) {
        return userRepository.findById(moderatorId)
                .orElseThrow(() -> new RuntimeException("Moderator not found: " + moderatorId));
    }
}
