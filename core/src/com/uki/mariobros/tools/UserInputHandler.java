package com.uki.mariobros.tools;

import com.badlogic.gdx.math.Vector2;
import com.uki.mariobros.sprites.Mario;

import static com.uki.mariobros.sprites.Mario.State.*;


public class UserInputHandler {

    private final Mario mario;
    public static final Vector2 X_VEL = new Vector2(20f,0);
    public static final Vector2 Y_VEL = new Vector2(0, 1000);
    public static final Vector2 X_VEL_N = new Vector2(-20f,0);
    private boolean doubleJump = true;
    public enum Side{LEFT, RIGHT};

    public UserInputHandler(Mario mario){
        this.mario = mario;

    }

    public void run(Side side){
        if(side == Side.RIGHT && mario.b2Body.getLinearVelocity().x < 80 && mario.b2Body.getLinearVelocity().y > -15){
            mario.b2Body.applyLinearImpulse(X_VEL, mario.b2Body.getWorldCenter(),true);
        }
        if (side == Side.LEFT && mario.b2Body.getLinearVelocity().x > -80 && mario.b2Body.getLinearVelocity().y > -15)
            mario.b2Body.applyLinearImpulse(X_VEL_N, mario.b2Body.getWorldCenter(),true);
    }

    public void jump(Vector2 velocity){

        if((mario.getState() == JUMPING) && (mario.b2Body.getLinearVelocity().y < 0) && doubleJump){
            Vector2 previousVelocity = mario.b2Body.getLinearVelocity();
            mario.b2Body.setLinearVelocity(new Vector2(previousVelocity.x, 500));
            mario.b2Body.applyForceToCenter(velocity,true);
            doubleJump = false;
            return;
        }

        if(mario.getState().equals(STANDING)|| mario.getState().equals(RUNNING)){
            mario.b2Body.setLinearVelocity(velocity);
            doubleJump = true;
            return;
        }


    }
}
