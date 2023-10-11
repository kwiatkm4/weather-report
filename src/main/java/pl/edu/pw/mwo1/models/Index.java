package pl.edu.pw.mwo1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Index {
    @JsonProperty("Name")
    public String name;
    @JsonProperty("LocalDateTime")
    public String localDateTime;
    @JsonProperty("Category")
    public String category;
}
