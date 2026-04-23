package org.ratmir.project.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDTO {
    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 20, message = "Phone number is too long")
    private String phone;
}
