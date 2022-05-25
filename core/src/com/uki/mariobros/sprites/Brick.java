package com.uki.mariobros.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.scene.Hud;
import com.uki.mariobros.screen.PlayScreen;
import com.uki.mariobros.tools.Sounds;

import static com.uki.mariobros.tools.Sounds.SOUND_BREAK;

public class Brick extends  InteractiveTileObject {

    public Brick(PlayScreen screen, Rectangle bounds){
        super(screen,bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        setCategoryFilter(MarioBros.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
        Sounds.getInstance().playSound(SOUND_BREAK);
    }
}
