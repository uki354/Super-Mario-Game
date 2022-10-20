package com.uki.mariobros.http;

import com.badlogic.gdx.Net;
import com.uki.mariobros.screen.StartScreen;

public class DefaultListener implements Net.HttpResponseListener {

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        if(httpResponse.getStatus().getStatusCode() == 200){
            StartScreen.successfulSignUp = true;
        }else StartScreen.errorOccurred = true;

    }

    @Override
    public void failed(Throwable throwable) {
        StartScreen.errorOccurred = true;

    }

    @Override
    public void cancelled() {
        StartScreen.errorOccurred = true;

    }
}
