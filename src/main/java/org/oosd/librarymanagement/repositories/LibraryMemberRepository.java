package org.oosd.librarymanagement.repositories;
//********************************************************************************************************************
// this is the LibraryMemberRepository interface that extends the JpaRepository interface
// it has a type parameter of LibraryMember and a type parameter of Long
// the LibraryMemberRepository interface is used to interact with the library_members table in the database
//********************************************************************************************************************


import org.oosd.librarymanagement.models.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {
}
