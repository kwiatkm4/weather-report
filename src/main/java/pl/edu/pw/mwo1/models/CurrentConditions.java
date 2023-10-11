package pl.edu.pw.mwo1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentConditions {
    @JsonProperty("WeatherText")
    public String weatherText;
    @JsonProperty("Temperature")
    public Temperature temperature;
}
