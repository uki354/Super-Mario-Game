package com.uki.mariobros.tools;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Login extends ClickListener {


    private String username;
    private String password;

    private void authenticate(){
        //send login request
        //wait for answer
        // if positive let, negative display message
    }

    private void signUp(){

    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        System.out.println("Login");
    }
}
