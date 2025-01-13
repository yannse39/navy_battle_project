package fr.lernejo.navy_battle;

public class Launcher {
    public static void main(String[] args) throws Exception
    {
        final int port = Integer.parseInt(args[0]);
        System.out.println("server started on http://localhost:" + port + "/");
        new Serveur(port);
        if (args.length > 1)
        {
            new Request().sendPostRequest(port, args[1]);
        }
    }
}
