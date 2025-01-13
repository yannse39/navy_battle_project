package fr.lernejo.navy_battle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

class CallHandlerTest {

    private Serveur server;
    private int port;

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
        server.stopServer();
    }

    @Test
    void testOfPing() throws IOException, InterruptedException
    {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/ping"))
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, httpResponse.statusCode(), "Expected HTTP status code 200");
        Assertions.assertEquals("OK", httpResponse.body(), "Expected response body to be 'OK'");
    }
}