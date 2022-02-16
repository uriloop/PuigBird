package com.mygdx.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class MainMenuScreen implements Screen {
    final Bird game;
    OrthographicCamera camera;
    Texture[] evilBird;
    Texture[] bird;
    Texture welcome;
    Texture hellBird;
    Texture fondo;
    Texture fondoLlamp;
    long timeAleteig;
    int i = 0;
    Sound tro;
    private boolean llamp = false;

    public MainMenuScreen(final Bird gam) {
        tro= Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        welcome = new Texture(Gdx.files.internal("welcomeTo.png"));
        hellBird = new Texture(Gdx.files.internal("hellBird.png"));
        evilBird = new Texture[2];
        bird = new Texture[2];
        evilBird[0] = new Texture(Gdx.files.internal("evilBird.png"));
        evilBird[1] = new Texture(Gdx.files.internal("evilBirdFlap.png"));
        bird[0] = new Texture(Gdx.files.internal("bird.png"));
        bird[1] = new Texture(Gdx.files.internal("birdFlap.png"));
        fondo = new Texture(Gdx.files.internal("fondo_menu.png"));
        fondoLlamp = new Texture(Gdx.files.internal("fondo_menu_llamp.png"));
        timeAleteig = TimeUtils.nanoTime();
    }

    long llampTime;

    @Override
    public void render(float delta) {
        if (TimeUtils.nanoTime() - timeAleteig > 50000000) {
            if (i == 0) i = 1;
            else i = 0;
            timeAleteig = TimeUtils.nanoTime();
        }


        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(fondo, 0, 0);
        if (llamp&&TimeUtils.nanoTime()-llampTime<35000000l) game.batch.draw(fondoLlamp, 0, 0);
        game.batch.draw(welcome, 240, 320);
        game.batch.draw(hellBird, 270, 240);
        game.batch.draw(bird[i], 250, 90);
        game.batch.draw(evilBird[i], 480, 90);
        /*game.font.draw(game.batch, "Welcome to Puig Bird!!! ", 300, 300);
        game.font.draw(game.batch, "Tap anywhere to begin!", 300, 180);*/
        game.batch.end();
        if (Gdx.input.justTouched() /*&& !llamp*/) {
            llamp = true;
            llampTime=TimeUtils.nanoTime();
            tro.play();
        }  else if (llamp&&TimeUtils.nanoTime()-llampTime>1000000000l) {
            game.setScreen(new GameScreen(game));
            dispose();

        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
