package org.oosd.librarymanagement.repositories;
// ********************************************************************************************************************
// this is the BorrowRecordRepository interface that extends the JpaRepository interface
// it has a type parameter of BorrowRecord and a type parameter of Long
// the BorrowRecordRepository interface is used to interact with the borrow_records table in the database
// it has a method to find a BorrowRecord by book id and return date is null
// ********************************************************************************************************************

import org.oosd.librarymanagement.models.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    Optional<BorrowRecord> findByBookIdAndReturnDateIsNull(Long bookId);
}
