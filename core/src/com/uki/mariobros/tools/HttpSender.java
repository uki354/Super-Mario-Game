package com.uki.mariobros.tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;


import static com.badlogic.gdx.Net.HttpMethods.GET;
import static com.badlogic.gdx.Net.HttpMethods.POST;

public class HttpSender {

    public static final String URL = "http://localhost:8080/api";

    public void sendPostRequest(String url, String body, Net.HttpResponseListener listener){
        Net.HttpRequest request = configurePostRequest(url, body);
        Gdx.net.sendHttpRequest(request, listener);

    }

    public void sendPostRequest(String url, String body, String token, Net.HttpResponseListener listener){
        Net.HttpRequest request = configurePostRequest(url, body);
        request.setHeader("Authorization", "Bearer " + token);
        Gdx.net.sendHttpRequest(request, listener);
    }

    public void sendGetRequest(String url, Net.HttpResponseListener listener){
        Net.HttpRequest request = configureGetRequest(url);
        Gdx.net.sendHttpRequest(request, listener);
    }

    public void sendGetRequest(String url, String token, Net.HttpResponseListener listener){
        Net.HttpRequest request = configureGetRequest(url);
        request.setHeader("Authorization", "Bearer " + token);
        Gdx.net.sendHttpRequest(request,listener);
    }


    private Net.HttpRequest configureGetRequest(String url){
        Net.HttpRequest request  = new Net.HttpRequest(GET);
        request.setUrl(url);
        request.setTimeOut(20000);
        return  request;
    }

    private Net.HttpRequest configurePostRequest(String url, String body){
        Net.HttpRequest request  = new Net.HttpRequest(POST);
        request.setUrl(url);
        request.setHeader("Content-type","application/json");
        request.setContent(body);
        request.setTimeOut(20000);
        return request;
    }



}
