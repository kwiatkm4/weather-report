package pl.edu.pw.mwo1.handlers;

import com.google.inject.AbstractModule;
import pl.edu.pw.mwo1.services.WeatherService;

public class WeatherModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WeatherService.class);
    }
}
