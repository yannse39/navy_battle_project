package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class FireHandlerTest {

    @Test
    void testFireHandlerResponse() throws IOException, InterruptedException
    {
        new Serveur(9876);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9876/api/game/fire"))
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, httpResponse.statusCode());
        String expectedJson = "{\n\t\"consequence\":\"true\",\n\t\"shipLeft\":\"true\"\n}";
        Assertions.assertEquals(expectedJson, httpResponse.body());
    }
}