package fr.lernejo.navy_battle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

class Json {

    private final String gameId;
    private final String serverUrl;
    private final String customMessage;

    Json(@JsonProperty("id") JsonNode gameId,
         @JsonProperty("url") JsonNode serverUrl,
         @JsonProperty("message") JsonNode customMessage) {
        this.gameId = gameId.toString();
        this.serverUrl = serverUrl.toString();
        this.customMessage = customMessage.toString();
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