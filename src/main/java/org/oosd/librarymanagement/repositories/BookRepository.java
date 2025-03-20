package org.oosd.librarymanagement.repositories;
//********************************************************************************************************************
// this is the BookRepository interface that extends the JpaRepository interface
// it has a type parameter of Book and a type parameter of Long
// the BookRepository interface is used to interact with the books table in the database
//********************************************************************************************************************

import org.oosd.librarymanagement.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
