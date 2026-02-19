package org.ratmir.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AuthorShortDTO {
    private UUID id;
    private String name;
    private String surname;
}
