package com.andrzej.bookDatabase.Model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity(name = "books")
public class Book {

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

    public Book() {
    }

    public Book(@NotEmpty(message = "Not empty") String title, @NotEmpty(message = "Not empty") String author, @NotEmpty(message = "Not empty") String isbn, @NotEmpty(message = "Not empty") String year, @NotEmpty(message = "Not empty") String pages, @NotEmpty(message = "Not empty") String language, @NotEmpty(message = "Not empty") String category, @NotEmpty(message = "Not empty") String publisher) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.pages = pages;
        this.language = language;
        this.category = category;
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
