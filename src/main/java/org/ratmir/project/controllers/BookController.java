package org.ratmir.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.book.BookDetailDTO;
import org.ratmir.project.dto.book.BookPublicDTO;
import org.ratmir.project.dto.book.CreateBookDTO;
import org.ratmir.project.dto.book.UpdateBookDTO;
import org.ratmir.project.service.AuthService;
import org.ratmir.project.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<BookPublicDTO>> getAllPublicBooks() {
        log.info("GET /api/books");
        List<BookPublicDTO> books = bookService.getAllPublic();
        if (books.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<BookDetailDTO>> getAll() {
        log.info("GET /api/books/admin/all");
        List<BookDetailDTO> books = bookService.getAll();

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookPublicDTO> getBook(@PathVariable UUID id) {
        log.info("GET /api/books/{}", id);
        return ResponseEntity.ok(bookService.getByIdPublic(id));
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<BookDetailDTO> getBookAuthor(@PathVariable UUID id) {
        log.info("GET /api/books/admin/{}", id);
        return ResponseEntity.ok(bookService.getByIdDetail(id));
    }

    @PostMapping
    public ResponseEntity<BookPublicDTO> createBook(@Valid @RequestBody CreateBookDTO createBookDTO) {
        log.info("POST /api/books - title: {}", createBookDTO.getTitle());
        BookPublicDTO dto = bookService.addBook(createBookDTO, authService.getCurrentUser());
        return  ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookPublicDTO> updateBook(@PathVariable UUID id, @Valid @RequestBody UpdateBookDTO dto) {
        log.info("PUT /api/books/{}", id);
        BookPublicDTO updateBook = bookService.updateBook(id, dto);
        return ResponseEntity.ok(updateBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        log.info("DELETE api/books/{}", id);
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookPublicDTO>> searchBooks(@RequestParam String query) {
        log.info("GET api/books/search?query: {}", query);
        List<BookPublicDTO> books = bookService.getByTitle(query);
        if (books.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(books);
    }
}
