package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Jeu {
    private final String adversaryUrl;
    private final String playerUrl;

    public Jeu(Json body, Json playerInfo)
    {
        this.adversaryUrl = body.getServerUrl();
        this.playerUrl = playerInfo.getServerUrl();
    }

    public void startGame() throws IOException, InterruptedException
    {
        System.out.println("Game started. Sending first fire request...");
        gameLoop();
    }

    public void gameLoop() throws IOException, InterruptedException {
        while (true)
        {
            String cellToFire = calculateNextCell();
            String fireResponse = sendFireRequest(this.adversaryUrl, cellToFire);

            if (isGameOver(fireResponse))
            {
                System.out.println("Game over! You win!");
                break;
            }

            waitForAdversaryFire();
        }
    }

    public String sendFireRequest(String adversaryUrl, String cell) throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(adversaryUrl + "/api/game/fire?cell=" + cell))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Fire response: " + response.body());

        return response.body();
    }

    private boolean isGameOver(String responseBody) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode response = objectMapper.readTree(responseBody);
        boolean shipLeft = response.get("shipLeft").asBoolean();
        return !shipLeft;
    }

    private void waitForAdversaryFire()
    {
        System.out.println("Waiting for adversary fire...");

    }

    private String calculateNextCell()
    {

        return "A1";
    }
}