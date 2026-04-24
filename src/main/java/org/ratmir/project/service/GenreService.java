package org.ratmir.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.genre.CreateGenreDTO;
import org.ratmir.project.dto.genre.GenreDTO;
import org.ratmir.project.exception.IllegalArgumentException;
import org.ratmir.project.exception.ResourceNotFoundException;
import org.ratmir.project.mapper.GenreMapper;
import org.ratmir.project.models.Genre;
import org.ratmir.project.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreMapper mapper;
    private final GenreRepository repository;

    public List<GenreDTO> getAllGenres() {
        log.debug("Fetching all genres");
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public GenreDTO getGenreById(UUID id) {
        log.debug("Fetching genre by Id: {}", id);
        Genre genre = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + id));
        return mapper.toDTO(genre);
    }

    public GenreDTO getGenreByName(String name) {
        log.debug("Fetching genre by name: {}", name);
        Genre genre = repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with name: " + name));
        return mapper.toDTO(genre);
    }

    public void deleteGenreById(UUID id) {
        log.debug("Deleting genre: {}", id);
        repository.deleteById(id);
    }

    @Transactional
    public GenreDTO createGenre(CreateGenreDTO createGenreDTO) {
        if (repository.findByName(createGenreDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("Genre already exists with name: " + createGenreDTO.getName());
        }
        Genre genre = mapper.fromCreateDTO(createGenreDTO);

        log.debug("Genre saved with name: {}", createGenreDTO.getName());
        Genre saved = repository.save(genre);
        return mapper.toDTO(saved);
    }

    @Transactional
    public GenreDTO updateGenre(UUID id, CreateGenreDTO createGenreDTO) {
        Genre genre = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + id));

        log.debug("Genre updated with name: {}", genre.getName());
        mapper.updateFromDTO(createGenreDTO, genre);
        Genre updated = repository.save(genre);
        return mapper.toDTO(updated);
    }
}
