package com.fx.springfx.repositories;

import com.fx.springfx.entities.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    ArrayList<Book> findAll();

    @Query(value = "SELECT EXISTS(SELECT * FROM books WHERE id = :id)", nativeQuery = true)
    boolean isExistId(Long id);
}
