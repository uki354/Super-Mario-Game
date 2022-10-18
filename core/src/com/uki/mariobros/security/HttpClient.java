package com.uki.mariobros.security;
import com.uki.mariobros.tools.HttpSender;

import java.util.HashMap;
import java.util.Map;

public class HttpClient {
    public static boolean  isErrorOccurred = false;
    private final HttpSender httpSender;
    public static Map<String, String> leaderboard;

    public HttpClient(HttpSender httpSender){
        this.httpSender = httpSender;
    }

    public boolean authenticate(User user) {
        httpSender.sendPostRequest(HttpSender.URL + "/login", user.toString(),new LoginListener());
        return true;
    }

    public boolean signUp(User user){
        httpSender.sendPostRequest(HttpSender.URL + "/user/create", user.toString(),new DefaultListener());
        return true;
    }

    public void saveScore(User user, int score){
        String jsonString = "{\"username\":\"" + user.getUsername() + "\"," +
                            "\"score\":\"" + score + "\"}";
        httpSender.sendPostRequest(HttpSender.URL + "/score/save", jsonString, user.getToken(), new DefaultListener());

    }

    public void getLeaderboard(String token){
        Map<String, String> map  = new HashMap<>();
        httpSender.sendGetRequest(HttpSender.URL + "/score/leaderboard",token, new LeaderboardListener());
    }









}
