package com.uki.mariobros.http;
import java.util.List;


public class HttpClient {
    public static boolean  isErrorOccurred = false;
    private final HttpSender httpSender;


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
        getLeaderboard(user.getToken());

    }

    public List<LeaderboardListener.LeaderboardUser> getLeaderboard(String token){
        httpSender.sendGetRequest(HttpSender.URL + "/score/leaderboard",token, new LeaderboardListener());
        return LeaderboardListener.getLeaderboardUsers();
    }









}
