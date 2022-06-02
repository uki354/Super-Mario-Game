package com.uki.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.screen.PlayScreen;

import static com.uki.mariobros.MarioBros.*;

public class Goomba extends  Enemy {

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean isDestroyed;



    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setToDestroy = false;
        isDestroyed = false;
        frames = new Array<>();
        for (int i = 0;  i < 2; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 1, 16,16));
            walkAnimation = new Animation<TextureRegion>(0.4f,frames);
            stateTime = 0;
            setBounds(getX(),getY(),16,16);
        }
    }

    public void update(float time){
        stateTime += time;
        
        if(setToDestroy &&  !isDestroyed){
            world.destroyBody(b2Body);
            isDestroyed = true;
            setBounds(getX(),getY(),16,8);
//            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32,1,16,16));
            stateTime = 0;
        }else {
            b2Body.setLinearVelocity(velocity);
            setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);
        fixtureDef.filter.categoryBits = MarioBros.ENEMY_BIT;
        fixtureDef.filter.maskBits = DEFAULT_BIT | COIN_BIT | BRICK_BIT | ENEMY_BIT | OBJECT_BIT | MARIO_BIT;

        fixtureDef.shape = shape;
        b2Body.createFixture(fixtureDef).setUserData(this);

        PolygonShape polygonShape = new PolygonShape();
        Vector2[] vector2s = new Vector2[4];
        vector2s[0] = new Vector2(-3,8);
        vector2s[1] = new Vector2(3,8);
        vector2s[2] = new Vector2(-3,3);
        vector2s[3] = new Vector2(3,3);
        polygonShape.set(vector2s);

        fixtureDef.shape = polygonShape;
        fixtureDef.restitution = 1.5f;
        fixtureDef.filter.categoryBits = ENEMY_HEAD_BIT;
        b2Body.createFixture(fixtureDef).setUserData(this);

    }

    @Override
    public void draw(Batch batch){
        if (!isDestroyed || stateTime < 1){
            super.draw(batch);
        }
    }

    @Override
    public void onHeadHit() {
        setToDestroy = true;

    }
}
