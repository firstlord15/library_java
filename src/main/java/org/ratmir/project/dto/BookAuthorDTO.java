package org.ratmir.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ratmir.project.enums.ModerationStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class BookAuthorDTO {
    private UUID id;
    private String title;
    private String description;
    private ModerationStatus status;
    private double rating;
    private String photoUrl;
    private LocalDate publicationDate;
    private String inventoryNumber;
    private List<AuthorShortDTO> authors;
    private List<GenreDTO> genres;
    private UUID originId;
    private String originUsername;
}
