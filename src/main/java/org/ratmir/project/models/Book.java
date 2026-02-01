package org.ratmir.project.models;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.enums.ModerationStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Slf4j
public class Book {
    // ID
    private UUID id;

    // Book info
    private String ISBN;
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

    // Foreign info
    private User origin;

    public Book(UUID id, String ISBN, String title, String description, double rating, String photo, List<Genre> genres, List<Author> authors, Date publishingYear, String inventoryNumber, ModerationStatus status, User origin) {
        this.id = id;
        this.ISBN = ISBN;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.photo = photo;
        this.genres = genres;
        this.authors = authors;
        this.publishingYear = publishingYear;
        this.inventoryNumber = inventoryNumber;
        this.status = status;
        this.origin = origin;
    }

    public Book(UUID id, String ISBN, String title, String description, double rating, String photo, List<Genre> genres, List<Author> authors, Date publishingYear, String inventoryNumber, User origin) {
        this.id = id;
        this.ISBN = ISBN;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.photo = photo;
        this.genres = genres;
        this.authors = authors;
        this.publishingYear = publishingYear;
        this.inventoryNumber = inventoryNumber;
        this.status = ModerationStatus.PENDING;
        this.origin = origin;
    }

    public Book(String ISBN, String title, String description, double rating, String photo, List<Genre> genres, List<Author> authors, Date publishingYear, String inventoryNumber, User origin) {
        this.id = UUID.randomUUID();
        this.ISBN = ISBN;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.photo = photo;
        this.genres = genres;
        this.authors = authors;
        this.publishingYear = publishingYear;
        this.inventoryNumber = inventoryNumber;
        this.status = ModerationStatus.PENDING;
        this.origin = origin;
    }
}
