package org.ratmir.project.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Author {
    private UUID id;
    private String name;
    private String surname;
    private String lastName;
    private String email;
    private String phone;
    private String bio;
}
