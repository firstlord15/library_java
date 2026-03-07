package org.ratmir.project.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateBookDTO {
    @NotBlank
    @Size(min = 1, max = 1500)
    private String title;

    @Size(min = 1, max = 5000)
    private String description;

    private String photoUrl;

    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate publishDate;

    private List<UUID> authorIds;
    private List<UUID> genreIds;

    @Pattern(regexp = "^(?:\\d{9}[\\dX]|\\d{13})$")
    private String isbn;
}
