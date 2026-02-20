package org.ratmir.project.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "authors")
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = true)
    private String patronymic;

    @Column(length = 5000)
    private String bio;

    public String getFullName() {
        return name + " " + surname + " " + patronymic;
    }
}
