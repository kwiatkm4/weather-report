package pl.edu.pw.mwo1.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import pl.edu.pw.mwo1.viewmodels.PublisherViewModel;

public class PublisherView {
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
    private AnchorPane contentPanel;
    @FXML
    private Label statusLabel;
    private final PublisherViewModel viewModel;

    public PublisherView() {
        viewModel = new PublisherViewModel();
    }


    public void initialize() {
        contentPanel.getChildren().add
    }

    @FXML
    public void getAction(ActionEvent actionEvent) {
        viewModel.get();
    }

    @FXML
    public void createAction(ActionEvent actionEvent) {
        viewModel.update(nameField.getText());
    }

    @FXML
    public void updateAction(ActionEvent actionEvent) {
        viewModel.update(idField.getText(), nameField.getText());
    }

    @FXML
    public void deleteAction() {
        viewModel.delete(idField.getText());
    }
}
