package com.uki.mariobros.tools;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.screen.PlayScreen;
import com.uki.mariobros.sprites.Brick;
import com.uki.mariobros.sprites.Coin;

import static com.uki.mariobros.MarioBros.OBJECT_BIT;


public class B2WorldCreator {

    public B2WorldCreator(PlayScreen screen){

        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        TiledMap map = screen.getMap() ;
        World world = screen.getWorld();
        Body body;


        //Refactor for loops


        for (RectangleMapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = object.getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight()  / 2 ) / MarioBros.PPM);

            body = world.createBody(bDef);
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() /2);
            fDef.shape = shape;
            body.createFixture(fDef);

        }

        for (RectangleMapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle  rect = object.getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight()  / 2 ) / MarioBros.PPM);

            body = world.createBody(bDef);
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() /2);
            fDef.shape = shape;
            fDef.filter.categoryBits = OBJECT_BIT;
            body.createFixture(fDef);

        }

        for (RectangleMapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle  rect = object.getRectangle();
            new Brick(screen,rect);
        }


        for (RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle  rect = object.getRectangle();
            new Coin(screen, rect);
        }

    }
}
