package org.oosd.librarymanagement.controllers;

import org.oosd.librarymanagement.models.Author;
import org.oosd.librarymanagement.repositories.AuthorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorRepository repository;

    public AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }

    // 🔓 Anyone (authenticated or not) can view all authors
    @GetMapping
    public ResponseEntity<?> getAllAuthors() {
        return ResponseEntity.ok(repository.findAll());
    }

    // 🔓 Anyone can view author by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = repository.findById(id);
        return author.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // 🛠 Only ADMIN and LIBRARIAN can add authors
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<?> addAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(repository.save(author));
    }

    // 🛠 Only ADMIN and LIBRARIAN can update authors
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor) {
        return repository.findById(id)
                .map(author -> {
                    author.setName(updatedAuthor.getName());
                    author.setBiography(updatedAuthor.getBiography());
                    return ResponseEntity.ok(repository.save(author));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // ❌ Only ADMIN can delete authors
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        Optional<Author> author = repository.findById(id);

        if (author.isPresent()) {
            if (!author.get().getBooks().isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Author cannot be deleted because they have books.");
            }

            repository.delete(author.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(404).body("❌ Author not found");
    }
}
