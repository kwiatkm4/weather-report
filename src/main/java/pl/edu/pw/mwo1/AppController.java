package pl.edu.pw.mwo1;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.edu.pw.mwo1.models.City;

import java.util.List;

public class AppController {

    @FXML
    private Button weatherButton;
    @FXML
    private ChoiceBox<City> cityList;
    @FXML
    private Label cityCountLabel;
    @FXML
    private TextField citySearchbar;
    @FXML
    private Button searchButton;
    private WeatherService service;
    private final static String CITY_COUNT_TEMPLATE = "Found %d cities:";

    @FXML
    public void initialize() {
        service = new WeatherService();
    }

    @FXML
    public void onCitySearch() {
        var cityText = citySearchbar.getText();

        if (cityText == null || cityText.isEmpty()) {
            return;
        }

        Thread searcher = new Thread(() -> {
            Platform.runLater(() -> {
                searchButton.setDisable(true);
            });

            List<City> cities = service.getCities(cityText);

            createSearchResultUI(cities);

            Platform.runLater(() -> {
                searchButton.setDisable(false);
            });
        });

        searcher.start();
    }


    private synchronized void createSearchResultUI(List<City> cities) {
        int cityCount = cities != null ? cities.size() : 0;

        Platform.runLater(() -> {
            cityCountLabel.setText(String.format(CITY_COUNT_TEMPLATE, cityCount));
        });

        if (cities == null || cities.isEmpty()) {
            toggleWeatherUI(false);
            return;
        }

        toggleWeatherUI(true);

        Platform.runLater(() -> {
            cityList.setItems(FXCollections.observableList(cities));
            cityList.setValue(cities.get(0));
        });
    }

    private synchronized void toggleWeatherUI(boolean isShown) {
        Platform.runLater(() -> {
            weatherButton.setVisible(isShown);
            cityList.setVisible(isShown);
        });
    }

    @FXML
    public void onWeatherInfoSearch() {
        String chosenCityId = cityList.getValue().key;

        Thread reporter = new Thread(() -> {

        });

        reporter.start();
    }
}