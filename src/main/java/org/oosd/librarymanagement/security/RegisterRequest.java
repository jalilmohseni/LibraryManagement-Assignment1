package org.oosd.librarymanagement.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Lombok;
import lombok.Setter;

@Getter
@Setter

public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String role;
}
