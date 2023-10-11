package pl.edu.pw.mwo1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Headline {
    @JsonProperty("EffectiveDate")
    public String effectiveDate;
    @JsonProperty("Text")
    public String text;
}
