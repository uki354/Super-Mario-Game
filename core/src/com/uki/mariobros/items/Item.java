package com.uki.mariobros.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.uki.mariobros.screen.PlayScreen;
import com.uki.mariobros.sprites.Mario;

public abstract  class Item  extends Sprite {

    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean isDestroyed;
    protected Body body;


    public Item(PlayScreen screen, float x , float y){
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x,y);
        setBounds(getX(),getY(),16,16);
        defineItem();
        toDestroy = false;
        isDestroyed = false;

    }

    public abstract  void defineItem();
    public abstract  void useItem(Mario mario);

    public void update(float dt){
        if(toDestroy && !isDestroyed) {
            world.destroyBody(body);
            isDestroyed = true;
        }

    }

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x *= -1;
        if (y)
            velocity.y *= -1;
    }

    public void destroy(){
        toDestroy = true;
    }





    @Override
    public void draw(Batch batch){
        if(!isDestroyed)
            super.draw(batch);
    }

}
