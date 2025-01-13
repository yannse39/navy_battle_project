package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Serveur {

    private final HttpServer httpServer;
    private final ExecutorService executorService;

    Serveur(int port) throws IOException
    {
        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        this.executorService = Executors.newSingleThreadExecutor();

        configureServer(port);
    }

    private void configureServer(int port)
    {
        httpServer.setExecutor(executorService);

        httpServer.createContext("/ping", new CallHandler());
        httpServer.createContext("/api/game/start", new PostHandler(port));
        httpServer.createContext("/api/game/fire", new FireHandler());

        startServer();
    }

    private void startServer()
    {
        httpServer.start();
        System.out.println("Server is running and listening for requests...");
    }

    public void stopServer()
    {
        httpServer.stop(0);
        executorService.shutdown();
        System.out.println("Server has been stopped.");
    }
}