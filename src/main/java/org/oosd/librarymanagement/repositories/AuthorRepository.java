package org.oosd.librarymanagement.repositories;

import org.oosd.librarymanagement.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
