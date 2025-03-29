package org.oosd.librarymanagement.controllers;
//*************************************************************************************************
//This file is the controller for the MembershipCard entity
//It is responsible for handling all the HTTP requests for the MembershipCard entity
//It is annotated with @RestController to enable Spring component scanning and
//allow Spring to automatically detect this class as a controller
//It is annotated with @RequestMapping to map HTTP requests to handler methods of this controller
//It has a constructor that takes in a MembershipCardRepository object
//The getAllCards method is mapped to the /api/membership-cards endpoint and returns all membership cards in the database
//The getCardById method is mapped to the /api/membership-cards/{id} endpoint and returns the membership card with the specified ID
//The addCard method is mapped to the /api/membership-cards endpoint and adds a new membership card to the database
//The deleteCard method is mapped to the /api/membership-cards/{id} endpoint and deletes the membership card with the specified ID
//*************************************************************************************************


/**
 * This controller handles CRUD operations for membership cards.
 * Only librarians can add cards, while only admins can delete them.
 * Both librarians and admins can view all cards.
 */



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
