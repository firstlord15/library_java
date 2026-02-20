package org.ratmir.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.models.Author;
import org.ratmir.project.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    public Author getById(UUID id) {
        log.info("GET Author with id {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    public List<Author> getAll() {
        log.info("GET All Authors");
        return repository.findAll();
    }

    public Author createAuthor(Author author) {
        log.info("Create Author {}", author.getFullName());
        return repository.save(author);
    }

    public void deleteAuthor(UUID id) {
        log.info("Delete Author {}", id);
        repository.deleteById(id);
    }

    public  Author updateAuthor(UUID id, Author author) {
        Author currentAuthor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        currentAuthor.setBio(author.getBio());
        currentAuthor.setName(author.getName());
        currentAuthor.setSurname(author.getSurname());
        currentAuthor.setPatronymic(author.getPatronymic());

        log.info("Update Author {}", author.getId());
        return repository.save(currentAuthor);
    }
}
