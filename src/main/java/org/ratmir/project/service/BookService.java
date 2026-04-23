package org.ratmir.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.book.BookDetailDTO;
import org.ratmir.project.dto.book.BookPublicDTO;
import org.ratmir.project.dto.book.CreateBookDTO;
import org.ratmir.project.dto.book.UpdateBookDTO;
import org.ratmir.project.enums.ModerationStatus;
import org.ratmir.project.exception.ResourceNotFoundException;
import org.ratmir.project.mapper.BookMapper;
import org.ratmir.project.models.Book;
import org.ratmir.project.models.User;
import org.ratmir.project.repository.AuthorRepository;
import org.ratmir.project.repository.BookRepository;
import org.ratmir.project.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository repository;
    private final BookMapper mapper;

    @Transactional
    public BookPublicDTO addBook(CreateBookDTO dto, User user) {
        Book book = mapper.fromCreateDTO(dto);
        book.setStatus(ModerationStatus.PENDING);   // всегда PENDING при создании
        book.setRating(0.0);
        book.setOrigin(user);
        book.setInventoryNumber(generateInventoryNumber());

        // Устанавливаем связи
        if (dto.getAuthorIds() != null) book.setAuthors(authorRepository.findAllById(dto.getAuthorIds()));
        if (dto.getGenreIds() != null) book.setGenres(genreRepository.findAllById(dto.getGenreIds()));

        book = repository.save(book);
        log.debug("Book saved with id: {}", book.getTitle());

        return mapper.toBookPublicDTO(book);
    }

    @Transactional
    public BookPublicDTO updateBook(UUID id, UpdateBookDTO dto) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));

        if (dto.getIsbn() != null && !dto.getIsbn().equals(book.getIsbn())) {
            log.warn("ISBN changed for book {}: {} -> {}", id, book.getIsbn(), dto.getIsbn());
            if (repository.existsByIsbn(dto.getIsbn())) {
                throw new IllegalArgumentException(
                        "Book with this ISBN already exists"
                );
            }
        }

        // Обновляем простые поля
        mapper.updateFromDTO(dto, book);

        // Обновляем связи
        if (dto.getAuthorIds() != null) book.setAuthors(authorRepository.findAllById(dto.getAuthorIds()));
        if  (dto.getGenreIds() != null) book.setGenres(genreRepository.findAllById(dto.getGenreIds()));

        book = repository.save(book);
        log.debug("Book updated with id: {}", id);

        return mapper.toBookPublicDTO(book);
    }

    @Transactional
    public void deleteBook(UUID id) {
        log.debug("Deleting book with id: {}", id);
        repository.deleteById(id);
    }

    // Публичный каталог — только одобренные книги
    public List<BookPublicDTO> getAllPublic() {
        log.debug("Fetching all approved books");
        return repository.findByStatus(ModerationStatus.APPROVED)
                .stream()
                .map(mapper::toBookPublicDTO)
                .toList();
    }

    // Для админа/автора — все книги
    public List<BookDetailDTO> getAll() {
        log.debug("Fetching all books");
        return repository.findAll()
                .stream()
                .map(mapper::toBookDetailDTO)
                .toList();
    }

    public BookPublicDTO getByIdPublic(UUID id) {
        log.debug("Fetching public book with id: {}", id);
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return mapper.toBookPublicDTO(book);
    }

    public BookDetailDTO getByIdDetail(UUID id) {
        log.debug("Fetching detail book with id: {}", id);
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return mapper.toBookDetailDTO(book);
    }

    public List<BookPublicDTO> getByTitle(String title) {
        log.debug("Fetching book with title: {}", title);
        return repository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(mapper::toBookPublicDTO)
                .toList();
    }

    private String generateInventoryNumber() {
        long count = repository.count() + 1;
        return String.format("LIB-%d-%05d", LocalDateTime.now().getYear(), count);
    }
}
