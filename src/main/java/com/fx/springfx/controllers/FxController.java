package com.fx.springfx.controllers;

import com.fx.springfx.entities.Book;
import com.fx.springfx.exceptions.ResourceAlreadyExistException;
import com.fx.springfx.exceptions.ResourceIsNotExistsException;
import com.fx.springfx.services.BookService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;

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
    private TextField tfIsbn;

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
    private TableColumn<Book, String> tvIsbn;

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

    @FXML
    private Label lbErrorId;

    @FXML
    private Label lbErrorIsbn;

    @FXML
    private Label lbErrorTitle;

    @FXML
    private Label lbErrorAuthor;

    @FXML
    private Label lbErrorYear;

    @FXML
    private Label lbErrorPages;

    @FXML
    private Label lbErrorMessage;

    @FXML
    private DialogPane errorMessageBox;

    @FXML
    private void buttonAction(ActionEvent event) {
        removeAllErrorLabel();
        if (event.getSource() == btnCreate) {
            try {
                boolean userHasAcceptedTheChanges = informAlertBox("Do you want to add this book to database?");
                bookService.create(tfId.getText(), tfIsbn.getText(), tfTitle.getText(), tfAuthor.getText(), tfYear.getText(), tfPages.getText(), userHasAcceptedTheChanges);
                bookService.showAllBook(tvId, tvIsbn, tvTitle, tvAuthor, tvYear, tvPages, tvBooks);
            } catch (DataFormatException exception) {
                setFieldErrorLabels(exception);
            } catch (ResourceAlreadyExistException exception) {
                errorAlertBox(exception.getMessage());
            } catch (Exception exception) {
                lbErrorMessage.setText(exception.getMessage());
            }
        }

        if (event.getSource() == btnUpdate) {
            try {
                boolean userHasAcceptedTheChanges = informAlertBox("Do you accept these changes?");
                bookService.update(tfId.getText(), tfIsbn.getText(), tfTitle.getText(), tfAuthor.getText(), tfYear.getText(), tfPages.getText(), userHasAcceptedTheChanges);
                bookService.showAllBook(tvId, tvIsbn, tvTitle, tvAuthor, tvYear, tvPages, tvBooks);
            } catch (DataFormatException exception) {
                setFieldErrorLabels(exception);
            } catch (ResourceAlreadyExistException exception) {
                errorAlertBox(exception.getMessage());
            } catch (Exception exception) {
                lbErrorMessage.setText(exception.getMessage());
            }
        }
        if (event.getSource() == btnDelete) {
            try {
                boolean userHasAcceptedTheChanges = informAlertBox("Do you accept these changes?");
                bookService.delete(tfId.getText(), tfIsbn.getText(), userHasAcceptedTheChanges);
                bookService.showAllBook(tvId, tvIsbn, tvTitle, tvAuthor, tvYear, tvPages, tvBooks);
            } catch (DataFormatException exception) {
                setFieldErrorLabels(exception);
            } catch (ResourceIsNotExistsException exception) {
                errorAlertBox(exception.getMessage());
            } catch (Exception exception) {
                lbErrorMessage.setText(exception.getMessage());
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookService.showAllBook(tvId, tvIsbn, tvTitle, tvAuthor, tvYear, tvPages, tvBooks);
        handleSelectedTableRow();
    }

    private void setFieldErrorLabels(Exception exception) {
        String[] errorMessages = exception.getMessage().split(";");
        for (int i = 0; i < errorMessages.length; i++) {
            if (errorMessages[i].contains("id")) {
                lbErrorId.setText(errorMessages[i]);
            }
            if (errorMessages[i].toLowerCase().contains("isbn-10")) {
                lbErrorIsbn.setText(errorMessages[i]);
            }
            if (errorMessages[i].toLowerCase().contains("title")) {
                lbErrorTitle.setText(errorMessages[i]);
            }
            if (errorMessages[i].toLowerCase().contains("author")) {
                lbErrorAuthor.setText(errorMessages[i]);
            }
            if (errorMessages[i].toLowerCase().contains("year")) {
                lbErrorYear.setText(errorMessages[i]);
            }
            if (errorMessages[i].toLowerCase().contains("pages")) {
                lbErrorPages.setText(errorMessages[i]);
            }
        }
    }

    private void removeAllErrorLabel() {
        lbErrorId.setText("");
        lbErrorIsbn.setText("");
        lbErrorAuthor.setText("");
        lbErrorTitle.setText("");
        lbErrorPages.setText("");
        lbErrorYear.setText("");
        lbErrorMessage.setText("");
    }

    private boolean informAlertBox(String text) {
        ButtonType cancel = new ButtonType("Cancel");
        ButtonType ok = new ButtonType("OK");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().setAll(cancel, ok);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(text);

        Optional<ButtonType> selectedButton = alert.showAndWait();
        if (selectedButton.get() == ok) {
            return true;
        } else {
            return false;
        }
    }

    private void errorAlertBox(String text) {
        ButtonType ok = new ButtonType("OK");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getButtonTypes().setAll(ok);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.show();
    }


    public void handleSelectedTableRow() {
        tvBooks.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Book selectedBook = tvBooks.getSelectionModel().getSelectedItem();
                Integer year = selectedBook.getYear();
                Integer pages = selectedBook.getPages();
                tfId.setText(selectedBook.getId().toString());
                tfIsbn.setText(selectedBook.getIsbn10().toString());
                tfTitle.setText(selectedBook.getTitle());
                tfAuthor.setText(selectedBook.getAuthor());
                tfYear.setText(year.toString());
                tfPages.setText(pages.toString());
            }
        });
    }
}

