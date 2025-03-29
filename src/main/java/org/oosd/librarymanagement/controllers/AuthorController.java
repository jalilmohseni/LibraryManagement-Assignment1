package org.oosd.librarymanagement.controllers;

//********************************************************************************************************************
// This class is a controller that handles HTTP requests related to authors.
// It is annotated with @RestController to enable Spring component scanning and allow Spring to automatically detect this class as a controller.
// It is annotated with @RequestMapping to map HTTP requests to handler methods of this controller.
// It has a constructor that takes in an AuthorRepository object.
// The getAllAuthors method is mapped to the /api/authors endpoint and returns all authors in the database.
// The getAuthorById method is mapped to the /api/authors/{id} endpoint and returns the author with the specified ID.
// The addAuthor method is mapped to the /api/authors endpoint and adds a new author to the database.
// The updateAuthor method is mapped to the /api/authors/{id} endpoint and updates the author with the specified ID.
// The deleteAuthor method is mapped to the /api/authors/{id} endpoint and deletes the author with the specified ID.
// The AuthorController class is responsible for handling CRUD operations related to authors in the library management system.
//********************************************************************************************************************
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
