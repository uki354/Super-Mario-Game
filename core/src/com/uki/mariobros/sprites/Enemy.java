package com.uki.mariobros.sprites;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.uki.mariobros.screen.PlayScreen;

public abstract class Enemy  extends Sprite {

    protected World world;
    protected PlayScreen screen;

    public Body b2Body;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
    }

    protected abstract void defineEnemy();


}
