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
        log.info("GET /api/authors");
        List<AuthorDetailDTO> authors = service.getAll();
        if (authors.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDetailDTO> getAuthorById(@PathVariable UUID id) {
        log.info("GET /api/authors/{}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorDetailDTO> createAuthor(@Valid @RequestBody CreateAuthorDTO createAuthorDTO) {
        log.info("POST /api/authors - name: {}", createAuthorDTO.getName());
        AuthorDetailDTO dto = service.createAuthor(createAuthorDTO);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDetailDTO> updateAuthor(@Valid @RequestBody UpdateAuthorDTO updateAuthorDTO, @PathVariable UUID id) {
        log.info("PUT /api/authors - name: {}", updateAuthorDTO.getName());
        AuthorDetailDTO dto = service.updateAuthor(id, updateAuthorDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable UUID id) {
        log.info("DELETE /api/authors/{}", id);
        service.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
