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

import java.util.ArrayList;
import java.util.Optional;
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
                            TableView<Book> tvBooks,
                            ArrayList<Book> gotBookList) {
        ObservableList<Book> list;
        if (gotBookList == null) {
            list = FXCollections.observableArrayList(bookRepository.findAll());
        } else {
            list = FXCollections.observableArrayList(gotBookList);
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbn10.setCellValueFactory(new PropertyValueFactory<>("isbn10"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        pages.setCellValueFactory(new PropertyValueFactory<>("pages"));
        tvBooks.setItems(list);
    }

    public void create(String id, String isbn10, String title, String author, String year, String pages, boolean userHasAcceptedTheChanges) throws Exception {
        Book book = DataValidation.checkFieldData(id, isbn10, title, author, year, pages);
        boolean isIsbn10Exist = bookRepository.isIsbnExist(book.getIsbn10());
        if (isIsbn10Exist) {
            throw new ResourceAlreadyExistException("ISBN-10 is already exist!\nIf you want to update this record, please click the UPDATE button.");
        }
        if (userHasAcceptedTheChanges) {
            bookRepository.save(book);
        }
    }

    public void update(String id, String isbn10, String title, String author, String year, String pages, boolean userHasAcceptedTheChanges) throws Exception {
        Book book = DataValidation.checkFieldData(id, isbn10, title, author, year, pages);
        boolean isIsbnExist = bookRepository.isIsbnExist(book.getIsbn10());
        if (!isIsbnExist) {
            throw new ResourceAlreadyExistException("ISBN-10 is not exist yet!\nIf you want to create a new record, please click the CREATE button.");
        }
        if (userHasAcceptedTheChanges)
            bookRepository.save(book);
    }


    public ArrayList<Book> showFoundBooks(String isbn10, String title, String author, String year, String pages) throws DataFormatException {
        ArrayList<Book> searchResult = new ArrayList<>();

        if (!isbn10.isEmpty()) {
            try {
                Long gotIsbn10;
                gotIsbn10 = Long.parseLong(isbn10);
                Book bookByIsbn10 = bookRepository.findByIsbn10(gotIsbn10)
                        .orElseThrow(() -> new ResourceIsNotExistsException("There is NO book with this ISBN-10"));
                if (isNotThisBookInTheListYet(searchResult, bookByIsbn10)) {
                    searchResult.add(bookByIsbn10);
                }
            } catch (NumberFormatException exception) {
                throw new DataFormatException("ISBN-10 must be a number");
            }
        }
        if (!title.isEmpty()) {
            ArrayList<Optional<Book>> booksByTitle = bookRepository.findByTitle(title.toLowerCase());
            if (!booksByTitle.isEmpty()) {
                for (Optional<Book> book : booksByTitle) {
                    if (isNotThisBookInTheListYet(searchResult, book.get())) {
                        searchResult.add(book.get());
                    }
                }
            } else {
                throw new ResourceIsNotExistsException("There is NO book with this Title");
            }
        }

        if (!author.isEmpty()) {
            ArrayList<Optional<Book>> booksByTitle = bookRepository.findByAuthor(author.toLowerCase());
            if (!booksByTitle.isEmpty()) {
                for (Optional<Book> book : booksByTitle) {
                    if (isNotThisBookInTheListYet(searchResult, book.get())) {
                        searchResult.add(book.get());
                    }
                }
            } else {
                throw new ResourceIsNotExistsException("There is NO book with this Author");
            }
        }
        return searchResult;
    }


    public void delete(String id, String iban10, boolean userHasAcceptedTheChanges) throws DataFormatException {
        if (userHasAcceptedTheChanges) {
            Book book = finByIdOrIban10(id, iban10);
            bookRepository.delete(book);
        }
    }


    private boolean isNotThisBookInTheListYet(ArrayList<Book> list, Book checkingBook) {
        if (!list.isEmpty() && checkingBook != null)
            for (Book book : list) {
                if (book.getId() == checkingBook.getId()) {
                    return false;
                }
            }
        return true;
    }

    private Book finByIdOrIban10(String id, String iban10) throws DataFormatException {
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
}
