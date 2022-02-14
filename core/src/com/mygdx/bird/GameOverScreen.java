package com.mygdx.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
    final Bird game;
    OrthographicCamera camera;
    public GameOverScreen(final Bird gam) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Game Over! ", 300, 300);
        game.font.draw(game.batch, "Final Score: " + game.lastScore, 300, 180);
        game.batch.end();
        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
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