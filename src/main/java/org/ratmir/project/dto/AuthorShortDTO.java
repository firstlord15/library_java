package org.ratmir.project.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthorShortDTO {
    private String name;
    private String surname;
    private String lastname;

    public AuthorShortDTO(String name, String surname, String lastname) {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
    }
}
