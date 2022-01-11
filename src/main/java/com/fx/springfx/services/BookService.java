package com.fx.springfx.services;

import com.fx.springfx.entities.Book;
import com.fx.springfx.repositories.BookRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void showAllBook(TableColumn<Book, Long> id,
                            TableColumn<Book, String> title,
                            TableColumn<Book, String> author,
                            TableColumn<Book, Integer> year,
                            TableColumn<Book, Integer> pages,
                            TableView<Book> tvBooks) {
        ObservableList<Book> list = FXCollections.observableArrayList(bookRepository.findAll());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        pages.setCellValueFactory(new PropertyValueFactory<>("pages"));
        tvBooks.setItems(list);
    }

    public void create(String id, String title, String author, String year, String pages) throws Exception {
        checkFieldValid(id, title, author, year, pages);
        try {
            Long longId = Long.parseLong(id);
            int integerYear = Integer.parseInt(year);
            int integerPages = Integer.parseInt(pages);
            Book book = new Book(longId, title, author, integerYear, integerPages);
            bookRepository.save(book);
        } catch (Exception exception) {
            System.out.println("Something went wrong during the save method");
        }

    }

    private void checkFieldValid(String id, String title, String author, String year, String pages) throws Exception {
        String exceptionMessage = "";
        if (id.isEmpty()) {
            exceptionMessage += " id ";
        }
        if (author.isEmpty()) {
            exceptionMessage += " author ";
        }
        if (title.isEmpty()) {
            exceptionMessage += " title ";
        }
        if (year.isEmpty()) {
            exceptionMessage += " year ";
        }
        if (pages.isEmpty()) {
            exceptionMessage += " pages ";
        }
        if (!exceptionMessage.isEmpty()) {
            throw new Exception(exceptionMessage);
        }
    }
}
