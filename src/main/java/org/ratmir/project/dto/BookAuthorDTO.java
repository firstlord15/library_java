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
    private List<GenreDTO> genres;
    private List<AuthorShortDTO> authors;
    private UUID originId;
    private String originUsername;

    public BookAuthorDTO(UUID id, String title, String description, ModerationStatus status, double rating, String photoUrl, LocalDate publicationDate, String inventoryNumber, List<GenreDTO> genres, List<AuthorShortDTO> authors, UUID originId, String originUsername) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.rating = rating;
        this.photoUrl = photoUrl;
        this.publicationDate = publicationDate;
        this.inventoryNumber = inventoryNumber;
        this.genres = genres;
        this.authors = authors;
        this.originId = originId;
        this.originUsername = originUsername;
    }
}
