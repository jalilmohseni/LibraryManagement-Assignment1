package org.oosd.librarymanagement.models;
//********************************************************************************************************************
// this is the Author class that represents an author of a book
// it has a name, biography, and a list of books
// the list of books is a many-to-many relationship with the Book class
// the Author class is an entity that is mapped to the authors table in the database
//********************************************************************************************************************
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Author name cannot be null")
    private String name;

    @Lob
    private String biography;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
