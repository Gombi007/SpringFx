package com.fx.springfx.controllers;

import com.fx.springfx.entities.Book;
import com.fx.springfx.services.BookService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/Main.fxml")
public class FxController implements Initializable {

    private BookService bookService;

    @Autowired
    public FxController(BookService bookService) {
        this.bookService = bookService;
    }

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private AnchorPane main;

    @FXML
    private TextField tfAuthor;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfPages;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfYear;

    @FXML
    private TableView<Book> tvBooks;

    @FXML
    private TableColumn<Book, String> tvAuthor;

    @FXML
    private TableColumn<Book, Long> tvId;

    @FXML
    private TableColumn<Book, Integer> tvPages;

    @FXML
    private TableColumn<Book, String> tvTitle;

    @FXML
    private TableColumn<Book, Integer> tvYear;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
      bookService.showAllBook(tvId,tvAuthor,tvTitle,tvYear,tvPages,tvBooks);
    }
}

