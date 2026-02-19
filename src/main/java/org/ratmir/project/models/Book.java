package org.ratmir.project.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.enums.ModerationStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Data
@Slf4j
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Book info
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;


    private double rating;
    private String photo;

    @ManyToMany
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    // Publish info
    @Column(name = "publishing_date")
    private LocalDate publishingDate;

    // Inventory info
    private String inventoryNumber;

    // Moderation info
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModerationStatus status;

    // Foreign info
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_user_id")
    private User origin;

    public Book(UUID id, String isbn, String title, String description, double rating, String photo, List<Genre> genres, List<Author> authors, LocalDate publishingDate, String inventoryNumber, ModerationStatus status, User origin) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.photo = photo;
        this.genres = genres;
        this.authors = authors;
        this.publishingDate = publishingDate;
        this.inventoryNumber = inventoryNumber;
        this.status = status;
        this.origin = origin;
    }

    public Book(UUID id, String isbn, String title, String description, double rating, String photo, List<Genre> genres, List<Author> authors, LocalDate publishingDate, String inventoryNumber, User origin) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.photo = photo;
        this.genres = genres;
        this.authors = authors;
        this.publishingDate = publishingDate;
        this.inventoryNumber = inventoryNumber;
        this.status = ModerationStatus.PENDING;
        this.origin = origin;
    }

    public Book(String isbn, String title, String description, double rating, String photo, List<Genre> genres, List<Author> authors, LocalDate publishingDate, String inventoryNumber, User origin) {
        this.id = UUID.randomUUID();
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.photo = photo;
        this.genres = genres;
        this.authors = authors;
        this.publishingDate = publishingDate;
        this.inventoryNumber = inventoryNumber;
        this.status = ModerationStatus.PENDING;
        this.origin = origin;
    }
}
