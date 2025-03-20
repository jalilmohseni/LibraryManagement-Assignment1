package org.oosd.librarymanagement.controllers;

import jakarta.validation.Valid;
import org.oosd.librarymanagement.models.LibraryMember;
import org.oosd.librarymanagement.repositories.LibraryMemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class LibraryMemberController {
    private final LibraryMemberRepository repository;

    public LibraryMemberController(LibraryMemberRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<LibraryMember> getAllMembers() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryMember> getMemberById(@PathVariable Long id) {
        Optional<LibraryMember> member = repository.findById(id);
        return member.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addMember(@Valid @RequestBody LibraryMember member) {
        return ResponseEntity.ok(repository.save(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibraryMember> updateMember(@PathVariable Long id, @Valid @RequestBody LibraryMember updatedMember) {
        return repository.findById(id)
                .map(member -> {
                    member.setName(updatedMember.getName());
                    member.setEmail(updatedMember.getEmail());
                    member.setMembershipDate(updatedMember.getMembershipDate());
                    return ResponseEntity.ok(repository.save(member));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        Optional<LibraryMember> member = repository.findById(id);

        if (member.isPresent()) {
            if (!member.get().getBorrowedBooks().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Member cannot be deleted because they have borrowed books.");
            }

            repository.delete(member.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(404).body("Error: Member not found.");
    }


    // Global exception handler for validation errors
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
