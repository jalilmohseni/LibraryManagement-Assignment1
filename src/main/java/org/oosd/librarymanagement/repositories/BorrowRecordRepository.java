package org.oosd.librarymanagement.repositories;

import org.oosd.librarymanagement.models.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    Optional<BorrowRecord> findByBookIdAndReturnDateIsNull(Long bookId);
}
