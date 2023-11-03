package pl.edu.pw.mwo1.views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.edu.pw.mwo1.models.PublisherDto;
import pl.edu.pw.mwo1.viewmodels.PublisherViewModel;

public class PublisherView {
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<PublisherDto> table;
    @FXML
    private TableColumn<PublisherDto, Integer> idCol;
    @FXML
    private TableColumn<PublisherDto, String> nameCol;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    private final PublisherViewModel viewModel;

    public PublisherView() {
        this.viewModel = new PublisherViewModel();
    }

    public void initialize() {
        pagination.pageCountProperty().bind(viewModel.getPageQuantProperty());
        pagination.currentPageIndexProperty().addListener(observable -> {
            Thread t = new Thread(() -> Platform.runLater(() -> viewModel.changePage(pagination.getCurrentPageIndex())));

            t.start();
        });
        table.itemsProperty().bind(viewModel.getPublishersOnPage());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    public void getAction() {
        Thread t = new Thread(() -> Platform.runLater(viewModel::get));

        pagination.currentPageIndexProperty().setValue(0);
        t.start();
    }

    @FXML
    public void createAction() {
        Thread t = new Thread(() -> Platform.runLater(() -> viewModel.create(nameField.getText())));

        pagination.currentPageIndexProperty().setValue(0);
        t.start();
    }

    @FXML
    public void updateAction() {
        Thread t = new Thread(() -> Platform.runLater(() -> viewModel.update(idField.getText(), nameField.getText())));

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
