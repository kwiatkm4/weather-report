package pl.edu.pw.mwo1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Temperature{
    @JsonProperty("Metric")
    public Metric metric;
}