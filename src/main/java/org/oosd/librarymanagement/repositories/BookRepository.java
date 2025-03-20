package org.oosd.librarymanagement.repositories;

import org.oosd.librarymanagement.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
