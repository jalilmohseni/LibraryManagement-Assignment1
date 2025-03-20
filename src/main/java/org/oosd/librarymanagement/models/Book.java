package org.oosd.librarymanagement.models;
//********************************************************************************************************************
// this is the Book class that represents a book in the library
// it has a title, ISBN, publication year, a list of authors, and a list of borrow records
// the list of authors is a many-to-many relationship with the Author class
// the list of borrow records is a one-to-many relationship with the BorrowRecord class
// the Book class is an entity that is mapped to the books table in the database
//********************************************************************************************************************

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title cannot be null")
    @Column(unique = true)
    private String title;

    @NotNull(message = "ISBN cannot be null")
    @Pattern(regexp = "\\d{3}-\\d{10}", message = "ISBN must be in format 978-XXXXXXXXXX")
    @Column(unique = true)
    private String isbn;

    @NotNull(message = "Publication year cannot be null")
    private int publicationYear;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BorrowRecord> borrowRecords;
}
