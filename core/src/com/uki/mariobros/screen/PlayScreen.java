package com.uki.mariobros.screen;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.scene.Hud;
import com.uki.mariobros.sprites.Mario;

public class PlayScreen  implements Screen {

    private MarioBros game;
    private OrthographicCamera gameCam;
    private Viewport viewport;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Mario mario;

    private World world;
    private Box2DDebugRenderer b2dr;




    public PlayScreen(MarioBros game){
        this.game = game;
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM,MarioBros.V_HEIGHT / MarioBros.PPM ,gameCam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);
        gameCam.position.set(viewport.getWorldWidth() / 2 , viewport.getWorldHeight() / 2 , 0 );

        world = new World(new Vector2(0, -500), true);
        this.mario = new Mario(world);
        b2dr = new Box2DDebugRenderer();


        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        for (MapObject  object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle  rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight()  / 2 ) / MarioBros.PPM);

            body = world.createBody(bDef);
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() /2);
            fDef.shape = shape;
            body.createFixture(fDef);

        }

        for (MapObject  object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle  rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight()  / 2 ) / MarioBros.PPM);

            body = world.createBody(bDef);
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() /2);
            fDef.shape = shape;
            body.createFixture(fDef);

        }

        for (MapObject  object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle  rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight()  / 2 ) / MarioBros.PPM);

            body = world.createBody(bDef);
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() /2);
            fDef.shape = shape;
            body.createFixture(fDef);

        }

        for (MapObject  object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle  rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight()  / 2 );

            body = world.createBody(bDef);
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() /2);
            fDef.shape = shape;
            body.createFixture(fDef);

        }



    }

    @Override
    public void show() {

    }

    public void update(float time){        
        handleInput(time);

        world.step(1/90f, 50,2);
        gameCam.position.x = mario.b2Body.getPosition().x;


        gameCam.update();
        renderer.setView(gameCam);

    }

    private void handleInput(float time) {

        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            mario.b2Body.applyLinearImpulse(new Vector2(0,150f), mario.b2Body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.b2Body.getLinearVelocity().x <= 300)
            mario.b2Body.applyLinearImpulse(new Vector2(60.0f,0), mario.b2Body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && mario.b2Body.getLinearVelocity().x >= -300)
            mario.b2Body.applyLinearImpulse(new Vector2(-60.0f,0), mario.b2Body.getWorldCenter(), true);


    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
