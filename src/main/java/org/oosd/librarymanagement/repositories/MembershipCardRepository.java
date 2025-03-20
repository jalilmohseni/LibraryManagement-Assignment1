package org.oosd.librarymanagement.repositories;
// ***********************************************************************************************************************
// this is the MembershipCardRepository interface that extends the JpaRepository interface
// it has a type parameter of MembershipCard and a type parameter of Long
// the MembershipCardRepository interface is used to interact with the membership_cards table in the database
// ***********************************************************************************************************************
import org.oosd.librarymanagement.models.MembershipCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipCardRepository extends JpaRepository<MembershipCard, Long> {
}
