package org.ratmir.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.genre.CreateGenreDTO;
import org.ratmir.project.dto.genre.GenreDTO;
import org.ratmir.project.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService service;

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        log.info("GET /api/genres");
        List<GenreDTO> genres = service.getAllGenres();
        if (genres.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenre(@PathVariable UUID id) {
        log.info("GET /api/genres/{}", id);
        return ResponseEntity.ok(service.getGenreById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<GenreDTO> getGenreByName(@RequestParam(required = false) String name) {
        log.info("GET api/genres/search?name={}", name);
        return ResponseEntity.ok(service.getGenreByName(name));
    }

    @PostMapping
    public ResponseEntity<GenreDTO> createGenre(@RequestBody @Valid CreateGenreDTO createGenreDTO) {
        log.info("POST /api/genres - name: {}", createGenreDTO.getName());
        GenreDTO dto = service.createGenre(createGenreDTO);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO>  updateGenre(@RequestBody @Valid CreateGenreDTO updateGenreDTO, @PathVariable UUID id) {
        log.info("PUT /api/genres/{}", id);
        GenreDTO dto = service.updateGenre(id, updateGenreDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable UUID id) {
        log.info("DELETE /api/genres/{}", id);
        service.deleteGenreById(id);
        return ResponseEntity.noContent().build();
    }
}
