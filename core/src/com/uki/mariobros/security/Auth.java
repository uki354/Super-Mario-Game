package com.uki.mariobros.security;

import com.uki.mariobros.tools.HttpClient;



public class Auth {

    public static boolean isErrorOccurred = false;

    public boolean authenticate(User user) {
       HttpClient.sendPostRequest(HttpClient.URL, user.toString());
       return  isErrorOccurred;
    }





}
