package fr.lernejo.navy_battle;

import com.fasterxml.jackson.annotation.JsonProperty;

class Json {

    private final String gameId;
    private final String serverUrl;
    private final String customMessage;

    public Json(
            @JsonProperty("id") String gameId,
            @JsonProperty("url") String serverUrl,
            @JsonProperty("message") String customMessage
    ) {
        this.gameId = gameId;
        this.serverUrl = serverUrl;
        this.customMessage = customMessage;
    }

    public String getGameId()
    {
        return gameId;
    }

    public String getServerUrl()
    {
        return serverUrl;
    }

    public String getCustomMessage()
    {
        return customMessage;
    }
}