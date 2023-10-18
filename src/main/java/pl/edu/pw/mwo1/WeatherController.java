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

public class WeatherController {

    @FXML
    private Label infoHeader;
    @FXML
    private Label conditionInfo;
    @FXML
    private Label forecastInfo;
    @FXML
    private Label alarmInfo;
    @FXML
    private Label indexInfo;
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
    private WeatherViewModel viewModel;

    @FXML
    public void initialize(WeatherViewModel viewModel) {
        service = new WeatherService();
        this.viewModel = viewModel;
    }

    @FXML
    public void onCitySearch() {
        var cityText = citySearchbar.getText();

        if (cityText == null || cityText.isEmpty()) {
            return;
        }

        Thread searcher = new Thread(() -> {
            Platform.runLater(() -> searchButton.setDisable(true));

            List<City> cities = service.getCities(cityText);

            createSearchResultUI(cities);

            Platform.runLater(() -> searchButton.setDisable(false));
        });

        searcher.start();
    }


    private synchronized void createSearchResultUI(List<City> cities) {
        int cityCount = cities != null ? cities.size() : 0;

        Platform.runLater(() -> cityCountLabel.setText(String.format("Found %d cities:", cityCount)));

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

    private synchronized void toggleButtons(boolean isDisabled) {
        Platform.runLater(() -> {
            weatherButton.setDisable(isDisabled);
            searchButton.setDisable(isDisabled);
        });
    }

    @FXML
    public void onWeatherInfoSearch() {
        String chosenCityId = cityList.getValue().key;

        if (chosenCityId == null || chosenCityId.isEmpty()) {
            return;
        }

        Thread reporter = new Thread(() -> {
            toggleButtons(true);
            clearWeatherInfo();

            Platform.runLater(() -> infoHeader.setText(String.format("Weather info for %s:", cityList.getValue().localizedName)));

            findConditions(chosenCityId);
            findAlarms(chosenCityId);
            findForecast(chosenCityId);
            findIndexes(chosenCityId);

            toggleButtons(false);
        });

        reporter.start();
    }

    @FXML
    private void clearWeatherInfo() {
        Platform.runLater(() -> {
            conditionInfo.setText("");
            indexInfo.setText("");
            forecastInfo.setText("");
            alarmInfo.setText("");
        });
    }

    @FXML
    private void findConditions(String key) {
        var conditions = service.getCurrentConditions(key);

        if (conditions != null) {
            var conditionReport = new StringBuilder();

            for (CurrentConditions c : conditions) {
                conditionReport.append(String.format("Current weather: %s, %g %s\n", c.weatherText, c.temperature.metric.value, c.temperature.metric.unit));
            }

            Platform.runLater(() -> conditionInfo.setText(conditionReport.toString()));
        } else {
            Platform.runLater(() -> conditionInfo.setText("Failed to get current conditions."));
        }
    }

    @FXML
    private void findAlarms(String key) {
        var alarms = service.getAlarms(key);
        var alarmText = alarms != null ? String.format("%d alarms issued for the city.", alarms.size()) :
                "Failed to get alarms.";

        Platform.runLater(() -> alarmInfo.setText(alarmText));
    }

    @FXML
    private void findForecast(String key) {
        var forecast = service.getForecast(key);
        var forecastText = forecast != null ?
                String.format("Forecast for %s: %s\n\n", forecast.headline.effectiveDate, forecast.headline.text) :
                "Failed to get forecast.";

        Platform.runLater(() -> forecastInfo.setText(forecastText));
    }

    @FXML
    private void findIndexes(String key) {
        var indices = service.getIndices(key);

        if (indices != null) {
            var indexText = new StringBuilder();
            int toDisplay = Math.min(indices.size(), 5);

            for (int i = 0; i < toDisplay; i++) {
                Index curr = indices.get(i);
                indexText.append(String.format("State for index %s on %s: %s\n", curr.name, curr.localDateTime, curr.category));
            }

            Platform.runLater(() -> indexInfo.setText(indexText.toString()));
        } else {
            Platform.runLater(() -> indexInfo.setText("Failed to get indexes."));
        }
    }
}