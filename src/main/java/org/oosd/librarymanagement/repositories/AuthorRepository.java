package org.oosd.librarymanagement.repositories;
//********************************************************************************************************************
// this is the AuthorRepository interface that extends the JpaRepository interface
// it has a type parameter of Author and a type parameter of Long
// the AuthorRepository interface is used to interact with the authors table in the database
//********************************************************************************************************************

import org.oosd.librarymanagement.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
