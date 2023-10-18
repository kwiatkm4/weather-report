package pl.edu.pw.mwo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WeatherReport extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        WeatherViewHandler handler = new WeatherViewHandler();
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