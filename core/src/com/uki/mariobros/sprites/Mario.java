package com.uki.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.screen.PlayScreen;

public class Mario extends Sprite {

    public World world;
    public Body b2Body;
    private TextureRegion marioStand;



    public Mario(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        defineMario();
        marioStand = new TextureRegion(getTexture(),1,11,16,16);
        setBounds(0,0,16,16);
        setRegion(marioStand);
    }

    public void update(float time){
        setPosition(b2Body.getPosition().x - getWidth() / 2 , b2Body.getPosition().y - getHeight() / 2);

    }

    private void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / MarioBros.PPM, 32 / MarioBros.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / MarioBros.PPM);

        fixtureDef.shape = shape;
        b2Body.createFixture(fixtureDef);

    }
}
