package com.fx.springfx.services;

import com.fx.springfx.entities.Book;
import com.fx.springfx.exceptions.ResourceAlreadyExistException;
import com.fx.springfx.exceptions.ResourceIsNotExistsException;
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

    public void create(String id, String isbn10, String title, String author, String year, String pages, boolean userHasAcceptedTheChanges) throws Exception {
        Book book = checkFieldData(id, isbn10, title, author, year, pages);
        boolean isIsbn10Exist = bookRepository.isIsbnExist(book.getIsbn10());
        if (isIsbn10Exist) {
            throw new ResourceAlreadyExistException("ISBN-10 is already exist!\nIf you want to update this record, please click the UPDATE button.");
        }
        if (userHasAcceptedTheChanges) {
            bookRepository.save(book);
        }
    }

    public void update(String id, String isbn10, String title, String author, String year, String pages, boolean userHasAcceptedTheChanges) throws Exception {
        Book book = checkFieldData(id, isbn10, title, author, year, pages);
        boolean isIsbnExist = bookRepository.isIsbnExist(book.getIsbn10());
        if (!isIsbnExist) {
            throw new ResourceAlreadyExistException("ISBN-10 is not exist yet!\nIf you want to create a new record, please click the CREATE button.");
        }
        if (userHasAcceptedTheChanges)
            bookRepository.save(book);
    }

    public Book finByIdOrIban10(String id, String iban10) throws DataFormatException {
        try {
            if (!id.isEmpty()) {
                Long isExistId = Long.parseLong(id);
                boolean isPresentId = bookRepository.findById(isExistId).isPresent();
                if (isPresentId) {
                    Book book = bookRepository.findById(isExistId).get();
                    return book;
                }
            } else if (!iban10.isEmpty()) {
                Long isExistIban = Long.parseLong(iban10);
                boolean isPresentIban10 = bookRepository.findByIsbn10(isExistIban).isPresent();
                if (isPresentIban10) {
                    Book book = bookRepository.findByIsbn10(isExistIban).get();
                    return book;
                }
            }
        } catch (NumberFormatException exception) {
            throw new DataFormatException("Id must be a number");
        }
        throw new ResourceIsNotExistsException("There is NO book with this id or IBAN-10");
    }

    public void delete(String id, String iban10, boolean userHasAcceptedTheChanges) throws DataFormatException {
        if (userHasAcceptedTheChanges) {
            Book book = finByIdOrIban10(id, iban10);
            bookRepository.delete(book);
        }
    }


    //Validation functions
    private void checkEmptyFields(String isbn10, String title, String author, String year, String pages) throws Exception {
        String exceptionMessage = "";
        if (isbn10.isEmpty()) {
            exceptionMessage += " Missing ISBN-10 ;";
        }
        if (author.isEmpty()) {
            exceptionMessage += " Missing Author ;";
        }
        if (title.isEmpty()) {
            exceptionMessage += " Missing Title ;";
        }
        if (year.isEmpty()) {
            exceptionMessage += " Missing Year ;";
        }
        if (pages.isEmpty()) {
            exceptionMessage += " Missing Pages ;";
        }
        if (!exceptionMessage.isEmpty()) {
            throw new DataFormatException(exceptionMessage);
        }

    }

    public Book checkFieldData(String id, String isbn10, String title, String author, String year, String pages) throws Exception {

        checkEmptyFields(isbn10, title, author, year, pages);
        Long longId = 0L;
        Long longIsbn10 = null;
        Integer integerYear = null;
        Integer integerPages = null;
        String exceptionMessage = "";
        if (!id.isEmpty()) {
            try {
                longId = Long.parseLong(id);
            } catch (NumberFormatException exception) {
                exceptionMessage += "ID field contains numbers only.;";
            }
        }
        try {
            longIsbn10 = Long.parseLong(isbn10);
        } catch (NumberFormatException exception) {
            exceptionMessage += "ISBN-10 field contains numbers only.;";
        }
        try {
            integerYear = Integer.parseInt(year);
        } catch (NumberFormatException exception) {
            exceptionMessage += "Publish year field contains numbers only.;";
        }
        try {
            integerPages = Integer.parseInt(pages);
        } catch (NumberFormatException exception) {
            exceptionMessage += "Pages field contains numbers only.;";
        }
        if (!exceptionMessage.isEmpty()) {
            throw new DataFormatException(exceptionMessage);
        }
        if (longId > 0) {
            return new Book(longId, longIsbn10, title, author, integerYear, integerPages);
        }
        return new Book(longIsbn10, title, author, integerYear, integerPages);
    }
}
