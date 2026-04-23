package org.ratmir.project.dto.genre;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GenreDTO {
    private UUID id;
    private String name;

    public GenreDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
