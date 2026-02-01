package org.ratmir.project.models;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.ratmir.project.enums.Role;

@Data
@Slf4j
public class User {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private Role role;
}
