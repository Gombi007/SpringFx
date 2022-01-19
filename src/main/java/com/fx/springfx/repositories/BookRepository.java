package com.fx.springfx.repositories;

import com.fx.springfx.entities.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    ArrayList<Book> findAll();

    @Query(value = "SELECT EXISTS(SELECT * FROM books WHERE isbn10 = :isbn)", nativeQuery = true)
    boolean isIsbnExist(Long isbn);

    @Query(value = "SELECT * FROM books WHERE isbn10 = :isbn", nativeQuery = true)
    Optional<Book> findByIsbn10(Long isbn);

    @Query(value = "SELECT * FROM books WHERE LOWER(title) LIKE %:title%", nativeQuery = true)
    ArrayList<Optional<Book>> findByTitle(String title);

    @Query(value = "SELECT * FROM books WHERE LOWER(author) LIKE %:author%", nativeQuery = true)
    ArrayList<Optional<Book>> findByAuthor(String author);

}
