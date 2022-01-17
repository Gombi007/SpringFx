package com.fx.springfx.services;

import com.fx.springfx.entities.Book;
import com.fx.springfx.exceptions.ResourceAlreadyExistException;
import com.fx.springfx.repositories.BookRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.zip.DataFormatException;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void showAllBook(TableColumn<Book, Long> id,
                            TableColumn<Book, String> isbn10,
                            TableColumn<Book, String> title,
                            TableColumn<Book, String> author,
                            TableColumn<Book, Integer> year,
                            TableColumn<Book, Integer> pages,
                            TableView<Book> tvBooks) {
        ObservableList<Book> list = FXCollections.observableArrayList(bookRepository.findAll());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbn10.setCellValueFactory(new PropertyValueFactory<>("isbn10"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        pages.setCellValueFactory(new PropertyValueFactory<>("pages"));
        tvBooks.setItems(list);
    }

    public void create(String id, String isbn10, String title, String author, String year, String pages) throws Exception {
        Book book = checkFieldData(isbn10, title, author, year, pages);
        boolean isIsbn10Exist = bookRepository.isIsbnExist(book.getIsbn10());
        if (isIsbn10Exist) {
            throw new ResourceAlreadyExistException("ISBN-10 is already exist!\nIf you want to update this record, please click the UPDATE button.");
        }
        bookRepository.save(book);
    }

    public void update(String id, String isbn10, String title, String author, String year, String pages) throws Exception {
        Book book = checkFieldData(isbn10, title, author, year, pages);
        boolean isIdExist = bookRepository.isIsbnExist(book.getIsbn10());
        if (!isIdExist) {
            throw new ResourceAlreadyExistException("ISBN-10 is not exist yet!\nIf you want to create a new record, please click the CREATE button.");
        }
        bookRepository.save(book);
    }


    //Validation functions
    private void checkEmptyFields(String isbn10, String title, String author, String year, String pages) throws Exception {
        String exceptionMessage = "";
        if (isbn10.isEmpty()) {
            exceptionMessage += " isbn10 ";
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
            throw new DataFormatException(exceptionMessage);
        }

    }

    public Book checkFieldData(String isbn10, String title, String author, String year, String pages) throws Exception {
        checkEmptyFields(isbn10, title, author, year, pages);
        long longIsbn10;
        int integerYear;
        int integerPages;
        try {
            longIsbn10 = Long.parseLong(isbn10);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("ISBN-10 field contains numbers only.");
        }
        try {
            integerYear = Integer.parseInt(year);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("Publish year field contains numbers only.");
        }
        try {
            integerPages = Integer.parseInt(pages);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("Pages field contains numbers only.");
        }
        return new Book(longIsbn10, title, author, integerYear, integerPages);
    }
}
