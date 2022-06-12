package com.uki.mariobros.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.uki.mariobros.screen.StartScreen;
import com.uki.mariobros.security.Auth;

import static com.badlogic.gdx.Net.HttpMethods.POST;

public class HttpClient {

    public static final String URL = "http://localhost:8080/nesto";


    public static boolean sendPostRequest(String url, String body){
        Net.HttpRequest request = new Net.HttpRequest(POST);
        request.setHeader("Content-type", "application/json");
        request.setContent(body);
        request.setTimeOut(3);

        request.setUrl(url);
        try {
            Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    System.out.println(httpResponse);

                }

                @Override
                public void failed(Throwable t) {
                    Auth.isErrorOccurred = true;
                }

                @Override
                public void cancelled() {

                }
            });
        }catch (Exception e){
            return false;
        }
        return true;
    }




}
