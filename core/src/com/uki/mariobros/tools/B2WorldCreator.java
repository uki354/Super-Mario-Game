package com.uki.mariobros.tools;



import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import com.uki.mariobros.screen.PlayScreen;
import com.uki.mariobros.sprites.Brick;
import com.uki.mariobros.sprites.Coin;
import com.uki.mariobros.sprites.Goomba;
import com.uki.mariobros.sprites.InteractiveTileObject;

import static com.uki.mariobros.MarioBros.DEFAULT_BIT;
import static com.uki.mariobros.MarioBros.OBJECT_BIT;


public class B2WorldCreator {

    private final Array<Goomba> goombas;

    public static final int GROUND_LAYER = 2;
    public static final int PIPE_LAYER = 3;
    public static final int COIN_LAYER = 4;
    public static final int BRICK_LAYER = 5;
    public static final int GOOMBA_LAYER = 6;

    private final BodyDef bDef;
    private final PolygonShape shape;
    private final FixtureDef fDef;
    private final TiledMap map;
    private final World world;
    private Body body;




    public B2WorldCreator(PlayScreen screen){
        bDef = new BodyDef();
        shape = new PolygonShape();
        fDef = new FixtureDef();
        map = screen.getMap();
        world = screen.getWorld();
        goombas = new Array<>();

        createStaticObject(GROUND_LAYER,DEFAULT_BIT);
        createStaticObject(PIPE_LAYER, OBJECT_BIT);

        createMutableObjects(COIN_LAYER, screen);
        createMutableObjects(BRICK_LAYER, screen);

        createEnemies(GOOMBA_LAYER, screen);
    }

    private void createEnemies(int layer, PlayScreen screen){
        map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class).forEach(rectangleMapObject -> {
            Rectangle  rect = rectangleMapObject.getRectangle();
            goombas.add(new Goomba(screen, rect.getX(), rect.getY()));
        });
    }

    private void createMutableObjects(int layer, PlayScreen screen){
        map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class).forEach(x -> {
            InteractiveTileObject obj = layer == BRICK_LAYER ? new Brick(screen, x) : new Coin(screen,x);
        });
    }


    private void createStaticObject(int layer, short categoryBit){
        map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class).forEach(rectangleMapObject -> {
            Rectangle rect = rectangleMapObject.getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rect.getX() + rect.getWidth() / 2,rect.getY() + rect.getHeight()  / 2 );

            body = world.createBody(bDef);
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() /2);
            fDef.shape = shape;
            fDef.filter.categoryBits = categoryBit;

            body.createFixture(fDef);
        });
    }

    public Array<Goomba> getGoombas(){
        return goombas;
    }
}
