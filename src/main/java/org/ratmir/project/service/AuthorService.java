package org.ratmir.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.dto.author.AuthorDetailDTO;
import org.ratmir.project.dto.author.CreateAuthorDTO;
import org.ratmir.project.dto.author.UpdateAuthorDTO;
import org.ratmir.project.exception.ResourceNotFoundException;
import org.ratmir.project.mapper.AuthorMapper;
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
    private final AuthorMapper mapper;

    public AuthorDetailDTO getById(UUID id) {
        log.info("GET Author with id {}", id);
        Author author = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return mapper.toDTO(author);
    }

    public List<AuthorDetailDTO> getAll() {
        log.info("GET All Authors");

        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public void deleteAuthor(UUID id) {
        log.info("Delete Author {}", id);
        repository.deleteById(id);
    }

    @Transactional
    public AuthorDetailDTO createAuthor(CreateAuthorDTO dto) {
        log.info("Create Author {}", dto.getName());
        Author author = mapper.fromCreatedDTO(dto);
        Author saved = repository.save(author);
        return mapper.toDTO(saved);
    }

    @Transactional
    public AuthorDetailDTO updateAuthor(UUID id, UpdateAuthorDTO dto) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        log.info("Update Author {}", author.getName());
        mapper.updateFromDTO(dto, author);
        Author updated = repository.save(author);
        return mapper.toDTO(updated);
    }
}
