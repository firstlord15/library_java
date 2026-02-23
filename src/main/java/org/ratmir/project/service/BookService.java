package org.ratmir.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.enums.ModerationStatus;
import org.ratmir.project.models.Book;
import org.ratmir.project.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    public Book addBook(Book book) {
        book.setStatus(ModerationStatus.PENDING);   // всегда PENDING при создании
        log.info("Add new Book: {}", book.getTitle());
        return repository.save(book);
    }

    public Book updateBook(UUID id, Book newBook) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found: " + id));

        book.setTitle(newBook.getTitle());
        book.setDescription(newBook.getDescription());
        book.setAuthors(newBook.getAuthors());
        book.setGenres(newBook.getGenres());
        book.setPhoto(newBook.getPhoto());
        book.setUpdatedAt(LocalDateTime.now());

        log.info("UPDATE Book: {}", id);
        return repository.save(book);
    }

    public void deleteBook(UUID id) {
        log.info("DELETE Book: {}", id);
        repository.deleteById(id);
    }

    // Публичный каталог — только одобренные книги
    public List<Book> getAllPublic() {
        log.info("GET All approved Books");
        return repository.findByStatus(ModerationStatus.APPROVED);
    }

    // Для админа/автора — все книги
    public List<Book> getAll() {
        log.info("GET All Books");
        return repository.findAll();
    }

    public Book getById(UUID id) {
        log.info("GET Book with id {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    public List<Book> getByTitle(String title) {
        log.info("GET Book with title {}", title);
        return repository.findByTitleContainingIgnoreCase(title);
    }
}
