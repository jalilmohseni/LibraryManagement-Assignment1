package org.oosd.librarymanagement.controllers;

import jakarta.validation.Valid;
import org.oosd.librarymanagement.models.LibraryMember;
import org.oosd.librarymanagement.repositories.LibraryMemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/members")
public class LibraryMemberController {

    private final LibraryMemberRepository repository;

    public LibraryMemberController(LibraryMemberRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<?> getAllMembers() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN', 'MEMBER')")
    public ResponseEntity<?> getMemberById(@PathVariable Long id) {
        Optional<LibraryMember> member = repository.findById(id);
        return member.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(null));
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<?> addMember(@Valid @RequestBody LibraryMember member) {
        return ResponseEntity.ok(repository.save(member));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @Valid @RequestBody LibraryMember updatedMember) {
        return repository.findById(id)
                .map(member -> {
                    member.setName(updatedMember.getName());
                    member.setEmail(updatedMember.getEmail());
                    member.setMembershipDate(updatedMember.getMembershipDate());
                    return ResponseEntity.ok(repository.save(member));
                })
                .orElse(ResponseEntity.status(404).body(null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        Optional<LibraryMember> member = repository.findById(id);

        if (member.isPresent()) {
            if (!member.get().getBorrowedBooks().isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Member cannot be deleted because they have borrowed books.");
            }

            repository.delete(member.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(404).body("❌ Member not found");
    }

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
