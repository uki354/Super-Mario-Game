package com.uki.mariobros.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.uki.mariobros.MarioBros;

public class Brick extends  InteractiveTileObject {

    public Brick(World world, TiledMap map, Rectangle bounds){
        super(world,map,bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        System.out.println("BRICK");
        setCategoryFilter(MarioBros.DESTROYED_BIT);
        getCell().setTile(null);
    }
}
