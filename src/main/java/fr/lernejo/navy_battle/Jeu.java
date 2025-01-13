package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class Jeu {
    private final Json Adversary;

    public void initGame() throws IOException, InterruptedException
    {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(Adversary.getServerUrl().substring(1, Adversary.getServerUrl().length() - 1) + "/api/game/fire?cell=A1"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(send.body());

    }
    Jeu(Json body1, Json body2)
    {
        this.Adversary = body1;
    }
}
