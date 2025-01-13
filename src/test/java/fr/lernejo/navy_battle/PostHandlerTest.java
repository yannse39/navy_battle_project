package fr.lernejo.navy_battle;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

    @AfterEach
    void tearDown()
    {
        // I will try somethong
    }

    @Test
    void testValidPostRequest() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/game/start"))
                .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + port + "\", \"message\":\"HELLO THE WINNER!\"}"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(202, response.statusCode(), "Expected 202 for valid POST request");
    }

    @Test
    void testInvalidHttpMethod() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/game/start"))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(404, response.statusCode(), "Expected 404 for unsupported HTTP method");
    }

    @Test
    void testPostRequestWithEmptyJsonFields() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = "{\"id\":\"\", \"url\":\"\", \"message\":\"\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/game/start"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(400, response.statusCode(), "Expected 400 for POST request with empty JSON fields");
    }

    @Test
    void testPostRequestWithLogicalInvalidJson() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = "{\"id\":\"invalidId\", \"url\":\"http://invalid\", \"message\":\"Test Message\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/game/start"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(202, response.statusCode(), "Expected 202 for POST request with logically invalid JSON");
    }
}