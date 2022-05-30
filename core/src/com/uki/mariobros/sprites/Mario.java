package com.uki.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.screen.PlayScreen;

import static com.uki.mariobros.MarioBros.*;

public class Mario extends Sprite {

    public enum State { FALLING, JUMPING, STANDING, RUNNING, GROWING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2Body;
    private TextureRegion marioStand;
    private Animation marioRun;
    private TextureRegion marioJump;
    private boolean runningRight;
    private float stateTimer;
    private TextureRegion bigMarioStand;
    private TextureRegion bigMarioJump;
    private Animation bigMarioRun;
    private Animation growMario;
    private boolean isMarioBig;
    private boolean runGrowAnimation;




    public Mario(PlayScreen screen){
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        bigMarioStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0, 16,32);
        bigMarioJump = new TextureRegion(screen.getAtlas().findRegion("big_mario"),80,0, 16,32);

        Array<TextureRegion> frames = new Array<>();
        for (int i  = 1; i < 4; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), i * 16,0 ,16,32));
            bigMarioRun = new Animation(0.1f,frames);
        }
        frames.clear();


        
        //Refactor for loops
        for (int i = 1; i < 4; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i * 16, 0, 16, 16));
            marioRun = new Animation(0.1f, frames);
        }
        frames.clear();

        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0 , 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0 , 16, 16));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0 , 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0 , 16, 16));
        growMario = new Animation(0.2f, frames);


        marioJump = new TextureRegion(screen.getAtlas().findRegion("little_mario"),  80, 0, 16,16);

        defineMario();
        marioStand = new TextureRegion(screen.getAtlas().findRegion("little_mario"),0,0,16,16);
        setBounds(0,0,16,16);
        setRegion(marioStand);
    }

    public void update(float time){
        setPosition(b2Body.getPosition().x - getWidth() / 2 , b2Body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(time));

    }

    public void grow(){
        runGrowAnimation = true;
        isMarioBig = true;
        setBounds(getX(),getY(),getWidth(),getHeight() * 2);
    }

    public TextureRegion getFrame(float time){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case GROWING:
                region = (TextureRegion) growMario.getKeyFrame(stateTimer);
                if(growMario.isAnimationFinished(stateTimer))
                    runGrowAnimation = false;
                break;
            case JUMPING:
                region = isMarioBig ? bigMarioJump : marioJump;
                break;
            case RUNNING:
                region = isMarioBig ?  (TextureRegion) bigMarioRun.getKeyFrame(stateTimer,true) :(TextureRegion) marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = isMarioBig ? bigMarioStand : marioStand;
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

        if(runGrowAnimation)
            return State.GROWING;
        else if(b2Body.getLinearVelocity().y > 0 || b2Body.getLinearVelocity().y < 0 && previousState == State.JUMPING)
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
        fixtureDef.filter.maskBits = DEFAULT_BIT | COIN_BIT
                | BRICK_BIT | ENEMY_BIT
                | OBJECT_BIT  | ENEMY_HEAD_BIT 
                | ITEM_BIT;



        fixtureDef.shape = shape;
        b2Body.createFixture(fixtureDef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2,6), new Vector2(2, 6));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;

        b2Body.createFixture(fixtureDef).setUserData("head");




    }
}
