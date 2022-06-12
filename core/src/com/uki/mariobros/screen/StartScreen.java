package com.uki.mariobros.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;


import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.security.Auth;
import com.uki.mariobros.security.User;




import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.uki.mariobros.MarioBros.V_HEIGHT;
import static com.uki.mariobros.MarioBros.V_WIDTH;

public class StartScreen implements Screen {


    private final Stage stage;
    private final Game game;
    private final SpriteBatch batch;
    private final Skin skin;

    public StartScreen(MarioBros game){
        this.game = game;
        this.batch = (SpriteBatch) game.getBatch();
        this.stage = new Stage(new FitViewport(V_WIDTH, V_HEIGHT, new OrthographicCamera()), game.getBatch());
        this.skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
        skin.getFont("font").getData().setScale(0.5f);
        skin.get("default", TextButton.TextButtonStyle.class).font.getData().setScale(0.4f);
        Gdx.input.setInputProcessor(stage);



        stage.addActor(drawScreen(false));    }


    public void showErrorMessage(){
        System.out.println("here");
        stage.clear();
        stage.addActor(drawScreen(true));
    }

    public Actor drawScreen(boolean error){
        Table table = new Table();
        table.center();
        table.setFillParent(true);
        Label usernameLabel = new Label("username",skin);
        Label passwordLabel = new Label("password", skin);
        TextField usernameField = new TextField("",skin);
        TextField passwordField = new TextField("", skin);
        TextButton guestButton = new TextButton("Play as guest", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        guestButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TransitionScreen(game,1));
            }
        });

        TextButton login = new TextButton("login", skin);
        login.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Auth auth = new Auth();
                if(auth.authenticate(new User(usernameField.getText(), passwordField.getText())))
                    showErrorMessage();
            }
        });
        guestButton.setSize(100,100);

        if(error){
            table.add(new Label("Service unavailable", new Label.LabelStyle(new BitmapFont(), Color.RED)));
            table.row();
        }

        table.add(usernameLabel);
        table.row();
        table.add(usernameField).size(120,25);
        table.add(guestButton).padLeft(20).size(100,25).padTop(10);
        table.row();
        table.add(passwordLabel);
        table.row();
        table.add(passwordField).size(120,25);
        table.row();
        table.add(login).size(80,25).padTop(20);

        return table;

    }









    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(new Texture("start.jpg"),80,0);
        batch.end();
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
