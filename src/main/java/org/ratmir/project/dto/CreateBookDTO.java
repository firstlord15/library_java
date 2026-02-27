package org.ratmir.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ratmir.project.models.User;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateBookDTO {
    private UUID id;
    private String isbn;
    private String title;
    private String description;
    private double rating;
    private String photoUrl;
    private LocalDate publishingDate;
    private String inventoryNumber;
    private User origin;
}
