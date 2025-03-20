package org.oosd.librarymanagement.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LibraryMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Membership date cannot be null")
    @Temporal(TemporalType.DATE)
    private Date membershipDate;

    @OneToOne(mappedBy = "libraryMember", cascade = CascadeType.ALL)
    private MembershipCard membershipCard;

    @OneToMany(mappedBy = "libraryMember", cascade = CascadeType.ALL)
    private List<BorrowRecord> borrowedBooks;
}
