package com.andrzej.bookDatabase.Model;

import lombok.*;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "books")
public class Book implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    @NotEmpty(message = "Not empty")
    private String title;
    @Column(name = "author")
    @NotEmpty(message = "Not empty")
    private String author;
    @Column(name = "isbn")
    @NotEmpty(message = "Not empty")
    private String isbn;
    @Column(name = "year")
    @NotEmpty(message = "Not empty")
    private String year;
    @Column(name = "pages")
    @NotEmpty(message = "Not empty")
    private String pages;
    @Column(name = "language")
    @NotEmpty(message = "Not empty")
    private String language;
    @Column(name = "category")
    @NotEmpty(message = "Not empty")
    private String category;
    @Column(name = "publisher")
    @NotEmpty(message = "Not empty")
    private String publisher;

}
