package com.uki.mariobros.security;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import static com.badlogic.gdx.Net.HttpMethods.POST;

public class Auth {

    public static final String URL = "http://localhost:8080/nesto";

    public void authenticate(User user) {
        Net.HttpRequest request = new Net.HttpRequest(POST);
        request.setHeader("Content-type", "application/json");
        request.setContent(user.toString());

        request.setUrl(URL);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                System.out.println(httpResponse);
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });

    }





}
