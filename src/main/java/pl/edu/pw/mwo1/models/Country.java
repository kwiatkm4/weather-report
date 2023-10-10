package pl.edu.pw.mwo1.models;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Country{
    @JsonProperty("ID")
    public String iD;
    @JsonProperty("LocalizedName")
    public String localizedName;
}