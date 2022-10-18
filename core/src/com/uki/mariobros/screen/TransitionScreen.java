package com.uki.mariobros.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.security.User;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.uki.mariobros.MarioBros.V_HEIGHT;
import static com.uki.mariobros.MarioBros.V_WIDTH;

public class TransitionScreen implements Screen {

    private final Stage stage;
    private final Game game;
    private final int level;
    private float time;
    private User user;


    public TransitionScreen(Game game, int level, User user){
        this.game = game;
        this.stage = new Stage(new FitViewport(V_WIDTH, V_HEIGHT,new OrthographicCamera()));
        this.level = level;
        this.user = user;

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        Label label = new Label("Level " + this.level, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label.setSize(100,40);
        table.add(label);
        stage.addActor(table);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        time+= delta;
        if(time > 1){
            game.setScreen(new PlayScreen((MarioBros) game, level, user));
        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();

    }
}
