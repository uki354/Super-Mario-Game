package com.uki.mariobros.sprites;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.items.ItemDef;
import com.uki.mariobros.items.Mushroom;
import com.uki.mariobros.scene.Hud;
import com.uki.mariobros.screen.PlayScreen;
import com.uki.mariobros.tools.Sounds;

import static com.uki.mariobros.tools.Sounds.*;

public class Coin extends  InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    private final Sounds sounds;

    private static final int BLANK_COIN  = 28;
    private static final int COIN_VALUE = 300;
    public static final String MUSHROOM_KEY = "mushroom";
    public static final String TILE_SET = "tileset_gutter";


    public Coin(PlayScreen screen, MapObject object){
        super(screen, object);
        tileSet = map.getTileSets().getTileSet(TILE_SET);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.COIN_BIT);
        sounds = Sounds.getInstance();

    }

    @Override
    public void onHeadHit(Mario mario) {
        if(getCell().getTile().getId() == BLANK_COIN)
            sounds.playSound(SOUND_BUMP);
        else {
            sounds.playSound(SOUND_COIN);
            Hud.addScore(COIN_VALUE);
            if(object.getProperties().containsKey(MUSHROOM_KEY)) {
                screen.spawnItem(
                        new ItemDef(
                                new Vector2(body.getPosition().x, body.getPosition().y + 16), Mushroom.class));

                sounds.playSound(SOUND_POWER_UP_SPAWN);
            }
            getCell().setTile(tileSet.getTile(BLANK_COIN));
        }


    }
}
