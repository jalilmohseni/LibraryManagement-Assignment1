package org.oosd.librarymanagement.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MembershipCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Card number cannot be null")
    @Column(unique = true)
    private String cardNumber;

    @NotNull(message = "Issue date cannot be null")
    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @NotNull(message = "Expiry date cannot be null")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @OneToOne
    @JoinColumn(name = "library_member_id", unique = true)
    private LibraryMember libraryMember;
}
