package com.fx.springfx.controllers;

import com.fx.springfx.entities.Book;
import com.fx.springfx.services.WeatherService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/Main.fxml")
public class FxController {

    private WeatherService weatherService;

    @Autowired
    public FxController(WeatherService weatherService) {
        this.weatherService = weatherService;
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
    private TableColumn<Book, Integer> tvId;

    @FXML
    private TableColumn<Book, Integer> tvPages;

    @FXML
    private TableColumn<Book, String> tvTitle;

    @FXML
    private TableColumn<Book, Integer> tvYear;


}

