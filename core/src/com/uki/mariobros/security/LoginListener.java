package com.uki.mariobros.security;

import com.badlogic.gdx.Net;
import com.uki.mariobros.screen.StartScreen;

public class LoginListener implements Net.HttpResponseListener {

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        if (httpResponse.getStatus().getStatusCode() ==  200) {
            String token = httpResponse.getResultAsString();
            StartScreen.user.setToken(token.substring(1, token.length() - 1));
            StartScreen.user.loggedIn = true;
        }else{
            HttpClient.isErrorOccurred = true;
        }
    }


    @Override
    public void failed(Throwable throwable) {

    }

    @Override
    public void cancelled() {

    }
}
