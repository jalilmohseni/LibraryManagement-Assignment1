package org.oosd.librarymanagement.controllers;



import org.oosd.librarymanagement.models.BorrowRecord;
import org.oosd.librarymanagement.repositories.BorrowRecordRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrow-records")
public class BorrowRecordController {
    private final BorrowRecordRepository repository;

    public BorrowRecordController(BorrowRecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<BorrowRecord> getAllRecords() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowRecord> getRecordById(@PathVariable Long id) {
        Optional<BorrowRecord> record = repository.findById(id);
        return record.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BorrowRecord addRecord(@RequestBody BorrowRecord record) {
        return repository.save(record);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowRecord> updateRecord(@PathVariable Long id, @RequestBody BorrowRecord updatedRecord) {
        return repository.findById(id)
                .map(record -> {
                    record.setReturnDate(updatedRecord.getReturnDate());
                    return ResponseEntity.ok(repository.save(record));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

