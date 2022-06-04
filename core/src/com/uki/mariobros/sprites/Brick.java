package com.uki.mariobros.sprites;

import com.badlogic.gdx.maps.MapObject;
import com.uki.mariobros.MarioBros;
import com.uki.mariobros.scene.Hud;
import com.uki.mariobros.screen.PlayScreen;
import com.uki.mariobros.tools.Sounds;

import static com.uki.mariobros.tools.Sounds.SOUND_BREAK;
import static com.uki.mariobros.tools.Sounds.SOUND_BUMP;

public class Brick extends  InteractiveTileObject {

    private final Sounds sounds;
    public static final int BRICK_VALUE = 200;

    public Brick(PlayScreen screen, MapObject object){
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
        sounds = Sounds.getInstance();
    }

    @Override
    public void onHeadHit(Mario mario) {
        if (!mario.isMarioBig()){
            sounds.playSound(SOUND_BUMP);
        }else {
            setCategoryFilter(MarioBros.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(BRICK_VALUE);
            Sounds.getInstance().playSound(SOUND_BREAK);
        }
    }
}
