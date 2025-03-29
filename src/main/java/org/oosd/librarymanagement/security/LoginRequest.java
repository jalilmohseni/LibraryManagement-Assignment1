package org.oosd.librarymanagement.security;
/**
 * This class represents a login request containing a username and password.
 * It is used to capture the login credentials provided by the user.
 */


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}
