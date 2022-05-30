package com.uki.mariobros.sprites;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.items.ItemDef;
import com.uki.mariobros.items.Mushroom;
import com.uki.mariobros.scene.Hud;
import com.uki.mariobros.screen.PlayScreen;
import com.uki.mariobros.tools.Sounds;

import static com.uki.mariobros.tools.Sounds.SOUND_BUMP;
import static com.uki.mariobros.tools.Sounds.SOUND_COIN;

public class Coin extends  InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    private static final int BLANK_COIN  = 28;
    private static final  int COIN_VALUE = 300;


    public Coin(PlayScreen screen, MapObject object){
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.COIN_BIT);

    }

    @Override
    public void onHeadHit() {
        if(getCell().getTile().getId() == BLANK_COIN)
            Sounds.getInstance().playSound(SOUND_BUMP);
        else {
            Sounds.getInstance().playSound(SOUND_COIN);
            Hud.addScore(COIN_VALUE);
            if(object.getProperties().containsKey("mushroom"))
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16), Mushroom.class));

        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));

    }
}
