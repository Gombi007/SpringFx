package com.fx.springfx.entities;


import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long isbn10;
    private String title;
    private String author;
    private int year;
    private int pages;


    public Book() {
    }

    public Book(Long id, Long isbn10, String title, String author, int year, int pages) {
        this.id = id;
        this.isbn10 = isbn10;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }

    public Book(Long isbn10, String title, String author, int year, int pages) {
        this.isbn10 = isbn10;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }

    public Long getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(Long isbn10) {
        this.isbn10 = isbn10;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
