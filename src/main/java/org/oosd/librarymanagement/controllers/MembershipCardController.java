package org.oosd.librarymanagement.controllers;

/**
 * This controller handles CRUD operations for membership cards.
 * Only librarians can add cards, while only admins can delete them.
 * Both librarians and admins can view all cards.
 */



import org.oosd.librarymanagement.models.MembershipCard;
import org.oosd.librarymanagement.repositories.MembershipCardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/membership-cards")
public class MembershipCardController {

    private final MembershipCardRepository repository;

    public MembershipCardController(MembershipCardRepository repository) {
        this.repository = repository;
    }

    // 🔓 View all cards - only ADMIN & LIBRARIAN
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<?> getAllCards() {
        return ResponseEntity.ok(repository.findAll());
    }

    // 🔓 View specific card - only ADMIN & LIBRARIAN
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<?> getCardById(@PathVariable Long id) {
        Optional<MembershipCard> card = repository.findById(id);
        return card.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(null));
    }

    // 🛠 Add a new card - only LIBRARIAN
    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<?> addCard(@RequestBody MembershipCard card) {
        return ResponseEntity.ok(repository.save(card));
    }

    // ❌ Delete a card - only ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        Optional<MembershipCard> card = repository.findById(id);
        if (card.isPresent()) {
            repository.delete(card.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(404).body("❌ Card not found");
    }
}
