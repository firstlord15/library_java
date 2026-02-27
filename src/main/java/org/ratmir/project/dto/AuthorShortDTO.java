package org.ratmir.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ratmir.project.models.Author;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AuthorShortDTO {
    private UUID id;
    private String name;
    private String surname;

    public AuthorShortDTO(UUID id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}
