package org.ratmir.project.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

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
    private List<GenreDTO> genres;
    private List<AuthorShortDTO> authors;

    // Publish info
    private LocalDate publishingDate;
}
