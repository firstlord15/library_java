package org.ratmir.project.repository;

import org.ratmir.project.models.Author;
import org.ratmir.project.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByAuthors(List<Author> authors);
    List<Book> findByTitle(String title);
}
