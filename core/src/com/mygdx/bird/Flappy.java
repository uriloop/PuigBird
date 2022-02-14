package com.mygdx.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Flappy {


    Texture[] textures;
    Rectangle box;
    Texture actual;
    Long flapTime;

    public Flappy() {
        textures = new Texture[2];
        textures[0] = new Texture(Gdx.files.internal("bird.png"));
        textures[1]= new Texture(Gdx.files.internal("birdFlap.png"));
        box = new Rectangle();
        box.height = 45;
        box.width = 64;
        box.x = 200;
        box.y = 480 / 2 - 45 / 2;
        actual=textures[0];
        flapTime=TimeUtils.nanoTime();
    }


    public void render(SpriteBatch batch) {

        batch.draw(actual,box.x,box.y);
    }

    public void flapeja(){
        actual=textures[1];
        flapTime= TimeUtils.nanoTime();
    }

    public float update(float speedy, float gravity, float xVelocity) {
        if (Gdx.input.justTouched()) {
            speedy = 400f;
            flapeja();
            /*Gdx.input.vibrate(300);    Falta mirar-s'ho  sino hi ha aixó
             // Get instance of Vibrator from current Context
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                // Vibrate for 300 milliseconds
            v.vibrate(300);    i la melodia de star wars diu :   v.vibrate(new long[]{0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500}, -1); */
        }

        box.y += speedy * Gdx.graphics.getDeltaTime();
        speedy -= gravity * Gdx.graphics.getDeltaTime();
        if (TimeUtils.nanoTime()-flapTime>100000000l) actual=textures[0];

        return speedy;
    }

    void planeja() {


        if (TimeUtils.nanoTime()-flapTime>15500000){
            if (actual.equals(textures[0])) actual=textures[1];
            else actual= textures[0];
            flapTime= TimeUtils.nanoTime();
        }


    }
}
