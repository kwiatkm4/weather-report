package pl.edu.pw.mwo1.handlers;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import pl.edu.pw.mwo1.WeatherReport;
import pl.edu.pw.mwo1.viewmodels.WeatherViewModel;

import java.io.IOException;
import java.lang.reflect.Constructor;

public class WeatherViewHandler {
    private final static String VIEW_NAME = "weather-view.fxml";
    private final static int WIDTH = 800;
    private final static int HEIGHT = 640;

    public Scene getWeatherScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(WeatherReport.class.getResource(VIEW_NAME));

        Injector injector = Guice.createInjector(new WeatherModule());
        WeatherViewModel viewModel = injector.getInstance(WeatherViewModel.class);

        loader.setControllerFactory((Class<?> type) -> {
            try {
                for (Constructor<?> c : type.getConstructors()) {
                    if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == WeatherViewModel.class) {
                        return c.newInstance(viewModel);
                    }
                }

                return type.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return new Scene(loader.load(), WIDTH, HEIGHT);
    }
}
