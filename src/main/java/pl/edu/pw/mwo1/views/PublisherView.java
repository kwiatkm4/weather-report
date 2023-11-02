package pl.edu.pw.mwo1.views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.edu.pw.mwo1.models.PublisherDto;
import pl.edu.pw.mwo1.viewmodels.PublisherViewModel;

public class PublisherView {
    @FXML
    public Pagination pagination;
    @FXML
    public TableView<PublisherDto> table;
    @FXML
    public TableColumn<PublisherDto, Integer> idCol;
    @FXML
    public TableColumn<PublisherDto, String> nameCol;
    @FXML
    private TextField nameField;
    @FXML
    private TextField idField;
    private final PublisherViewModel viewModel;

    public PublisherView() {
        viewModel = new PublisherViewModel();
    }

    public void initialize() {
        pagination.pageCountProperty().bind(viewModel.getPageQuantProperty());
        pagination.currentPageIndexProperty().addListener(observable -> {
            Thread t = new Thread(() -> Platform.runLater(() -> viewModel.changePage(pagination.getCurrentPageIndex())));

            t.start();
        });
        table.itemsProperty().bind(viewModel.getPubsOnPage());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    public void getAction() {
        Thread t = new Thread(() -> Platform.runLater(() -> {
            pagination.currentPageIndexProperty().setValue(0);
            viewModel.get();
        }));

        t.start();
    }

    @FXML
    public void createAction() {
        Thread t = new Thread(() -> Platform.runLater(() -> viewModel.create(nameField.getText())));

        t.start();
    }

    @FXML
    public void updateAction() {
        Thread t = new Thread(() -> Platform.runLater(() -> viewModel.update(idField.getText(), nameField.getText())));

        t.start();
    }

    @FXML
    public void deleteAction() {
        Thread t = new Thread(() -> Platform.runLater(() -> viewModel.delete(idField.getText())));

        t.start();
    }
}
