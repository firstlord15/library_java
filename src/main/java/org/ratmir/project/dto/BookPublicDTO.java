package org.ratmir.project.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ratmir.project.models.Author;
import org.ratmir.project.models.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class BookPublicDTO {
    private UUID id;

    // Book info
    private String title;
    private String description;
    private double rating;
    private String photoUrl;
    private List<Genre> genres;
    private List<Author> authors;

    // Publish info
    private LocalDate publishingDate;
}
