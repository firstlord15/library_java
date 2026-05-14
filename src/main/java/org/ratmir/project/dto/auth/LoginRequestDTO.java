package org.ratmir.project.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Username is require")
    private String username;

    @NotBlank(message = "Password is require")
    private String password;
}
