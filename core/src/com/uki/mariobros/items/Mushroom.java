package com.uki.mariobros.items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.uki.mariobros.screen.PlayScreen;

public class Mushroom extends  Item implements Comparable<Mushroom> {


    public Mushroom(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("mushroom"), 0, 0,16,16);
        velocity = new Vector2(0,0);

    }

    @Override
    public void defineItem() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);

    }

    @Override
    public void useItem() {
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
//        setCenter(b2body.getPosition().x,b2body.getPosition().y);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        body.setLinearVelocity(velocity);

    }

    @Override
    public int compareTo(Mushroom mushroom) {
        return 1;
    }
}
