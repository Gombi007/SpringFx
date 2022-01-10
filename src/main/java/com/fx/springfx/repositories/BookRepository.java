package com.fx.springfx.repositories;

import com.fx.springfx.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    ArrayList<Book> findAll();
}
