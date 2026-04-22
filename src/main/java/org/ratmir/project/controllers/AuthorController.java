package org.ratmir.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.author.AuthorDetailDTO;
import org.ratmir.project.dto.author.CreateAuthorDTO;
import org.ratmir.project.dto.author.UpdateAuthorDTO;
import org.ratmir.project.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService service;

    @GetMapping
    public ResponseEntity<List<AuthorDetailDTO>> getAllAuthors() {
        List<AuthorDetailDTO> authors = service.getAll();

        if (authors.isEmpty()) {
            log.info("No authors found");
            return ResponseEntity.noContent().build();
        }

        log.info("GET all authors");
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDetailDTO> getAuthorById(@PathVariable UUID id) {
        log.info("GET author with id {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorDetailDTO> createAuthor(@Valid @RequestBody CreateAuthorDTO createAuthorDTO) {
        log.info("CREATE author {}", createAuthorDTO.getName());
        AuthorDetailDTO dto = service.createAuthor(createAuthorDTO);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDetailDTO> updateAuthor(@Valid @RequestBody UpdateAuthorDTO updateAuthorDTO, @PathVariable UUID id) {
        log.info("UPDATE author {}", updateAuthorDTO.getName());
        AuthorDetailDTO dto = service.updateAuthor(id, updateAuthorDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable UUID id) {
        log.info("DELETE author with id {}", id);
        service.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
