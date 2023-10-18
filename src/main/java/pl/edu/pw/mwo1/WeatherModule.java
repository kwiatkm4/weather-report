package pl.edu.pw.mwo1;

import com.google.inject.AbstractModule;

public class WeatherModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WeatherService.class);
    }
}
