package pl.edu.pw.mwo1;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import pl.edu.pw.mwo1.models.City;

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

    private void toggleWeatherUI(boolean isShown) {
        isCityListVisible.setValue(isShown);
        isWeatherSearchVisible.setValue(isShown);
    }

    public ListProperty<City> citiesProperty() {
        return cities;
    }

    public void weatherInfo(City city) {
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
}
