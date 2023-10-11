package pl.edu.pw.mwo1;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.edu.pw.mwo1.models.*;

import java.util.List;

public class AppController {

    @FXML
    private Label infoHeader;
    @FXML
    private Label weatherInfo;
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

    private synchronized void toggleInfoHeader(boolean isShown) {
        Platform.runLater(() -> {
            infoHeader.setVisible(isShown);
        });
    }

    @FXML
    public void onWeatherInfoSearch() {
        String chosenCityId = cityList.getValue().key;

        if (chosenCityId == null || chosenCityId.isEmpty()) {
            return;
        }

        Thread reporter = new Thread(() -> {
            var conditions = service.getCurrentConditions(chosenCityId);
            var alarm = service.getAlarms(chosenCityId);
            var forecast = service.getForecast(chosenCityId);
            var indices = service.getIndices(chosenCityId);

            toggleInfoHeader(true);

            var report = new StringBuilder();

            if (conditions != null) {
                for (CurrentConditions c : conditions) {
                    report.append(String.format("Current weather: %s, %g %s\n", c.weatherText, c.temperature.metric.value, c.temperature.metric.unit));
                }

                report.append("\n");
            }

            if (alarm != null) {
                report.append(String.format("%d alarms issued for the city.\n\n", alarm.size()));
            }

            if (forecast != null) {
                report.append(String.format("Forecast for %s: %s\n\n", forecast.headline.effectiveDate, forecast.headline.text));
            }

            if (indices != null) {
                int toDisplay = Math.min(indices.size(), 5);

                for (int i = 0; i < toDisplay; i++) {
                    Index curr = indices.get(i);
                    report.append(String.format("State for index %s on %s: %s\n", curr.name, curr.localDateTime, curr.category));
                }
            }

            Platform.runLater(()-> {
                weatherInfo.setText(report.toString());
            });
        });

        reporter.start();
    }
}