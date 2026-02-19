package org.ratmir.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GenreDTO {
    private UUID id;
    private String name;
}
