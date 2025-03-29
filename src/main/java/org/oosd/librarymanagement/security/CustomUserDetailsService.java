package org.oosd.librarymanagement.security;
/**
 * This class is a custom implementation of the UserDetailsService interface.
 * It is used to load user-specific data during authentication.
 * It retrieves user details from the UserRepository based on the username.
 */


import org.oosd.librarymanagement.models.User;
import org.oosd.librarymanagement.repositories.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    /**
         * Loads the user details based on the provided username.
         * This method is used during the authentication process to retrieve user-specific data.
         *
         * @param username the username identifying the user whose data is required.
         * @return a fully populated UserDetails object.
         * @throws UsernameNotFoundException if the user could not be found.
         */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
