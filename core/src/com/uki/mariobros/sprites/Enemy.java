package com.uki.mariobros.sprites;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.uki.mariobros.screen.PlayScreen;

public abstract class Enemy  extends Sprite {

    protected World world;
    protected PlayScreen screen;
    public Vector2 velocity;

    public Body b2Body;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
        velocity = new Vector2(50,0);
    }

    protected abstract void defineEnemy();
    public abstract void onHeadHit();
    public abstract void update(float dt);

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x *= -1;
        if (y)
            velocity.y *= -1;
    }




}
