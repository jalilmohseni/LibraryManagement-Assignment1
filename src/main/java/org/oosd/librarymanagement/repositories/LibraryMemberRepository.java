package org.oosd.librarymanagement.repositories;


import org.oosd.librarymanagement.models.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {
}
