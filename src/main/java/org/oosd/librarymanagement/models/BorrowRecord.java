package org.oosd.librarymanagement.models;
//********************************************************************************************************************
// this is the BorrowRecord class that represents a record of a book being borrowed by a library member
// it has a borrow date, return date, library member, and book
// the BorrowRecord class is an entity that is mapped to the borrow_records table in the database
// it has a many-to-one relationship with the LibraryMember class and the Book class
//********************************************************************************************************************

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Borrow date cannot be null")
    @Temporal(TemporalType.DATE)
    @PastOrPresent(message = "Borrow date cannot be in the future")
    private Date borrowDate;

    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name = "library_member_id")
    private LibraryMember libraryMember;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
