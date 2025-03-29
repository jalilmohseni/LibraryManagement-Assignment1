package org.oosd.librarymanagement.controllers;

/**
 * This controller handles CRUD operations for books.
 * It allows anyone to view books, while only admins and librarians can add, update, or delete books.
 */

import org.oosd.librarymanagement.models.Book;
import org.oosd.librarymanagement.repositories.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    // 🔓 Anyone can view available books
    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        Optional<Book> book = repository.findById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // 🛠 Only Admin and Librarian can add
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(repository.save(book));
    }

    // 🛠 Only Admin and Librarian can update
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return repository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setIsbn(updatedBook.getIsbn());
                    book.setPublicationYear(updatedBook.getPublicationYear());
                    return ResponseEntity.ok(repository.save(book));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // ❌ Only Admin and Librarian can delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Optional<Book> book = repository.findById(id);

        if (book.isPresent()) {
            if (!book.get().getBorrowRecords().isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Book cannot be deleted because it has active borrow records.");
            }

            repository.delete(book.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(404).body("❌ Book not found");
    }
}
