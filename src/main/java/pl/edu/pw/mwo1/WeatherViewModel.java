package pl.edu.pw.mwo1;

public class WeatherViewModel {
    private WeatherService service;
    public WeatherViewModel(WeatherService service) {
        this.service = service;
    }
}
