package com.uki.mariobros.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;


public class Sounds implements Disposable {

    private static Sounds INSTANCE;

    private final AssetManager manager;

    public static final String SOUND_MUSIC = "audio/music/mario_music.ogg";
    public static final String SOUND_COIN =  "audio/sounds/coin.wav";
    public static final String SOUND_BUMP = "audio/sounds/bump.wav";
    public static final String SOUND_BREAK = "audio/sounds/breakblock.wav";

    private Sounds(){
        manager = new AssetManager();
        loadSoundAssets();
    }

    public static Sounds getInstance(){
        return INSTANCE == null ? new Sounds() : INSTANCE;
    }

    private void loadSoundAssets(){
        manager.load(SOUND_MUSIC, Music.class);
        manager.load(SOUND_COIN, com.badlogic.gdx.audio.Sound.class);
        manager.load(SOUND_BUMP, com.badlogic.gdx.audio.Sound.class);
        manager.load(SOUND_BREAK, com.badlogic.gdx.audio.Sound.class);
        manager.finishLoading();
    }

    public void playBackgroundMusic(){
        Music music = manager.get(SOUND_MUSIC, Music.class);
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    public void playSound(String soundPath){
        manager.get(soundPath, com.badlogic.gdx.audio.Sound.class).play();
    }

    @Override
    public void dispose() {
        manager.dispose();
    }














}
