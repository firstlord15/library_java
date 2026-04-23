package org.ratmir.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.book.BookDetailDTO;
import org.ratmir.project.dto.moderation.ModerationRecordDTO;
import org.ratmir.project.enums.ModerationStatus;
import org.ratmir.project.exception.ResourceNotFoundException;
import org.ratmir.project.mapper.BookMapper;
import org.ratmir.project.mapper.ModerationMapper;
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
    private final BookMapper bookMapper;
    private final ModerationMapper mapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModerationRepository repository;

    @Transactional
    public ModerationRecordDTO approveBook(UUID moderatorId, UUID bookId, String comment) {
        return moderateBook(moderatorId, bookId, comment, ModerationStatus.APPROVED);
    }

    @Transactional
    public ModerationRecordDTO rejectBook(UUID moderatorId, UUID bookId, String comment) {
        return moderateBook(moderatorId, bookId, comment, ModerationStatus.REJECTED);
    }

    public ModerationRecordDTO moderateBook(UUID moderatorId, UUID bookId, String comment, ModerationStatus status) {
        Book book = getBookOrThrow(bookId);
        User moderator = getModeratorOrThrow(moderatorId);

        book.setStatus(status);
        bookRepository.save(book);

        ModerationRecord moderationRecord = new ModerationRecord();
        moderationRecord.setBook(book);
        moderationRecord.setModerator(moderator);
        moderationRecord.setComment(comment);
        moderationRecord.setStatus(status);

        log.info("{} book: {} by moderator: {}", status, bookId, moderatorId);
        ModerationRecord moderation = repository.save(moderationRecord);
        return mapper.toDTO(moderation);
    }

    public List<ModerationRecordDTO> getHistoryByBook(UUID bookId) {
        log.debug("Fetching history by bookId: {}", bookId);
        return repository.findByBookId(bookId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<ModerationRecordDTO> getHistoryByModerator(UUID moderatorId) {
        log.debug("Fetching history by moderatorId: {}", moderatorId);
        return repository.findByModeratorId(moderatorId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public ModerationRecordDTO getLastRecordByBook(UUID bookId) {
        log.debug("Fetching last record by bookId: {}", bookId);
        ModerationRecord moderation = repository.findTopByBookIdOrderByCreatedAtDesc(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("No moderation records for book: " + bookId));
        return mapper.toDTO(moderation);
    }

    public List<BookDetailDTO> getPendingBooks() {
        log.info("Fetching pending books");
        return bookRepository.findByStatus(ModerationStatus.PENDING)
                .stream()
                .map(bookMapper::toBookDetailDTO)
                .toList();
    }

    // Вспомогательные методы
    private Book getBookOrThrow(UUID bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));
    }

    private User getModeratorOrThrow(UUID moderatorId) {
        return userRepository.findById(moderatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Moderator not found: " + moderatorId));
    }
}
