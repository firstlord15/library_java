package org.ratmir.project.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ratmir.project.enums.ModerationStatus;
import org.ratmir.project.models.Author;
import org.ratmir.project.models.Genre;
import org.ratmir.project.models.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class BookDto {
    // Book info
    private String title;
    private String description;
    private double rating;
    private String photo;
    private List<Genre> genres;
    private List<Author> authors;

    // Publish info
    private Date publishingYear;

    // Inventory info
    private String inventoryNumber;

    // Moderation info
    private ModerationStatus status;

    // User info
    private UUID originId;
    private String originUsername;
}
