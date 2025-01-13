package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LauncherTest {

    private int port;

    @BeforeEach
    void setUp() throws IOException
    {
        try (ServerSocket socket = new ServerSocket(0))
        {
            port = socket.getLocalPort();
        }
    }

    @AfterEach
    void tearDown()
    {
        //I will try something
    }

    @Test
    void testInvalidArgumentThrowsException()
    {
        assertThrows(NumberFormatException.class, () -> Launcher.main(new String[]{"azert"}),
                "Expected NumberFormatException for invalid port argument");
    }

    @Test
    void testNoArgumentThrowsException()
    {
        assertThrows(NumberFormatException.class, () -> Launcher.main(new String[]{""}),
                "Expected NumberFormatException for empty argument");
    }

    @Test
    void testPingEndpointWithValidPort() throws Exception
    {
        Launcher.main(new String[]{String.valueOf(port)});
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/ping"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertThat(response.statusCode()).as("HTTP Status Code").isEqualTo(200);
        Assertions.assertThat(response.body()).as("Response Body").isEqualTo("OK");
    }
}