package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

class PostHandler implements HttpHandler {
    private final StringBuilder stringBuilder;

    PostHandler(int port)
    {
        this.stringBuilder = new StringBuilder();
        this.stringBuilder.append("http://localhost:").append(port);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        if (!exchange.getRequestMethod().equals("POST"))
        {
            error(exchange);
        }
        else
        {
            processPostRequest(exchange);
        }
    }

    private void processPostRequest(HttpExchange exchange) throws IOException {
        Json body = parser(exchange);
        if (isInvalidJson(body)) {
            msg(exchange, "format non valide", 400);
        }
        else
        {
            respondAndInitGame(exchange, body);
        }
    }

    private boolean isInvalidJson(Json body) {
        return body == null || body.getServerUrl().equals("\"\"") || body.getGameId().equals("\"\"") || body.getCustomMessage().equals("\"\"");
    }

    private void respondAndInitGame(HttpExchange exchange, Json body) throws IOException {
        msg(exchange, "{\n\t\"id\":\"" + UUID.randomUUID() + "\",\n\t\"url\":\"" + this.stringBuilder + "\",\n\t\"message\":\"May the best code win\"\n}", 202);
        var game = new Jeu(body, body);
        try {
            game.startGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void msg(HttpExchange exchange, String message, int code) throws IOException {
        exchange.sendResponseHeaders(code, message.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }

    private void error(HttpExchange exchange) throws IOException {
        String body = "Not Found !";
        exchange.sendResponseHeaders(404, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

    private Json parser(HttpExchange exchange) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Json body = null;
        String streamString = formatConversion(exchange.getRequestBody());
        if (streamString.isBlank()) {
            return null;
        } else {
            try {
                body = objectMapper.readValue(streamString, Json.class);
            } catch (IllegalArgumentException e) {
                exchange.sendResponseHeaders(400, "not exist".length());
                throw new IllegalArgumentException();
            }
        }
        return body;
    }

    private String formatConversion(InputStream str) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int i;
        while ((i = str.read()) > 0) {
            stringBuilder.append((char) i);
        }
        return stringBuilder.toString();
    }
}
