package pl.edu.pw.mwo1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AppController {

    @FXML
    private TextField citySearchbar;
    @FXML
    private Button searchButton;
    private WeatherService service;

    @FXML
    public void initialize() {
        service = new WeatherService();
    }

    @FXML
    public void onCitySearch() {
        Thread searcher = new Thread(() -> {
            Platform.runLater(() -> {
                searchButton.setDisable(true);
            });

            var cityText = citySearchbar.getText();

            if (cityText == null || cityText.isEmpty()) {
                Platform.runLater(() -> {
                    searchButton.setDisable(false);
                });
                return;
            }

            Platform.runLater(() -> {
                searchButton.setDisable(false);
            });
        });

        searcher.start();
    }
}