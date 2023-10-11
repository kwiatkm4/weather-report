package pl.edu.pw.mwo1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
    @JsonProperty("Headline")
    public Headline headline;
}
