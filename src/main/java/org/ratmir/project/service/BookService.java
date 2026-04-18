package org.ratmir.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.BookAuthorDTO;
import org.ratmir.project.dto.BookPublicDTO;
import org.ratmir.project.dto.CreateBookDTO;
import org.ratmir.project.dto.UpdateBookDTO;
import org.ratmir.project.enums.ModerationStatus;
import org.ratmir.project.enums.Role;
import org.ratmir.project.exception.ResourceNotFoundException;
import org.ratmir.project.mapper.BookMapper;
import org.ratmir.project.models.Book;
import org.ratmir.project.models.User;
import org.ratmir.project.repository.AuthorRepository;
import org.ratmir.project.repository.BookRepository;
import org.ratmir.project.repository.GenreRepository;
import org.ratmir.project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public BookPublicDTO addBook(CreateBookDTO dto) {
        Book book = mapper.fromCreateDTO(dto);
        book.setStatus(ModerationStatus.PENDING);   // всегда PENDING при создании
        book.setRating(0.0);
        book.setOrigin(getCurrentUser());
        book.setInventoryNumber(generateInventoryNumber());
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());

        // Устанавливаем связи
        if (dto.getAuthorIds() != null) {
            book.setAuthors(authorRepository.findAllById(dto.getAuthorIds()));
        }
        if (dto.getGenreIds() != null) {
            book.setGenres(genreRepository.findAllById(dto.getGenreIds()));
        }

        log.info("Add new Book: {}", book.getTitle());
        book = repository.save(book);

        return mapper.toBookPublicDTO(book);
    }

    @Transactional
    public BookPublicDTO updateBook(UUID id, UpdateBookDTO dto) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));

        if (dto.getIsbn() != null && !dto.getIsbn().equals(book.getIsbn())) {
            log.warn("ISBN changed for book {}: {} -> {}",
                    id, book.getIsbn(), dto.getIsbn());

            if (repository.existsByIsbn(dto.getIsbn())) {
                throw new IllegalArgumentException(
                        "Book with this ISBN already exists"
                );
            }
        }

        // Обновляем простые поля
        mapper.updateFromDTO(dto, book);

        // Обновляем связи
        if (dto.getAuthorIds() != null) {
            book.setAuthors(authorRepository.findAllById(dto.getAuthorIds()));
        }
        if  (dto.getGenreIds() != null) {
            book.setGenres(genreRepository.findAllById(dto.getGenreIds()));
        }

        log.info("UPDATE Book: {}", id);
        book = repository.save(book);

        return mapper.toBookPublicDTO(book);
    }

    @Transactional
    public void deleteBook(UUID id) {
        log.info("DELETE Book: {}", id);
        repository.deleteById(id);
    }

    // Публичный каталог — только одобренные книги
    public List<BookPublicDTO> getAllPublic() {
        log.info("GET All approved Books");
        return repository.findByStatus(ModerationStatus.APPROVED)
                .stream()
                .map(mapper::toBookPublicDTO)
                .toList();
    }

    // Для админа/автора — все книги
    public List<BookAuthorDTO> getAll() {
        log.info("GET All Books");

        return repository.findAll()
                .stream()
                .map(mapper::toBookAuthorDTO)
                .toList();
    }

    public BookPublicDTO getByIdPublic(UUID id) {
        log.info("GET Public Book with id {}", id);
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        return mapper.toBookPublicDTO(book);
    }

    public BookAuthorDTO getByIdAuthor(UUID id) {
        log.info("GET Author Book with id {}", id);
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        return mapper.toBookAuthorDTO(book);
    }

    public List<BookPublicDTO> getByTitle(String title) {
        log.info("GET Book with title {}", title);
        return repository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(mapper::toBookPublicDTO)
                .toList();
    }

    private User getCurrentUser() {
        // 🔧 ВРЕМЕННАЯ ЗАГЛУШКА для тестирования
        // TODO: заменить на SecurityContextHolder.getContext().getAuthentication()
        //       когда добавим JWT авторизацию

        log.warn("Using test user stub - replace with real authentication!");

        return userRepository.findByUsername("test_user")
                .orElseGet(() -> {
                    log.info("Creating test user for development");
                    User testUser = new User();
                    testUser.setUsername("test_user");
                    testUser.setPasswordHash(passwordEncoder.encode("password"));
                    testUser.setEmail("test@test.com");
                    testUser.setRole(Role.USER);
                    return userRepository.save(testUser);
                });
    }

    private String generateInventoryNumber() {
        long count = repository.count() + 1;
        return String.format("LIB-%d-%05d", LocalDateTime.now().getYear(), count);
    }
}
