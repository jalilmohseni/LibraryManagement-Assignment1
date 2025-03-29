package org.oosd.librarymanagement.controllers;
//************************************************************************************
//This is the BookController class for the Library Management System. This class
//is responsible for handling all the HTTP requests for the Book entity.
//It is annotated with @RestController to enable Spring component scanning and
//allow Spring to automatically detect this class as a controller.
//It is annotated with @RequestMapping to map HTTP requests to handler methods of this controller.
//It has a constructor that takes in a BookRepository object.
//The getAllBooks method is mapped to the /api/books endpoint and returns all books in the database.
//The getBookById method is mapped to the /api/books/{id} endpoint and returns the book with the specified ID.
//The addBook method is mapped to the /api/books endpoint and adds a new book to the database.
//The updateBook method is mapped to the /api/books/{id} endpoint and updates the book with the specified ID.
//The deleteBook method is mapped to the /api/books/{id} endpoint and deletes the book with the specified ID.
//**************************************************************************************

/**
 * This controller handles CRUD operations for books.
 * It allows anyone to view books, while only admins and librarians can add, update, or delete books.
 */

import org.oosd.librarymanagement.models.Book;
import org.oosd.librarymanagement.repositories.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

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

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(repository.save(book));
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Optional<Book> book = repository.findById(id);

        if (book.isPresent()) {
            if (!book.get().getBorrowRecords().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Book cannot be deleted because it has active borrow records.");
            }

            repository.delete(book.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(404).body("Error: Book not found.");
    }
}
