package pl.edu.pw.mwo1.viewmodels;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import pl.edu.pw.mwo1.services.WeatherService;
import pl.edu.pw.mwo1.models.City;
import pl.edu.pw.mwo1.models.CurrentConditions;
import pl.edu.pw.mwo1.models.Index;

import java.util.ArrayList;
import java.util.List;

public class WeatherViewModel {
    private final WeatherService service;
    private final ListProperty<City> cities;
    private final BooleanProperty isSearchDisabled;
    private final BooleanProperty isWeatherSearchDisabled;
    private final BooleanProperty isWeatherSearchVisible;
    private final BooleanProperty isCityListVisible;
    private final StringProperty searchResults;
    private final StringProperty infoHeader;
    private final StringProperty conditionInfo;
    private final StringProperty forecastInfo;
    private final StringProperty indexInfo;
    private final StringProperty alarmInfo;

    @Inject
    public WeatherViewModel(WeatherService service) {
        this.service = service;

        this.cities = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
        this.isSearchDisabled = new SimpleBooleanProperty(false);
        this.searchResults = new SimpleStringProperty("");
        this.isWeatherSearchDisabled = new SimpleBooleanProperty(false);
        this.isWeatherSearchVisible = new SimpleBooleanProperty(false);
        this.isCityListVisible = new SimpleBooleanProperty(false);
        this.infoHeader = new SimpleStringProperty("");
        this.conditionInfo = new SimpleStringProperty("");
        this.forecastInfo = new SimpleStringProperty("");
        this.indexInfo = new SimpleStringProperty("");
        this.alarmInfo = new SimpleStringProperty("");
    }

    public void citySearch(String text) {
        if (text == null || text.isEmpty()) {
            return;
        }

        isSearchDisabled.setValue(true);

        Thread searcher = new Thread(() -> {
            List<City> cityData = service.getCities(text);
            int cityCount = cityData != null ? cityData.size() : 0;

            Platform.runLater(() -> searchResults.setValue(String.format("Found %d cities:", cityCount)));

            if (cityData == null || cityData.isEmpty()) {
                Platform.runLater(() -> {
                    isSearchDisabled.setValue(false);
                    toggleWeatherUI(false);
                });

                return;
            }

            Platform.runLater(() -> {
                toggleWeatherUI(true);
                cities.setValue(FXCollections.observableList(cityData));
                isSearchDisabled.setValue(false);
            });
        });

        searcher.start();
    }

    public void weatherInfo(City city) {
        String chosenCityId = city.key;

        if (chosenCityId == null || chosenCityId.isEmpty()) {
            return;
        }

        Thread reporter = new Thread(() -> {
            Platform.runLater(() -> {
                disableButtons(true);
                clearWeatherInfo();

                infoHeader.setValue(String.format("Weather info for %s:", city.localizedName));
            });

            findConditions(chosenCityId);
            findAlarms(chosenCityId);
            findForecast(chosenCityId);
            findIndexes(chosenCityId);

            Platform.runLater(() -> disableButtons(false));
        });

        reporter.start();
    }

    private void clearWeatherInfo() {
        conditionInfo.setValue("");
        indexInfo.setValue("");
        forecastInfo.setValue("");
        alarmInfo.setValue("");
    }

    private void toggleWeatherUI(boolean isShown) {
        isCityListVisible.setValue(isShown);
        isWeatherSearchVisible.setValue(isShown);
    }

    private void disableButtons(boolean isShown) {
        isSearchDisabled.setValue(isShown);
        isWeatherSearchDisabled.setValue(isShown);
    }

    public ListProperty<City> citiesProperty() {
        return cities;
    }

    public BooleanProperty isSearchDisabledProperty() {
        return isSearchDisabled;
    }

    public StringProperty indexInfoProperty() {
        return indexInfo;
    }

    public BooleanProperty isWeatherSearchDisabledProperty() {
        return isWeatherSearchDisabled;
    }

    public BooleanProperty isWeatherSearchVisibleProperty() {
        return isWeatherSearchVisible;
    }

    public BooleanProperty isCityListVisibleProperty() {
        return isCityListVisible;
    }

    public StringProperty searchResultsProperty() {
        return searchResults;
    }

    public StringProperty infoHeaderProperty() {
        return infoHeader;
    }

    public StringProperty conditionInfoProperty() {
        return conditionInfo;
    }

    public StringProperty forecastInfoProperty() {
        return forecastInfo;
    }

    public StringProperty alarmInfoProperty() {
        return alarmInfo;
    }

    private void findConditions(String key) {
        var conditions = service.getCurrentConditions(key);

        if (conditions != null) {
            var conditionReport = new StringBuilder();

            for (CurrentConditions c : conditions) {
                conditionReport.append(String.format("Current weather: %s, %g %s\n", c.weatherText, c.temperature.metric.value, c.temperature.metric.unit));
            }

            Platform.runLater(() -> conditionInfo.setValue(conditionReport.toString()));
        } else {
            Platform.runLater(() -> conditionInfo.setValue("Failed to get current conditions."));
        }
    }

    private void findAlarms(String key) {
        var alarms = service.getAlarms(key);
        var alarmText = alarms != null ? String.format("%d alarms issued for the city.", alarms.size()) :
                "Failed to get alarms.";

        Platform.runLater(() -> alarmInfo.setValue(alarmText));
    }

    private void findForecast(String key) {
        var forecast = service.getForecast(key);
        var forecastText = forecast != null ?
                String.format("Forecast for %s: %s\n\n", forecast.headline.effectiveDate, forecast.headline.text) :
                "Failed to get forecast.";

        Platform.runLater(() -> forecastInfo.setValue(forecastText));
    }

    private void findIndexes(String key) {
        var indices = service.getIndices(key);

        if (indices != null) {
            var indexText = new StringBuilder();
            int toDisplay = Math.min(indices.size(), 5);

            for (int i = 0; i < toDisplay; i++) {
                Index curr = indices.get(i);
                indexText.append(String.format("State for index %s on %s: %s\n", curr.name, curr.localDateTime, curr.category));
            }

            Platform.runLater(() -> indexInfo.setValue(indexText.toString()));
        } else {
            Platform.runLater(() -> indexInfo.setValue("Failed to get indexes."));
        }
    }
}
