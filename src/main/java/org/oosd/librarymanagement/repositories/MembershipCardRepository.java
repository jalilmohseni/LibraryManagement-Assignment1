package org.oosd.librarymanagement.repositories;

import org.oosd.librarymanagement.models.MembershipCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipCardRepository extends JpaRepository<MembershipCard, Long> {
}
