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
                            TableColumn<Book, String> author,
                            TableColumn<Book, String> title,
                            TableColumn<Book, Integer> year,
                            TableColumn<Book, Integer> pages,
                            TableView<Book> tvBooks) {
        ObservableList<Book> list = FXCollections.observableArrayList(bookRepository.findAll());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        pages.setCellValueFactory(new PropertyValueFactory<>("pages"));
        tvBooks.setItems(list);
    }

    public void create(String id, String author, String title, String year, String pages) {
        Long longId = Long.parseLong(id);
        int integerYear = Integer.parseInt(year);
        int integerPages = Integer.parseInt(pages);
        Book book = new Book(longId, author, title, integerYear, integerPages);
        bookRepository.save(book);

    }
}
