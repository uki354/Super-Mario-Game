package com.uki.mariobros.screen;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.scene.Hud;

public class PlayScreen  implements Screen {


    private MarioBros game;
    private OrthographicCamera gameCam;
    private Viewport viewport;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;



    public PlayScreen(MarioBros game){
        this.game = game;
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(MarioBros.V_WIDTH,MarioBros.V_HEIGHT,gameCam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(viewport.getWorldWidth() / 2 , viewport.getWorldHeight() / 2 , 0 );

    }

    @Override
    public void show() {
    }

    public void update(float time){        
        handleInput(time);

        gameCam.update();
        renderer.setView(gameCam);
        
    }

    private void handleInput(float time) {

        if( Gdx.input.isTouched())
            gameCam.position.x += 100 * time;


    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

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
