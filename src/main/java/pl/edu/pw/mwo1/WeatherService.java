package pl.edu.pw.mwo1;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pw.mwo1.models.City;

public class WeatherService {

    private final HttpClient client;
    private final static String API_KEY = "";
    private final static String BASE_URL = "http://dataservice.accuweather.com/";
    private final static String CITY_ENDPOINT = "locations/v1/cities/autocomplete?apikey=%s&language=pl-pl&q=%s";


    public WeatherService() {
        client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    }

    public synchronized List<City> getCities(String query) {
        var uriBuilder = new StringBuilder();

        uriBuilder
                .append(BASE_URL)
                .append(String.format(CITY_ENDPOINT, API_KEY, URLEncoder.encode(query, StandardCharsets.UTF_8)));

        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(uriBuilder.toString())).GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
