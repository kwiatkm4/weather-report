package pl.edu.pw.mwo1.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pw.mwo1.models.Endpoints;

public class ConfigHandler {
    public static Endpoints getConfig() {
        try {
            var mapper = new ObjectMapper();
            var url = ConfigHandler.class.getResource("AppConfig.json");

            return mapper.readValue(url, Endpoints.class);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
