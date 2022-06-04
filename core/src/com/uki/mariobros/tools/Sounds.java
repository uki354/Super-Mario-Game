package com.uki.mariobros.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;


public class Sounds implements Disposable {

    private static Sounds INSTANCE;

    private final AssetManager manager;

    public static final String SOUND_MUSIC = "audio/music/mario_music.ogg";
    public static final String SOUND_COIN =  "audio/sounds/coin.wav";
    public static final String SOUND_BUMP = "audio/sounds/bump.wav";
    public static final String SOUND_BREAK = "audio/sounds/breakblock.wav";
    public static final String SOUND_STOMP = "audio/sounds/stomp.wav";
    public static final String SOUND_POWER_DOWN = "audio/sounds/powerdown.wav";
    public static final String SOUND_POWER_UP = "audio/sounds/powerup.wav";
    public static final String SOUND_POWER_UP_SPAWN = "audio/sounds/powerup_spawn.wav";
    public static final String SOUND_MARIO_DIED = "audio/sounds/mariodie.wav";


    private Sounds(){
        manager = new AssetManager();
        loadSoundAssets();
    }

    public static Sounds getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Sounds();
        }
        return INSTANCE;
    }

    private void loadSoundAssets(){
        manager.load(SOUND_MUSIC, Music.class);
        manager.load(SOUND_COIN, Sound.class);
        manager.load(SOUND_BUMP, Sound.class);
        manager.load(SOUND_BREAK,Sound.class);
        manager.load(SOUND_STOMP, Sound.class);
        manager.load(SOUND_POWER_DOWN, Sound.class);
        manager.load(SOUND_POWER_UP_SPAWN, Sound.class);
        manager.load(SOUND_POWER_UP, Sound.class);
        manager.load(SOUND_MARIO_DIED, Sound.class);
        manager.finishLoading();
    }

    public void playBackgroundMusic(){
        Music music = manager.get(SOUND_MUSIC, Music.class);
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    public void playSound(String soundPath){
        manager.get(soundPath, Sound.class).play();
    }

    @Override
    public void dispose() {
        manager.dispose();
    }














}
