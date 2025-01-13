package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

class CallHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        String responseMessage = "OK";

        httpExchange.sendResponseHeaders(200, responseMessage.length());

        try (OutputStream responseStream = httpExchange.getResponseBody())
        {
            responseStream.write(responseMessage.getBytes());
        }
    }
}