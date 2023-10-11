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
import pl.edu.pw.mwo1.models.*;

public class WeatherService {

    private final HttpClient client;
    private final static String API_KEY = "";
    private final static String BASE_URL = "http://dataservice.accuweather.com/";
    private final static String CITY_ENDPOINT = "locations/v1/cities/autocomplete?apikey=%s&language=pl-pl&q=%s";
    private final static String CURR_COND_ENDPOINT = "/currentconditions/v1/%s?apikey=%s&language=pl-pl";
    private final static String INDEX_ENDPOINT = "indices/v1/daily/1day/%s?apikey=%s&language=pl-pl";
    private final static String FORECAST_ENDPOINT = "forecasts/v1/daily/1day/%s?apikey=%s&language=pl-pl&metric=true";
    private final static String ALARM_ENDPOINT = "alarms/v1/1day/%s?apikey=%s&language=pl-pl";


    public WeatherService() {
        client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    }

    public synchronized List<City> getCities(String query) {

        String uriBuilder = BASE_URL +
                String.format(CITY_ENDPOINT, API_KEY, URLEncoder.encode(query, StandardCharsets.UTF_8));

        String response = sendRequest(uriBuilder);

        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(response, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public synchronized List<CurrentConditions> getCurrentConditions(String key) {
        String uriBuilder = BASE_URL +
                String.format(CURR_COND_ENDPOINT, key, API_KEY);

        String response = sendRequest(uriBuilder);

        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(response, new TypeReference<>() {});
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public synchronized List<Index> getIndices(String key) {
        String uriBuilder = BASE_URL +
                String.format(INDEX_ENDPOINT, key, API_KEY);

        String response = sendRequest(uriBuilder);

        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(response, new TypeReference<>() {});
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public synchronized Forecast getForecast(String key) {
        String uriBuilder = BASE_URL +
                String.format(FORECAST_ENDPOINT, key, API_KEY);

        String response = sendRequest(uriBuilder);

        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(response, Forecast.class);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public synchronized Alarm getAlarms(String key) {
        String uriBuilder = BASE_URL +
                String.format(ALARM_ENDPOINT, key, API_KEY);

        String response = sendRequest(uriBuilder);

        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(response, Alarm.class);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private synchronized String sendRequest(String uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(uri)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return null;
            }

            return response.body();

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
