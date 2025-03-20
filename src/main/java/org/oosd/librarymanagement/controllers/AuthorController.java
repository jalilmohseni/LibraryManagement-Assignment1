package org.oosd.librarymanagement.controllers;

import org.oosd.librarymanagement.models.Author;
import org.oosd.librarymanagement.repositories.AuthorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorRepository repository;

    public AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<?> getAllAuthors() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = repository.findById(id);
        return author.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @PostMapping
    public ResponseEntity<?> addAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(repository.save(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor) {
        return repository.findById(id)
                .map(author -> {
                    author.setName(updatedAuthor.getName());
                    author.setBiography(updatedAuthor.getBiography());
                    return ResponseEntity.ok(repository.save(author));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        Optional<Author> author = repository.findById(id);

        if (author.isPresent()) {
            if (!author.get().getBooks().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Author cannot be deleted because they have books.");
            }

            repository.delete(author.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(404).body("Error: Author not found.");
    }
}
