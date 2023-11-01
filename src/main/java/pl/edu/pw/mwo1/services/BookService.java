package pl.edu.pw.mwo1.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pw.mwo1.models.BookDto;
import pl.edu.pw.mwo1.models.Endpoints;
import pl.edu.pw.mwo1.models.ServiceResponse;
import pl.edu.pw.mwo1.utils.ConfigHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class BookService {
    private final HttpClient client;
    private final ObjectMapper mapper;
    private final String BASE_URL = "http://localhost:8080";
    private final Endpoints endpoints;

    public BookService() {
        this.client = HttpClient.newHttpClient();
        this.endpoints = ConfigHandler.getConfig();
        this.mapper = new ObjectMapper();
    }

    public synchronized List<BookDto> getAll() {
        try {
            var uri = BASE_URL + endpoints.getBook().getMulti();
            var request = HttpRequest.newBuilder(new URI(uri)).GET().build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.body() == null) return null;

            return mapper.readValue(response.body(), new TypeReference<ServiceResponse<List<BookDto>>>() {
            }).getData();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized BookDto get(int id) {
        try {
            var uri = BASE_URL + String.format(endpoints.getBook().getSingle(), id);
            var request = HttpRequest.newBuilder(new URI(uri)).GET().build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.body() == null) return null;

            return mapper.readValue(response.body(), new TypeReference<ServiceResponse<BookDto>>() {
            }).getData();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized void create(BookDto dto) {
        try {
            var uri = BASE_URL + endpoints.getBook().getMulti();
            var data = mapper.writeValueAsString(dto);
            var request = HttpRequest.newBuilder(new URI(uri))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void update(int id, BookDto dto) {
        try {
            var uri = BASE_URL + String.format(endpoints.getBook().getSingle(), id);
            var data = mapper.writeValueAsString(dto);
            var request = HttpRequest.newBuilder(new URI(uri))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void delete(int id) {
        try {
            var uri = BASE_URL + String.format(endpoints.getBook().getSingle(), id);
            var request = HttpRequest.newBuilder(new URI(uri))
                    .DELETE()
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
