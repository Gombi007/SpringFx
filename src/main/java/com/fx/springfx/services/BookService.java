package com.fx.springfx.services;

import com.fx.springfx.entities.Book;
import com.fx.springfx.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ArrayList<Book> allBook() {
        return bookRepository.findAll();
    }
}
