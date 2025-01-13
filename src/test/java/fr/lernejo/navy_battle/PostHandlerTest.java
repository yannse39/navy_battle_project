package fr.lernejo.navy_battle;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

class PostHandlerTest {
    private static Serveur server;
    private static int port;

    @BeforeEach
    void setUp() throws IOException
    {
        try (ServerSocket socket = new ServerSocket(0))
        {
            port = socket.getLocalPort();
        }
        server = new Serveur(port);
    }

    @Test
    void postReqTest() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/game/start"))
                .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + port + "\", \"message\":\"HELLO THE WINNER!\"}"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(202, response.statusCode());
    }

    @Test
    void another_ping_cheking() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/game/start"))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(404, response.statusCode());
    }
    @Test
    void postRequestWithEmptyJsonFields() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = "{\"id\":\"\", \"url\":\"\", \"message\":\"\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/game/start"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(400, response.statusCode());
    }
    @Test
    void postRequestWithLogicalInvalidJson() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = "{\"id\":\"invalidId\", \"url\":\"http://invalid\", \"message\":\"Test Message\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/game/start"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(202, response.statusCode());

    }



}