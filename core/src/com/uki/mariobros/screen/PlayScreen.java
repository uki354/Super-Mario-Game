package com.uki.mariobros.screen;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.items.Item;
import com.uki.mariobros.items.ItemDef;
import com.uki.mariobros.items.Mushroom;
import com.uki.mariobros.scene.Hud;
import com.uki.mariobros.http.HttpClient;
import com.uki.mariobros.http.HttpSender;
import com.uki.mariobros.http.User;
import com.uki.mariobros.sprites.Mario;
import com.uki.mariobros.tools.*;


import java.util.PriorityQueue;
import java.util.Queue;


import static com.badlogic.gdx.Input.Keys.*;
import static com.uki.mariobros.MarioBros.V_HEIGHT;
import static com.uki.mariobros.MarioBros.V_WIDTH;
import static com.uki.mariobros.tools.UserInputHandler.*;
import static com.uki.mariobros.tools.UserInputHandler.Side.LEFT;


public class PlayScreen  implements Screen {

    private final MarioBros game;
    private final OrthographicCamera gameCam;
    private final Viewport viewport;
    private final Hud hud;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final Mario mario;
    private final Array<Item> items;
    private final Queue<ItemDef> itemsToSpawn;
    private final World world;
    private final Box2DDebugRenderer b2dr;
    private final B2WorldCreator creator;
    private final TextureAtlas atlas;
    private final UserInputHandler inputHandler;
    private User user;
    private int currentLevel;

    public static final String TEXTURE_PACK ="Mario_And_Enimes.pack";
    public static boolean LEVEL_FINISHED = false;



    public PlayScreen(MarioBros game, int level, User user){
        this.game = game;
        this.currentLevel = level;

        atlas = new TextureAtlas(TEXTURE_PACK);

        gameCam = new OrthographicCamera();
        viewport = new FitViewport(V_WIDTH, V_HEIGHT,gameCam);
        gameCam.position.set(viewport.getWorldWidth() / 2 , viewport.getWorldHeight() / 2 , 0 );
        world = new World(new Vector2(0, -200), true);

        hud = Hud.createHud((SpriteBatch) game.getBatch());
        this.mario = new Mario(this);

        map = new TmxMapLoader().load("level" + level + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1);

        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        world.setContactListener(new WorldContactListener());
        Sounds.getInstance().playBackgroundMusic();
        items = new Array<>();
        itemsToSpawn = new PriorityQueue<>();
        inputHandler = new UserInputHandler(mario);
        this.user = user;

    }

    public void setUser(User user){
        this.user = user;
    }


    public void spawnItem(ItemDef itemDef){
        itemsToSpawn.add(itemDef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef itemDef = itemsToSpawn.poll();
            if(itemDef.type == Mushroom.class){
                items.add(new Mushroom(this,itemDef.position.x, itemDef.position.y));
            }
        }
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
        handleInput();
        handleSpawningItems();

        world.step(1/60f, 6,2);
        if(!mario.isMarioDead()) {
            if(!inputHandler.getUseMouse()) {
                gameCam.position.x = mario.b2Body.getPosition().x;
            }
        }
        if(inputHandler.getUseMouse()){
            if(mario.b2Body.getPosition().x > viewport.getWorldWidth()){
                mario.redefineMario(new Vector2(25,20));
            }else if(mario.b2Body.getPosition().x < 0){
                mario.redefineMario(new Vector2(viewport.getWorldWidth()-10,20));
            }
        }

        hud.update(time);
        mario.update(time);
        creator.getGoombas().forEach(enemy -> {
            enemy.update(time);
            if(enemy.getX() < mario.getX() + 300){
                enemy.b2Body.setActive(true);
            }
        });
        items.forEach(item -> item.update(time));
        gameCam.update();
        renderer.setView(gameCam);

    }




    private void handleInput() {
        if(!mario.isMarioDead()) {
            if (Gdx.input.isKeyPressed(UP))
                inputHandler.jump(Y_VEL);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || (inputHandler.getUseMouse() ))
                inputHandler.run(Side.RIGHT);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || inputHandler.getUseMouse() )
                inputHandler.run(LEFT);
        }



    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        b2dr.render(world, gameCam.combined);


        game.getBatch().setProjectionMatrix(gameCam.combined);
        game.getBatch().begin();

        mario.draw(game.getBatch());
        creator.getGoombas().forEach(goomba -> goomba.draw(game.getBatch()));
        items.forEach(item -> item.draw(game.getBatch()));

        game.getBatch().end();
        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game,getHud(), user));
            HttpClient httpClient = new HttpClient(new HttpSender());
            System.out.println(hud.getTotalScore());
            httpClient.saveScore(user,hud.getTotalScore());
            dispose();
        }
        if(LEVEL_FINISHED){
            LEVEL_FINISHED = false;
            levelUp();
        }

    }

    public void removeHud(){
        hud.getStage().clear();
        Hud.INSTANCE = null;
    }

    public Hud getHud(){
        return this.hud;
    }

    public UserInputHandler getInputHandler(){
        return inputHandler;
    }
    public Viewport getViewport(){
        return this.viewport;
    }

//    public void saveScore(Integer totalScore){
//        HttpClient.sendPostRequest(URL, totalScore.toString());
//    }

    private void levelUp(){
        currentLevel += 1;
        game.setScreen(new TransitionScreen(game,currentLevel, user));
        hud.updateLevelLabel((currentLevel));
    }

    public boolean gameOver(){
        return mario.isMarioDead() && mario.getStateTimer() > 3;
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
