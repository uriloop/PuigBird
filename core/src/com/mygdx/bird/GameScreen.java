package com.mygdx.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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

    float speedy;
    float speedyEvil;
    float gravity;
    boolean dead;
    Texture pipeUpImage;
    Texture pipeDownImage;
    Array<Rectangle> obstacles;
    long lastObstacleTime;
    float score;
    Sound flapSound;
    Sound failSound;
    Texture falconImage;
    Rectangle falcon;
    boolean evilBird;


    float xVelocity;
    Background background = new Background();

    public GameScreen(final Bird gam) {
        // load the sound effects
        evilBird = false;
        falconImage = new Texture(Gdx.files.internal("evilBird.png"));
        falcon = new Rectangle();
        falcon.width = 64;
        falcon.height = 45;
        failSound = Gdx.audio.newSound(Gdx.files.internal("waterDrum.ogg"));
        flapSound = Gdx.audio.newSound(Gdx.files.internal("hey.ogg"));
        score = 0;
        pipeUpImage = new Texture(Gdx.files.internal("pipe_up.png"));
        pipeDownImage = new Texture(Gdx.files.internal("pipe_down.png"));
        this.game = gam;
        flappy = new Flappy();
        speedy = 0;
        gravity = 850f;
        xVelocity = -30;
        lava = new Lava();
// load the images


// create the camera and the SpriteBatch
        dead = false;
        gravity = 850f;
        // create a Rectangle to logically represent the player


        // load the images

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        obstacles = new Array<Rectangle>();
        spawnObstacle();

    }

    private void spawnObstacle() {
        // Calcula la alçada de l'obstacle aleatòriament
        float holey = MathUtils.random(50, 230);
        // Crea dos obstacles: Una tubería superior i una inferior
        Rectangle pipe1 = new Rectangle();
        pipe1.x = 800;
        pipe1.y = holey - 230;
        pipe1.width = 64;
        pipe1.height = 230;
        obstacles.add(pipe1);
        Rectangle pipe2 = new Rectangle();
        pipe2.x = 800;
        pipe2.y = holey + 200;
        pipe2.width = 64;
        pipe2.height = 230;
        obstacles.add(pipe2);
        lastObstacleTime = TimeUtils.nanoTime();
    }

    private void spawnEvilBird() {
        // Calcula la alçada de l'obstacle aleatòriament
        evilBird = true;
        // Crea dos obstacles: Una tubería superior i una inferior
        speedyEvil = 0;
        falcon.x = 800 + (64 / 2);
        falcon.y = ((int) Math.random() * 300) + 100;
        lastObstacleTime = TimeUtils.nanoTime();

    }

    @Override
    public void render(float delta) {
        background.update();
        lava.update();


        speedy = flappy.update(speedy, gravity, xVelocity);
        if (Gdx.input.isTouched()) {
            flappy.planeja();
            speedy = 200;
        }

        if (flappy.restart) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }


            /*if (player.y > 480 - 45) {
                player.y = 480 - 45;
                speedy = 0;
            }
            if (player.y < 0 - 45) {
                dead = true;
               *//* timeFromDeath= TimeUtils.nanoTime();*//*
            }
*/

        //La puntuació augmenta amb el temps de joc
        score += Gdx.graphics.getDeltaTime();
        if (Gdx.input.justTouched()) {
            speedy = 400f;
            /*flapSound.play();*/
        }


        if (flappy.box.overlaps(falcon)) {
            dead = true;
            /*timeFromDeath = TimeUtils.nanoTime();*/

        }


        if (evilBird) {
            falcon.x -= 200 * Gdx.graphics.getDeltaTime();

        }

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

        for (
                int i = 0;
                i < obstacles.size; i++) {
            game.batch.draw(
                    i % 2 == 0 ? pipeUpImage : pipeDownImage,
                    obstacles.get(i).x, obstacles.get(i).y);
        }
        if (evilBird) {
            game.batch.draw(falconImage, falcon.x, falcon.y);
        }
        game.font.draw(game.batch, "Score: " + (int) score, 10, 470);
        game.batch.end();

        // process user input

        if (evilBird) {
            falcon.y += speedyEvil * Gdx.graphics.getDeltaTime();
            speedyEvil -= gravity * Gdx.graphics.getDeltaTime();
        }

        // Comprova si cal generar un obstacle nou
        if (TimeUtils.nanoTime() - lastObstacleTime > 1500000000 && evilBird)

            spawnObstacle();
        else if (TimeUtils.nanoTime() - lastObstacleTime > 1500000000 && !evilBird) {
            if (Math.random() > 0.5f) {
                spawnEvilBird();
            } else {
                spawnObstacle();
            }
        }

        // Mou els obstacles. Elimina els que estan fora de la pantalla
        // Comprova si el jugador colisiona amb un obstacle,
        // llavors game over
        Iterator<Rectangle> iter = obstacles.iterator();
        while (iter.hasNext()) {
            Rectangle tuberia = iter.next();
            tuberia.x -= 200 * Gdx.graphics.getDeltaTime();
            if (tuberia.x < -64)
                iter.remove();
            if (tuberia.overlaps(flappy.box)) {
                dead = true;
                /*timeFromDeath = TimeUtils.nanoTime();*/
            }
        }
        if (falcon.x < -64) evilBird = false;

        if (falcon.y < 25 && evilBird) {
            speedyEvil = 400f;
        } else if (falcon.y < 400f && Math.random() > 0.95 && evilBird) {
            speedyEvil = 400f;
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
        pipeUpImage.dispose();
        pipeDownImage.dispose();
        failSound.dispose();
        flapSound.dispose();

    }
}