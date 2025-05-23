package org.oosd.librarymanagement.controllers;
//****************************************************************************************************************************
//This is the BorrowRecordController class for the Library Management System.
//This class is responsible for handling all the HTTP requests for the BorrowRecord entity.
//It is annotated with @RestController to enable Spring component scanning and
//allow Spring to automatically detect this class as a controller.
//It is annotated with @RequestMapping to map HTTP requests to handler methods of this controller.
//It has a constructor that takes in a BorrowRecordRepository object.
//The getAllBorrowRecords method is mapped to the /api/borrowRecords endpoint and returns all borrow records in the database.
//The getBorrowRecordById method is mapped to the /api/borrowRecords/{id} endpoint and returns the borrow record with the specified ID.
//The addBorrowRecord method is mapped to the /api/borrowRecords endpoint and adds a new borrow record to the database.
//The updateBorrowRecord method is mapped to the /api/borrowRecords/{id} endpoint and updates the borrow record with the specified ID.
//The deleteBorrowRecord method is mapped to the /api/borrowRecords/{id} endpoint and deletes the borrow record with the specified ID.
//****************************************************************************************************************************
import jakarta.validation.Valid;
import org.oosd.librarymanagement.models.BorrowRecord;
import org.oosd.librarymanagement.repositories.BorrowRecordRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/borrowRecords")
public class BorrowRecordController {
    private final BorrowRecordRepository repository;

    public BorrowRecordController(BorrowRecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<?> getAllBorrowRecords() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBorrowRecordById(@PathVariable Long id) {
        Optional<BorrowRecord> record = repository.findById(id);
        return record.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @PostMapping
    public ResponseEntity<?> addBorrowRecord(@Valid @RequestBody BorrowRecord borrowRecord) {
        // Ensure returnDate is after borrowDate
        if (borrowRecord.getReturnDate() != null && borrowRecord.getReturnDate().before(borrowRecord.getBorrowDate())) {
            return ResponseEntity.badRequest().body("Error: Return date must be after borrow date.");
        }

        // Check if the book is already borrowed (not returned yet)
        Optional<BorrowRecord> existingBorrow = repository.findByBookIdAndReturnDateIsNull(borrowRecord.getBook().getId());
        if (existingBorrow.isPresent()) {
            return ResponseEntity.badRequest().body("Error: This book is already borrowed and has not been returned.");
        }

        return ResponseEntity.ok(repository.save(borrowRecord));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateBorrowRecord(@PathVariable Long id, @Valid @RequestBody BorrowRecord updatedRecord) {
        return repository.findById(id)
                .map(record -> {
                    if (updatedRecord.getReturnDate() != null && updatedRecord.getReturnDate().before(updatedRecord.getBorrowDate())) {
                        return ResponseEntity.badRequest().body("Error: Return date must be after borrow date.");
                    }

                    record.setBorrowDate(updatedRecord.getBorrowDate());
                    record.setReturnDate(updatedRecord.getReturnDate());
                    return ResponseEntity.ok(repository.save(record));
                })
                .orElse(ResponseEntity.status(404).body("Error: Borrow record not found."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBorrowRecord(@PathVariable Long id) {
        Optional<BorrowRecord> record = repository.findById(id);

        if (record.isPresent()) {
            repository.delete(record.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(404).body("Error: Borrow record not found.");
    }

    // Validation error handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
