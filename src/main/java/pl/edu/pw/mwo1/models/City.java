package pl.edu.pw.mwo1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City{
    @JsonProperty("Key")
    public String key;
    @JsonProperty("LocalizedName")
    public String localizedName;
    @JsonProperty("Country")
    public Country country;
}