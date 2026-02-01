package org.ratmir.project.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@Data
@RequiredArgsConstructor
public class Genre {
    private UUID id;
    private String name;
    private String description;
}
