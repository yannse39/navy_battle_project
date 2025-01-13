package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

class FireHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException
    {
        String body = "{\n\t\"consequence\":\"true\",\n\t\"shipLeft\":\"true\"\n}";
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody())
        {
            os.write(body.getBytes());
        }
    }
}