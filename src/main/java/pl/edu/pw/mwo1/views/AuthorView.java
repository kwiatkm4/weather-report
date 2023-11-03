package pl.edu.pw.mwo1.views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.edu.pw.mwo1.models.AuthorDto;
import pl.edu.pw.mwo1.viewmodels.AuthorViewModel;

public class AuthorView {
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<AuthorDto> table;
    @FXML
    private TableColumn<AuthorDto, Integer> idCol;
    @FXML
    private TableColumn<AuthorDto, String> nameCol;
    @FXML
    private TableColumn<AuthorDto, String> surnameCol;
    @FXML
    private TableColumn<AuthorDto, String> mailCol;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField mailField;
    private final AuthorViewModel viewModel;

    public AuthorView() {
        this.viewModel = new AuthorViewModel();
    }

    public void initialize() {
        pagination.pageCountProperty().bind(viewModel.getPageQuantProperty());
        pagination.currentPageIndexProperty().addListener(observable -> {
            Thread t = new Thread(() -> Platform.runLater(() -> viewModel.changePage(pagination.getCurrentPageIndex())));

            t.start();
        });
        table.itemsProperty().bind(viewModel.getAuthorsOnPage());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        mailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    @FXML
    public void getAction() {
        Thread t = new Thread(() -> Platform.runLater(viewModel::get));

        pagination.currentPageIndexProperty().setValue(0);
        t.start();
    }

    @FXML
    public void createAction() {
        Thread t = new Thread(() -> Platform.runLater(() -> viewModel.create(nameField.getText(), surnameField.getText(), mailField.getText())));

        pagination.currentPageIndexProperty().setValue(0);
        t.start();
    }

    @FXML
    public void updateAction() {
        Thread t = new Thread(() -> Platform.runLater(() -> viewModel.update(idField.getText(), nameField.getText(), surnameField.getText(), mailField.getText())));

        pagination.currentPageIndexProperty().setValue(0);
        t.start();
    }

    @FXML
    public void deleteAction() {
        Thread t = new Thread(() -> Platform.runLater(() -> viewModel.delete(idField.getText())));

        pagination.currentPageIndexProperty().setValue(0);
        t.start();
    }
}
