package com.uki.mariobros.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.uki.mariobros.MarioBros;
import com.uki.mariobros.scene.Hud;
import com.uki.mariobros.tools.HttpClient;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.uki.mariobros.MarioBros.V_HEIGHT;
import static com.uki.mariobros.MarioBros.V_WIDTH;


public class GameOverScreen  implements Screen {
    
    private final Stage stage;
    private final Game game;
    private final Skin skin;
    private final Hud hud;

    public GameOverScreen(MarioBros game, Hud hud){
        this.game = game;
        this.hud = hud;
        stage = new Stage(new FitViewport(V_WIDTH, V_HEIGHT, new OrthographicCamera()), game.getBatch());
        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));

        skin.getFont("font").getData().setScale(0.5f);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(drawScreen(false));

    }


    private Table drawScreen(boolean displayErrorMessage){
        Table table = new Table();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", skin);
        Label scoreLabel = new Label("Your score: " + hud.getScore(), skin);
        TextButton playAgainButton = new TextButton("Play again", skin);
        TextButton scoreBoardButton = new TextButton("Scoreboard", skin);
        playAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                playAgain();

        }});

        scoreBoardButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               if(HttpClient.sendPostRequest("","")){
                   stage.clear();
                   stage.addActor(drawScreen(true));
               };
           }
        });


        table.center();
        table.add(gameOverLabel).size(120,80).colspan(2);
        table.row();
        table.add(scoreLabel).size(70,40).padTop(5);
        table.row();
        table.add(playAgainButton).size(110,30).padRight(5);
        table.add(scoreBoardButton).size(110,30);
        if(displayErrorMessage){
            table.row();
            table.add(new Label("Service unavailable", new Label.LabelStyle(new BitmapFont(),Color.RED))).colspan(2);

        }

        return table;

    }

    private void playAgain(){
            Hud.INSTANCE = null;
            game.setScreen(new TransitionScreen(game,1));
            dispose();
        }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
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
