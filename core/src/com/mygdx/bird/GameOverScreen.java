package com.mygdx.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import org.w3c.dom.Text;

public class GameOverScreen implements Screen {
    final Bird game;
    OrthographicCamera camera;
    Texture bestScore;
    Texture yourScore;
    Texture gameOver;
    Texture[] bird;
    private long timeAleteig;
    Music musica;

    public GameOverScreen(final Bird gam) {
        bird = new Texture[2];
        bird[0] = new Texture(Gdx.files.internal("evilBirdUll.png"));
        bird[1] = new Texture(Gdx.files.internal("evilBirdFlapUll.png"));
        musica = Gdx.audio.newMusic(Gdx.files.internal("03.mp3"));
        musica.setVolume(9.0f);
        bestScore = new Texture(Gdx.files.internal("bestScore.png"));
        yourScore = new Texture(Gdx.files.internal("yourScore.png"));
        gameOver = new Texture(Gdx.files.internal("game_over.png"));
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        timeAleteig = TimeUtils.nanoTime();
    }

    int i = 0;
    int x = 810;

    @Override
    public void render(float delta) {
        musica.play();

        if (TimeUtils.nanoTime() - timeAleteig > 50000000) {
            if (i == 0) i = 1;
            else i = 0;
            timeAleteig = TimeUtils.nanoTime();
        }
        x -= 2;
        if (x < -70) x = 810;
        ScreenUtils.clear(0.2f, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(bird[i], x, 50);
        game.batch.draw(bestScore, 140, 150);
        game.batch.draw(yourScore, 120, 240);
        game.font.draw(game.batch, String.valueOf(game.topScore),590,170);
        game.font.draw(game.batch, String.valueOf(game.score),590,260);
        game.batch.draw(gameOver, 40, 350);

        game.batch.end();

        if (Gdx.input.justTouched()) {
            musica.stop();
            game.menuMusic.play();

            if (game.score > game.topScore) game.topScore = game.score;
            game.lastScore = game.score;
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