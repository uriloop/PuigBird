package com.mygdx.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    final Bird game;
    Flappy flappy;
    Lava lava;
    OrthographicCamera camera;
    Music gameOver;
    Texture pauseT;
    Rectangle pause;
    float speedy;
    float speedyEvil;
    float gravity;
    float score;
    Texture falconImage;
    Rectangle falcon;
    boolean evilBird;
    Obstacles obstacles;
    long obstacleTime;
    Music backgroundMusic;


    float xVelocity;
    Background background = new Background();
    private long timeEsCrema = 0;
    private boolean jocPausat;

    public GameScreen(final Bird gam) {
        gameOver = Gdx.audio.newMusic(Gdx.files.internal("gameOverMusic.mp3"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        pauseT = new Texture(Gdx.files.internal("pause.png"));
        evilBird = false;
        falconImage = new Texture(Gdx.files.internal("evilBird.png"));
        falcon = new Rectangle();
        falcon.width = 64;
        falcon.height = 45;
        this.game = gam;
        game.menuMusic.stop();
        flappy = new Flappy();
        speedy = 0;
        gravity = 850f;
        xVelocity = -30;
        lava = new Lava();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        obstacleTime = TimeUtils.nanoTime();
        obstacles = new Obstacles();
        score = 0;
        pause = new Rectangle();
        pause.y = 375;
        pause.x = 700;
        pause.height = 64;
        pause.width = 64;
        backgroundMusic.setLooping(true);

    }


    private void spawnEvilBird() {
        // Calcula la alçada de l'obstacle aleatòriament
        evilBird = true;
        // Crea dos obstacles: Una tubería superior i una inferior
        speedyEvil = 0;
        falcon.x = 800 + (64 / 2);
        falcon.y = ((int) Math.random() * 300) + 100;

    }


    boolean haComencatMusica = false;

    private void update() {
backgroundMusic.setVolume(0.8f);
        if (!backgroundMusic.isLooping()) backgroundMusic.setLooping(true);
        if (!haComencatMusica) {
            backgroundMusic.play();
            haComencatMusica = true;
        }
        if (jocPausat) {
            /// *****
            if (Gdx.input.justTouched()) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                // Ara touchPos conté les coordenades a on l'usuari ha tocat en x,y

                if (pause.contains(touchPos.x, touchPos.y)) {
                    // Akí el que sigui que faci que pari
                    pause();

                }
            }
        } else {
            if (flappy.dead) {
                backgroundMusic.stop();
                gameOver.play();
            }
            if (Gdx.input.justTouched()) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                // Ara touchPos conté les coordenades a on l'usuari ha tocat en x,y

                if (pause.contains(touchPos.x, touchPos.y)) {
                    // Akí el que sigui que faci que pari
                    pause();

                }
            }


            background.update();
            lava.update();
            obstacles.update(xVelocity, obstacleTime);

            speedy = flappy.update(speedy, gravity, xVelocity);
        /*if (Gdx.input.isTouched()) {
            flappy.planeja();
            speedy = 200;
        }
*/
            if (flappy.restart) {
                game.score = (int) score;
                game.setScreen(new GameOverScreen(game));

                dispose();
            }

            if (!flappy.dead) {
                score += Gdx.graphics.getDeltaTime();

            }
            //La puntuació augmenta amb el temps de joc

            flappy.setEnergiaPlaneig(1);
            if (flappy.box.overlaps(falcon)) {
                flappy.dead = true;
                /*timeFromDeath = TimeUtils.nanoTime();*/
            }
            for (Rectangle box :
                    obstacles.obstacles) {
                if (box.overlaps(flappy.box)) {
                    flappy.dead = true;
                }
            }
            for (Obstacles.LavaCascada lava :
                    obstacles.lavaList) {
                if (lava.box.overlaps(flappy.box)) flappy.dead = true;
            }
            if (flappy.box.y < -62) flappy.dead = true;
            if (flappy.box.y < 25) {
                timeEsCrema = TimeUtils.nanoTime();
            }
            if (TimeUtils.nanoTime() - timeEsCrema < 5000000000l) {
                flappy.esCrema();
            } else {
                timeEsCrema = 0;
            }
            if (evilBird) {
                falcon.x -= 200 * Gdx.graphics.getDeltaTime();
            }
///////////
            // Moviment de l'ocell enemic
            if (evilBird) {
                falcon.y += speedyEvil * Gdx.graphics.getDeltaTime();
                speedyEvil -= gravity * Gdx.graphics.getDeltaTime();
            }

            // Comprova si cal generar un obstacle nou
            if (TimeUtils.nanoTime() - obstacleTime > 1500000000 && evilBird) {
                obstacles.spawnObstaclePipes();
                obstacleTime = TimeUtils.nanoTime();
            } else if (TimeUtils.nanoTime() - obstacleTime > 1500000000 && !evilBird) {
                double random = Math.random();
                if (random > 0.8) {
                    obstacles.spawnObstacleLava();
                } else if (random > 0.6) {
                    spawnEvilBird();
                } else {
                    obstacles.spawnObstaclePipes();
                }
                obstacleTime = TimeUtils.nanoTime();

            }
            if (falcon.x < -64) evilBird = false;
            if (falcon.y < 25 && evilBird) {
                speedyEvil = 400f;
            } else if (falcon.y < 400f && Math.random() > 0.95 && evilBird) {
                speedyEvil = 400f;
            }
        }
        ///////////
        camera.update();
    }


    @Override
    public void render(float delta) {

        update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        ScreenUtils.clear(0.2f, 0, 0, 1);

        background.render(game.batch);
        flappy.render(game.batch, jocPausat);
        obstacles.render(game.batch);
        lava.render(game.batch);
        if (evilBird) {
            game.batch.draw(falconImage, falcon.x, falcon.y);
        }
        game.font.draw(game.batch, "Score: " + (int) score, 10, 470);
        game.font.draw(game.batch, "Energy: " + (int) flappy.getEnergiaPlaneig(), 100, 470);
        game.batch.draw(pauseT, pause.x, pause.y);
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
        jocPausat = !jocPausat;
        if (jocPausat) {
            backgroundMusic.pause();
            game.menuMusic.play();
        } else {
            game.menuMusic.stop();
            backgroundMusic.play();
        }
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        backgroundMusic.dispose();
        background.dispose();
        obstacles.dispose();
        flappy.dispose();
        lava.dispose();
        pauseT.dispose();
        falconImage.dispose();

    }
}