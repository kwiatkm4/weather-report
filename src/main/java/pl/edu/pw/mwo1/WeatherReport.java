package pl.edu.pw.mwo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WeatherReport extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        WeatherService service = new WeatherService();
        WeatherViewHandler handler = new WeatherViewHandler(service);
        Scene scene = handler.getWeatherScene();

        stage.setTitle("Weather Report");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}