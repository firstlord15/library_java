package org.ratmir.project.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.ratmir.project.enums.Role;

@Data
public class CreateUserDTO {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
    private String username;

    @NotBlank(message = "Password is password")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    @Email(message = "Invalid email format")
    @Size(max = 255)
    private String email;

    @Size(max = 20, message = "Phone number is too long")
    private String phone;
}
