package org.oosd.librarymanagement.repositories;

import org.oosd.librarymanagement.models.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
}
