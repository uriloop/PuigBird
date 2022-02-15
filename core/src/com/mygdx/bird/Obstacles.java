package com.mygdx.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Obstacles {

    Texture lavaFall;
    Texture pipeUpImage;
    Texture pipeDownImage;
    Array<Rectangle> obstacles;
    Array<Rectangle> lavaList;
    long timeForLavaFall;

    public Obstacles() {
        lavaFall = new Texture(Gdx.files.internal("cascadaLava.png"));
        pipeUpImage = new Texture(Gdx.files.internal("pipe_up.png"));
        pipeDownImage = new Texture(Gdx.files.internal("pipe_down.png"));
        obstacles = new Array<Rectangle>();
        spawnObstaclePipes();
        lavaList = new Array<>();

    }

    public void spawnObstaclePipes() {
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
    }

    public void spawnObstacleLava() {
        timeForLavaFall=TimeUtils.nanoTime();
        Rectangle lava = new Rectangle();
        lava.height = 475;
        lava.width = 50;
        lava.x = 800;
        lava.y=480;


    }

    public void update(float xVelocity, long obstacleTime) {
        Iterator<Rectangle> iter = obstacles.iterator();
        while (iter.hasNext()) {
            Rectangle tuberia = iter.next();
            tuberia.x -= 200 * Gdx.graphics.getDeltaTime();
            if (tuberia.x < -64)
                iter.remove();
        }
        Iterator<Rectangle> iter2 = lavaList.iterator();
        while (iter2.hasNext()) {
            Rectangle lava = iter2.next();
            lava.x -= 200 * Gdx.graphics.getDeltaTime();
            if (TimeUtils.nanoTime()-timeForLavaFall>Math.random()*50000000){

                lava.y -= 120*Gdx.graphics.getDeltaTime();
            }
            if (lava.x < -64)
                iter2.remove();
        }


    }

    public void render(SpriteBatch batch) {


        for (
                int i = 0;
                i < obstacles.size; i++) {
            batch.draw(
                    i % 2 == 0 ? pipeUpImage : pipeDownImage,
                    obstacles.get(i).x, obstacles.get(i).y);
        }
        for (
                int i = 0;
                i < lavaList.size; i++) {
            batch.draw(
                   lavaFall,
                    lavaList.get(i).x, lavaList.get(i).y);
        }
    }

}
