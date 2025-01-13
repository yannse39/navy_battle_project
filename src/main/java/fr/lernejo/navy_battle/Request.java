package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class Request {

    public void sendPostRequest(int serverPort, String opponentUrl) throws IOException, InterruptedException
    {
        HttpClient httpClient = HttpClient.newHttpClient();
        String requestBody = String.format(
                "{\"id\":\"1\", \"url\":\"http://localhost:%d\", \"message\":\"Hello, may the best win!\"}",
                serverPort
        );

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(opponentUrl + "/api/game/start"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        handleResponse(response);
    }

    private void handleResponse(HttpResponse<String> response)
    {
        int statusCode = response.statusCode();
        String responseBody = response.body();

        if (statusCode == 202)
        {
            System.out.println("Request successful! Response body:");
        }
        else
        {
            System.out.printf("Request failed with status code %d.%n", statusCode);
        }
        System.out.println(responseBody);
    }
}