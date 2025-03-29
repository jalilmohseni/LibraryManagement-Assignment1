package org.oosd.librarymanagement.controllers;

/**
 * This controller handles authentication-related operations such as login, registration, and logout.
 */



import org.oosd.librarymanagement.models.Role;
import org.oosd.librarymanagement.models.User;
import org.oosd.librarymanagement.repositories.UserRepository;
import org.oosd.librarymanagement.security.JwtUtil;
import org.oosd.librarymanagement.security.LoginRequest;
import org.oosd.librarymanagement.security.LoginResponse;
import org.oosd.librarymanagement.security.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ LOGIN
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        System.out.println("LOGIN ATTEMPT: " + request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return new LoginResponse(token);
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("❌ Username already taken");
        }

        // Create and save user
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());

        Role role = Role.valueOf(request.getRole() != null ? request.getRole().toUpperCase() : "LIBRARIAN");
        newUser.setRole(role);

        userRepository.save(newUser);
        return ResponseEntity.ok("✅ Registration successful");
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("✅ Logged out (client must delete the token)");
    }

}
