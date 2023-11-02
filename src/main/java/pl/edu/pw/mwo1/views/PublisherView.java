package pl.edu.pw.mwo1.views;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private Button getButton;
    @FXML
    private Button createButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField idField;
    @FXML
    private Label statusLabel;
    private final PublisherViewModel viewModel;

    public PublisherView() {
        viewModel = new PublisherViewModel();
    }


    public void initialize() {
        pagination.pageCountProperty().bind(viewModel.getPageQuantProperty());
        pagination.currentPageIndexProperty().addListener(observable -> {
            viewModel.changePage(pagination.getCurrentPageIndex());
        });
        table.itemsProperty().bind(viewModel.getPubsOnPage());
    }

    @FXML
    public void getAction() {
        Thread t = new Thread(()-> {
            Platform.runLater(()-> {
                viewModel.get();
                pagination.currentPageIndexProperty().setValue(0);
            });
        });

        t.start();
    }

    @FXML
    public void createAction() {
        viewModel.create(nameField.getText());
    }

    @FXML
    public void updateAction() {
        viewModel.update(idField.getText(), nameField.getText());
    }

    @FXML
    public void deleteAction() {
        viewModel.delete(idField.getText());
    }
}
