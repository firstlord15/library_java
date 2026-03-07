package org.ratmir.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.BookAuthorDTO;
import org.ratmir.project.dto.BookPublicDTO;
import org.ratmir.project.dto.CreateBookDTO;
import org.ratmir.project.dto.UpdateBookDTO;
import org.ratmir.project.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController("/api/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookPublicDTO>> getAllPublicBooks() {
        log.info("Getting all books");
        List<BookPublicDTO> books = bookService.getAllPublic();

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<BookAuthorDTO>> getAll() {
        log.info("Admin: Getting all books");
        List<BookAuthorDTO> books = bookService.getAll();

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookPublicDTO> getBook(@PathVariable UUID id) {
        log.info("Getting book: {}", id);
        return ResponseEntity.ok(bookService.getByIdPublic(id));
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<BookAuthorDTO> getBookAuthor(@PathVariable UUID id) {
        log.info("Admin: Getting book: {}", id);
        return ResponseEntity.ok(bookService.getByIdAuthor(id));
    }

    @PostMapping
    public ResponseEntity<BookPublicDTO> createBook(@Valid @RequestBody CreateBookDTO createBookDTO) {
        log.info("Creating new book {}", createBookDTO);
        BookPublicDTO dto = bookService.addBook(createBookDTO);

        return  ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookPublicDTO> updateBook(@PathVariable UUID id, @Valid @RequestBody UpdateBookDTO dto) {
        log.info("Updating book: {}", id);
        BookPublicDTO updateBook = bookService.updateBook(id, dto);
        return ResponseEntity.ok(updateBook);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        log.info("Deleting book: {}", id);
        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookPublicDTO>> searchBooks(@RequestParam String query) {
        log.info("Searching books");

        List<BookPublicDTO> books = bookService.getByTitle(query);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(books);
    }
}
