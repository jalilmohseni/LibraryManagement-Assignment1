package org.oosd.librarymanagement.controllers;



import org.oosd.librarymanagement.models.MembershipCard;
import org.oosd.librarymanagement.repositories.MembershipCardRepository;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public List<MembershipCard> getAllCards() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipCard> getCardById(@PathVariable Long id) {
        Optional<MembershipCard> card = repository.findById(id);
        return card.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MembershipCard addCard(@RequestBody MembershipCard card) {
        return repository.save(card);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        Optional<MembershipCard> card = repository.findById(id);
        if (card.isPresent()) {
            repository.delete(card.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
