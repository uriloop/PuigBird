package com.mygdx.bird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;




public class Bird extends Game {

    SpriteBatch batch;
    BitmapFont font;
    int topScore;
    int lastScore;
    int score;
    Music menuMusic;

    public void create() {
        menuMusic=Gdx.audio.newMusic(Gdx.files.internal("menuMusic.mp3"));
        batch = new SpriteBatch();
// Use LibGDX's default Arial font.
        font = new BitmapFont();

        menuMusic.play();
        menuMusic.setLooping(true);
        this.setScreen(new MainMenuScreen(this));
        topScore = 0;
        lastScore = 0;
        score = 0;
    }

    public void render() {
        super.render(); // important!

    }

    public void dispose() {
    }
}

