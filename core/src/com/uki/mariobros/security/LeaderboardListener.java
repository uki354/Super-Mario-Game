package com.uki.mariobros.security;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class LeaderboardListener implements Net.HttpResponseListener {

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(httpResponse.getResultAsString());
        jsonValue.forEach(jsonValue1 -> HttpClient.leaderboard.put(jsonValue1.getString("username"), jsonValue1.getString("score")));
    }

    @Override
    public void failed(Throwable throwable) {

    }

    @Override
    public void cancelled() {

    }
}
