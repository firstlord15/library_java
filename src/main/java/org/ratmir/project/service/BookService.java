package org.ratmir.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.models.Author;
import org.ratmir.project.models.Book;
import org.ratmir.project.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private BookRepository repository;

    public Book addBook(Book book) {
        log.info("Add new Book: {}", book.getTitle());
        Book newBook = repository.save(book);

        return newBook;
    }

    public Book updateBook(UUID id, Book newBook) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        book.setTitle(newBook.getTitle());
        book.setDescription(newBook.getDescription() == null ? "" : newBook.getDescription());
        book.setAuthors(newBook.getAuthors());
        book.setRating(newBook.getRating());
        book.setPrice(newBook.getPrice());
        book.setPhoto(newBook.getPhoto());

        log.info("UPDATE Book: {}", id);
        Book updatedBook = repository.save(book);

        return updatedBook;
    }

    public void deleteBook(UUID id) {
        log.info("DELETE Book: {}", id);
        repository.deleteById(id);
    }

    public void deleteBook(Book book) { // TODO: проверить возможность убрать повторения
        log.info("DELETE Book: {}", book.getId());
        repository.deleteById(book.getId());
    }

    public List<Book> getAll() {
        log.info("GET All Books");
        return repository.findAll();
    }

    public Book getById(UUID id) {
        log.info("GET Book with id {}", id);
        Book book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        return book;
    }

    public List<Book> getByAuthor(Author author) {
        log.info("GET Book with author {}", author.getName());
        List<Book> books = repository.findByAuthors(List.of(author));

        return books;
    }

    public List<Book> getByTitle(String title) {
        log.info("GET Book with title {}", title);
        List<Book> books = repository.findByTitle(title);

        return books;
    }
}
