package pl.edu.pw.mwo1.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.edu.pw.mwo1.models.*;
import pl.edu.pw.mwo1.viewmodels.WeatherViewModel;

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
    private final WeatherViewModel viewModel;

    public WeatherController(WeatherViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void initialize() {
        cityList.itemsProperty().bind(viewModel.citiesProperty());
        searchButton.disableProperty().bind(viewModel.isSearchDisabledProperty());
        cityCountLabel.textProperty().bind(viewModel.searchResultsProperty());
        weatherButton.disableProperty().bind(viewModel.isWeatherSearchDisabledProperty());
        weatherButton.visibleProperty().bind(viewModel.isWeatherSearchVisibleProperty());
        cityList.visibleProperty().bind(viewModel.isCityListVisibleProperty());
        infoHeader.textProperty().bind(viewModel.infoHeaderProperty());
        forecastInfo.textProperty().bind(viewModel.forecastInfoProperty());
        alarmInfo.textProperty().bind(viewModel.alarmInfoProperty());
        indexInfo.textProperty().bind(viewModel.indexInfoProperty());
        conditionInfo.textProperty().bind(viewModel.conditionInfoProperty());

        cityList.itemsProperty().addListener((observable -> {
            if (!cityList.getItems().isEmpty()) {
                cityList.setValue(cityList.getItems().get(0));
            }
        }));
    }

    public void onCitySearch() {
        viewModel.citySearch(citySearchbar.getText());
    }

    public void onWeatherInfoSearch() {
        viewModel.weatherInfo(cityList.getValue());
    }
}