package com.mygdx.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final Bird game;
    Flappy flappy;
    Lava lava;
    OrthographicCamera camera;
    Texture birdImage;
    Rectangle player;
    float speedy;
    float gravity;
    float xVelocity;
    Background background = new Background();

    public GameScreen(final Bird gam) {
        this.game = gam;
        flappy = new Flappy();
        speedy = 0;
        gravity = 850f;
        xVelocity = -30;
        lava = new Lava();
// load the images


// create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        background.update();
        lava.update();

        if (Gdx.input.isTouched()){
            flappy.planeja();
            gravity=600f;
        }

        speedy = flappy.update(speedy, gravity, xVelocity);

// tell the camera to update its matrices.
        camera.update();
// tell the SpriteBatch to render in the
// coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
// begin a new batch
        game.batch.begin();
        background.render(game.batch);
        lava.render(game.batch);
        flappy.render(game.batch);
        game.batch.end();
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