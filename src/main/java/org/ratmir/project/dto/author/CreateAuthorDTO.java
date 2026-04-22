package org.ratmir.project.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAuthorDTO {
    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(max = 100)
    private String surname;

    @Size(max = 100)
    private String patronymic;

    @Size(max = 5000)
    private String bio;
}
