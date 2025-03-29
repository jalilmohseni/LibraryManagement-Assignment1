package org.oosd.librarymanagement.security;

/**
 * This class represents a response containing a JWT token.
 * It is used to send the generated JWT token back to the client after a successful login.
 */


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
