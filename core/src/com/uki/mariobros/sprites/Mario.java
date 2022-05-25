package com.uki.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.screen.PlayScreen;

public class Mario extends Sprite {

    public enum State { FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2Body;
    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private boolean runningRight;
    private float stateTimer;


    public Mario(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        Array<TextureRegion> frames = new Array<>();

        
        //Refactor for loops
        for (int i = 1; i < 4; i++){
            frames.add(new TextureRegion(getTexture(), i * 16, 11, 16, 16));
            marioRun = new Animation(0.1f, frames);
        }
        frames.clear();
        
        for ( int i = 4; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), i * 16, 11, 16,16));
            marioJump = new Animation(0.1f, frames);
        }



        defineMario();
        marioStand = new TextureRegion(getTexture(),1,11,16,16);
        setBounds(0,0,16,16);
        setRegion(marioStand);
    }

    public void update(float time){
        setPosition(b2Body.getPosition().x - getWidth() / 2 , b2Body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(time));

    }

    public TextureRegion getFrame(float time){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = (TextureRegion) marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;

        }

        if((b2Body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        }else if((b2Body.getLinearVelocity().x > 0 || runningRight) & region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + time : 0;
        previousState  = currentState;
        return region;
    }

    public State getState(){
        if(b2Body.getLinearVelocity().y > 0 || b2Body.getLinearVelocity().y < 0 && previousState == State.JUMPING)
            return State.JUMPING;
        else if (b2Body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2Body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else return State.STANDING;
    }

    private void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / MarioBros.PPM, 32 / MarioBros.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / MarioBros.PPM);
        fixtureDef.filter.categoryBits = MarioBros.MARIO_BIT;
        fixtureDef.filter.maskBits = MarioBros.DEFAULT_BIT | MarioBros.COIN_BIT | MarioBros.BRICK_BIT;



        fixtureDef.shape = shape;
        b2Body.createFixture(fixtureDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2,6), new Vector2(2, 6));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;

        b2Body.createFixture(fixtureDef).setUserData("head");




    }
}
