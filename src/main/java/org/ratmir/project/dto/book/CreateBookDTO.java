package org.ratmir.project.dto.book;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateBookDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 1500)
    private String title;

    @Size(min = 1, max = 5000)
    private String description;

    private String photoUrl;

    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate publishingDate;

    @NotEmpty(message = "At least one genre is required")
    private List<UUID> genreIds;

    @NotEmpty(message = "At least one author is required")
    private List<UUID> authorIds;

    @Pattern(regexp = "^(97[89])?[0-9]{9}[0-9X]$", message = "Invalid ISBN format (10 or 13 digits)")
    private String isbn;
}
