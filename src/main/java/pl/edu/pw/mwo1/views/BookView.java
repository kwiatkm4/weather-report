package pl.edu.pw.mwo1.views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.edu.pw.mwo1.models.BookDto;
import pl.edu.pw.mwo1.viewmodels.BookViewModel;

import java.sql.Date;

public class BookView {
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<BookDto> table;
    @FXML
    private TableColumn<BookDto, Integer> idCol;
    @FXML
    private TableColumn<BookDto, Integer> pubCol;
    @FXML
    private TableColumn<BookDto, Integer> authCol;
    @FXML
    private TableColumn<BookDto, String> titleCol;
    @FXML
    private TableColumn<BookDto, Integer> countCol;
    @FXML
    private TableColumn<BookDto, Date> dateCol;
    @FXML
    private TableColumn<BookDto, String> isbnCol;
    @FXML
    private TextField idField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField pageField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField pubField;
    @FXML
    private TextField isbnField;
    private final BookViewModel viewModel;

    public BookView() {
        viewModel = new BookViewModel();
    }

    public void initialize() {
        pagination.pageCountProperty().bind(viewModel.getPageQuantProperty());
        pagination.currentPageIndexProperty().addListener(observable -> {
            Thread t = new Thread(() -> Platform.runLater(() -> viewModel.changePage(pagination.getCurrentPageIndex())));

            t.start();
        });
        table.itemsProperty().bind(viewModel.getPubsOnPage());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        pubCol.setCellValueFactory(new PropertyValueFactory<>("publisherId"));
        authCol.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
    }

    @FXML
    public void getAction() {
        Thread t = new Thread(() -> Platform.runLater(viewModel::get));

        pagination.currentPageIndexProperty().setValue(0);
        t.start();
    }

    @FXML
    public void updateAction() {
        var date = dateField.getValue();
        Thread t = new Thread(() -> Platform.runLater(() -> viewModel.update(
                idField.getText(),
                titleField.getText(),
                authorField.getText(),
                pubField.getText(),
                pageField.getText(),
                new Date(date.getYear(), date.getMonthValue(), date.getDayOfMonth()),
                isbnField.getText())));

        pagination.currentPageIndexProperty().setValue(0);
        t.start();
    }

    @FXML
    public void createAction() {
        var date = dateField.getValue();
        Thread t = new Thread(() -> Platform.runLater(() -> viewModel.create(
                titleField.getText(),
                authorField.getText(),
                pubField.getText(),
                pageField.getText(),
                new Date(date.getYear(), date.getMonthValue(), date.getDayOfMonth()),
                isbnField.getText()
        )));

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
