package com.uki.mariobros.http;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String password;
    private String token;
    public boolean loggedIn = false;


    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String token){
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }



    @Override
    public String toString() {
        return " {" +
                "\"username\":\"" + username +  "\""+
                ", \"password\":\"" + password  + "\""+
                '}';
    }
}
