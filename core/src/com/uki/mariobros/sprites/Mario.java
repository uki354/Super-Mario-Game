package com.uki.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.screen.PlayScreen;
import com.uki.mariobros.tools.Sounds;

import java.util.Objects;

import static com.uki.mariobros.MarioBros.*;
import static com.uki.mariobros.tools.Sounds.SOUND_POWER_DOWN;
import static com.uki.mariobros.tools.Sounds.SOUND_POWER_UP;

public class Mario extends Sprite {

    public enum State { FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD};
    public State currentState;
    public State previousState;
    private float stateTimer;
    public World world;
    public Body b2Body;

    private final TextureRegion marioJump;
    private final TextureRegion bigMarioStand;
    private final TextureRegion bigMarioJump;
    private final TextureRegion marioStand;
    private final TextureRegion marioDead;

    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> bigMarioRun;
    private Animation<TextureRegion> growMario;

    private boolean isMarioBig;
    private boolean runGrowAnimation;
    private boolean timeToDefineBigMario;
    private boolean timeToRedefineMario;
    private boolean runningRight;
    private boolean isMarioDead;

    private final Vector2 startPosition;
    private final Sounds sounds;
    private final PlayScreen playScreen;

    private static final String BIG_MARIO_REGION = "big_mario";
    private static final String LITTLE_MARIO_REGION = "little_mario";


    public Mario(PlayScreen screen){
        this.playScreen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        startPosition = new Vector2(32,32);
        sounds = Sounds.getInstance();
        runningRight = true;

        bigMarioStand = loadTextureRegion(BIG_MARIO_REGION,0,0,16,32);
        bigMarioJump = loadTextureRegion(BIG_MARIO_REGION,80,0,16,32);
        marioStand = loadTextureRegion(LITTLE_MARIO_REGION,0,0,16,16);
        marioJump = loadTextureRegion(LITTLE_MARIO_REGION, 80,0,16,16);
        marioDead = loadTextureRegion(LITTLE_MARIO_REGION, 96,0,16,16);

        Array<TextureRegion> frames = new Array<>();

        loadAnimation(frames,BIG_MARIO_REGION,32);
        loadAnimation(frames,LITTLE_MARIO_REGION,16);
        loadGrowingAnimation(frames);

        defineMario();
        setBounds(0,0,16,16);
        setRegion(marioStand);
    }



    private void loadGrowingAnimation(Array<TextureRegion> frames){
        frames.add(loadTextureRegion(BIG_MARIO_REGION,240,0,16,32));
        frames.add(loadTextureRegion(LITTLE_MARIO_REGION,0,0,16,16));
        frames.add(loadTextureRegion(BIG_MARIO_REGION,240,0,16,32));
        frames.add(loadTextureRegion(LITTLE_MARIO_REGION,0,0,16,16));
        growMario = new Animation(0.4f, frames);
        frames.clear();

    }

    private void loadAnimation(Array<TextureRegion> frames,String region, int height){
        for (int i = 1; i < 4; i++){
            frames.add(loadTextureRegion(region, i * 16,0,16,height));
            if(Objects.equals(region, BIG_MARIO_REGION))
              bigMarioRun = new Animation(0.1f,frames);
            else
                marioRun = new Animation(0.1f, frames);
        }
        frames.clear();
    }

    private TextureRegion loadTextureRegion(String regionName, int x, int y, int width, int height){
        return new TextureRegion(playScreen.getAtlas().findRegion(regionName),x,y,width,height);
    }

    public void update(float time){
        if (isMarioBig){
            setPosition(b2Body.getPosition().x - getWidth() / 2 , b2Body.getPosition().y - getHeight() / 2 - 6);
        }else {
            setPosition(b2Body.getPosition().x - getWidth() / 2 , b2Body.getPosition().y - getHeight() / 2);
        }
        setRegion(getFrame(time));
        if(timeToDefineBigMario){
            defineBigMario();
        }
        if(timeToRedefineMario){
            redefineMario();
        }
        if(b2Body.getPosition().y < 20){
            isMarioDead = true;
        }

    }

    private void defineMario(Vector2 position, Vector2 shapePosition){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);
        fixtureDef.filter.categoryBits = MarioBros.MARIO_BIT;
        fixtureDef.filter.maskBits = DEFAULT_BIT | COIN_BIT
                | BRICK_BIT | ENEMY_BIT
                | OBJECT_BIT  | ENEMY_HEAD_BIT
                | ITEM_BIT;
        fixtureDef.shape = shape;
        shape.setPosition(shapePosition);
        b2Body.createFixture(fixtureDef).setUserData(this);


        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2,8), new Vector2(2, 8));
        fixtureDef.filter.categoryBits = MARIO_HEAD_BIT;
        fixtureDef.shape = head;

        fixtureDef.isSensor = false;

        b2Body.createFixture(fixtureDef).setUserData(this);
    }

    private void defineMario() {
        defineMario(startPosition);
    }

    private void defineMario(Vector2 position){
        defineMario(position, new Vector2(0,0));
    }

    private void redefineMario(){
        Vector2 currentPosition = b2Body.getPosition();
        world.destroyBody(b2Body);
        defineMario(currentPosition);
        timeToRedefineMario = false;
    }

    private void defineBigMario() {
        Vector2 currentPosition = b2Body.getPosition();
        world.destroyBody(b2Body);
        defineMario(currentPosition.add(0,10), new Vector2(0,-14));
        timeToDefineBigMario = false;

    }

    public void grow(){
        runGrowAnimation = true;
        isMarioBig = true;
        timeToDefineBigMario = true;
        setBounds(getX(),getY(),getWidth(),getHeight() * 2);
        sounds.playSound(SOUND_POWER_UP);
    }

    public TextureRegion getFrame(float time){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case DEAD:
                region = marioDead;
                break;
            case GROWING:
                region = growMario.getKeyFrame(stateTimer);
                if(growMario.isAnimationFinished(stateTimer))
                    runGrowAnimation = false;
                break;
            case JUMPING:
                region = isMarioBig ? bigMarioJump : marioJump;
                break;
            case RUNNING:
                region = isMarioBig ? bigMarioRun.getKeyFrame(stateTimer,true) : marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                this.b2Body.applyForceToCenter(new Vector2(0,-100), true);
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
        else if(isMarioDead)
            return State.DEAD;
        else return State.STANDING;
    }



    public boolean isMarioBig() {
        return isMarioBig;
    }
    public boolean isMarioDead(){
        return isMarioDead;
    }
    public float getStateTimer(){
        return  stateTimer;
    }

    public void hit(){
        if(isMarioBig){
            isMarioBig = false;
            timeToRedefineMario = true;
            setBounds(getX(),getY(),getWidth(),getHeight() / 2);
            sounds.playSound(SOUND_POWER_DOWN);
        }else{
            sounds.playSound(Sounds.SOUND_MARIO_DIED);
            isMarioDead = true;
            Filter filter = new Filter();
            filter.maskBits = NOTHING_BIT;
            b2Body.getFixtureList().forEach(fixture -> fixture.setFilterData(filter));
            b2Body.applyLinearImpulse(new Vector2(0, 50f), b2Body.getWorldCenter(), true);

        }
    }
}
