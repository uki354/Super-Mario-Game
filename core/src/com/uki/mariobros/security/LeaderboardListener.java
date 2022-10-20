package com.uki.mariobros.security;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardListener implements Net.HttpResponseListener {

    public static List<LeaderboardUser> leaderboardUsers;

    public LeaderboardListener(){
        leaderboardUsers = new ArrayList<>();
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(httpResponse.getResultAsString());
        for (JsonValue json: jsonValue){
            String username = json.getString("username");
            String score = json.getString("score");
            LeaderboardUser user = new LeaderboardUser(username, score);
            leaderboardUsers.add(user);
        }


    }

    @Override
    public void failed(Throwable throwable) {

    }

    @Override
    public void cancelled() {

    }

    public  static List<LeaderboardUser> getLeaderboardUsers() {
        return leaderboardUsers;
    }

    public static class LeaderboardUser{
        private final String username;
        private final String score;

        LeaderboardUser(String username, String score){
            this.username = username;
            this.score = score;


        }

        public String getScore() {
            return score;
        }

        public String getUsername() {
            return username;
        }
    }


}
