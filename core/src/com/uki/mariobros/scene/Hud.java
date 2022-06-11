package com.uki.mariobros.scene;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.uki.mariobros.MarioBros.*;

public class Hud  implements Disposable {

    private final Stage stage;

    private float timeCount;
    private Integer worldTimer;
    private Label countdownLabel;

    private static  Integer score;
    private static  Label  scoreLabel;
    private  Label levelLabel;

    private static final int GAME_SECONDS = 300;

    private static Hud INSTANCE;

    public static Hud createHud(SpriteBatch batch){
        if(INSTANCE == null){
           INSTANCE = new Hud(batch);
        }else return INSTANCE;
        return INSTANCE;

    }



    private Hud(SpriteBatch batch){
        worldTimer = GAME_SECONDS;
        timeCount = 0;
        score = 0;

        stage =  new Stage(new FitViewport(V_WIDTH, V_HEIGHT, new OrthographicCamera()), batch);

        stage.addActor(drawHud());
    }

    private Table drawHud(){
        Table table = new Table();
        table.top();
        table.setFillParent(true);

//        countdownLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        countdownLabel = new Label(worldTimer.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(score.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
        return table;
    }

    public void updateLevelLabel(int level){
//        levelLabel.setText(String.format("1-%01d",level));
        levelLabel.setText("1-" + level);
    }

    public void update(float time){
        timeCount += time;
        if(timeCount >= 1){
            worldTimer--;
//            countdownLabel.setText(String.format("%03d", worldTimer));
            countdownLabel.setText(worldTimer.toString());
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
//        scoreLabel.setText(String.format("%06d", score));
        scoreLabel.setText(score.toString());

    }

    public Stage getStage(){
        return stage;
    }



    @Override
    public void dispose() {
        stage.dispose();
    }
}
