package pl.edu.pw.mwo1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Alarm {
    @JsonProperty("Date")
    public String date;
    @JsonProperty("Alarms")
    public ArrayList<Alarms> alarms;
}
