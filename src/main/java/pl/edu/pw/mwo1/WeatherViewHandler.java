package pl.edu.pw.mwo1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class WeatherViewHandler {
    private final WeatherService service;
    private final static String VIEW_NAME = "weather-view.fxml";
    private final static int WIDTH = 800;
    private final static int HEIGHT = 640;

    public WeatherViewHandler(WeatherService service) {
        this.service = service;
    }

    public Scene getWeatherScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(WeatherReport.class.getResource(VIEW_NAME));
        Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);

        WeatherController controller = loader.getController();
        WeatherViewModel viewModel = new WeatherViewModel(service);
        controller.initialize(viewModel);

        return scene;
    }
}
