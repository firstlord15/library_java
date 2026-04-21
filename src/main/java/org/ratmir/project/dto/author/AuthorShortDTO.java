package org.ratmir.project.dto.author;

import lombok.Data;
import lombok.NoArgsConstructor;

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
