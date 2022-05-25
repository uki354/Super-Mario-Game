package com.uki.mariobros.screen;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.scene.Hud;
import com.uki.mariobros.sprites.Goomba;
import com.uki.mariobros.sprites.Mario;
import com.uki.mariobros.tools.B2WorldCreator;
import com.uki.mariobros.tools.Sounds;
import com.uki.mariobros.tools.WorldContactListener;

public class PlayScreen  implements Screen {

    private MarioBros game;
    private OrthographicCamera gameCam;
    private Viewport viewport;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Mario mario;
    private Music music;
    private Goomba goomba;

    private World world;
    private Box2DDebugRenderer b2dr;
    private TextureAtlas atlas;




    public PlayScreen(MarioBros game){
        atlas = new TextureAtlas("Mario_And_Enimes.pack");
        this.game = game;
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM,MarioBros.V_HEIGHT / MarioBros.PPM ,gameCam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);
        gameCam.position.set(viewport.getWorldWidth() / 2 , viewport.getWorldHeight() / 2 , 0 );

        world = new World(new Vector2(0, -500), true);
        this.mario = new Mario(this);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(this);

        world.setContactListener(new WorldContactListener());
        Sounds.getInstance().playBackgroundMusic();

        goomba = new Goomba(this,.32f,.32f);

    }



    public TextureAtlas getAtlas(){
        return atlas;
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void show() {

    }

    public void update(float time){        
        handleInput(time);

        world.step(1/90f, 50,2);
        gameCam.position.x = mario.b2Body.getPosition().x;

        hud.update(time);
        mario.update(time);
        goomba.update(time);
        gameCam.update();
        renderer.setView(gameCam);

    }

    private void handleInput(float time) {
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            mario.b2Body.applyLinearImpulse(new Vector2(0,150f), mario.b2Body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.b2Body.getLinearVelocity().x <= 85 && mario.b2Body.getLinearVelocity().y > -15)
            mario.b2Body.applyLinearImpulse(new Vector2(80.0f, 0), mario.b2Body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && mario.b2Body.getLinearVelocity().x >= -85 && mario.b2Body.getLinearVelocity().y > -15)
            mario.b2Body.applyLinearImpulse(new Vector2(-80.0f,0), mario.b2Body.getWorldCenter(), true);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gameCam.combined);


        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        mario.draw(game.batch);
        goomba.draw(game.batch);
        game.batch.end();
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
