package com.mygdx.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.sql.Time;

public class Flappy {

    Texture[] textures;

    Rectangle box;
    Texture actual;
    Long flapTime;
    boolean dead = false;
    boolean restart = false;
    private float divisioPlaneig;
    private boolean planejant;
    private long timeFromFlap;
    private long timeFromUll;
    private long timeFoc;

    public long getEnergiaPlaneig() {
        return energiaPlaneig;
    }

    private long energiaPlaneig;



    public Flappy() {
        energiaPlaneig = 1000;
        textures = new Texture[7];
        textures[0] = new Texture(Gdx.files.internal("bird.png"));
        textures[1] = new Texture(Gdx.files.internal("birdFlap.png"));
        textures[2] = new Texture(Gdx.files.internal("deathBird1.png"));
        textures[3] = new Texture(Gdx.files.internal("birdFlapDead.png"));
        textures[4] = new Texture(Gdx.files.internal("birdFlama.png"));
        textures[5] = new Texture(Gdx.files.internal("birdFlapFlama.png"));
        textures[6] = new Texture(Gdx.files.internal("birdUll.png"));
        box = new Rectangle();
        box.height = 45;
        box.width = 64;
        box.x = 200;
        box.y = 480 / 2 - 45 / 2;
        actual = textures[0];
        flapTime = TimeUtils.nanoTime();
        restart = false;
        timeFromUll=TimeUtils.nanoTime();

    }

    public void setEnergiaPlaneig(long energiaPlaneig) {
        this.energiaPlaneig += energiaPlaneig;
    }

    public void render(SpriteBatch batch) {

        if (dead) {
            if (box.y > 480 + 45) {
                restart = true;
            } else {
                box.y += 2;
                box.x += 1;
            }
        }
        batch.draw(actual, box.x, box.y);
    }

    public float update(float speedy, float gravity, float xVelocity) {
        if (dead) {

            actual = textures[2];
            speedy += 50;
            volaMort();
        } else {
            divisioPlaneig = 1;

           /* if(TimeUtils.nanoTime()-timeFromUll>5000000000l&&TimeUtils.nanoTime()-timeFromUll<9000000000l) {
                actual=textures[6];
            }else if (TimeUtils.nanoTime()-timeFromUll>9000000000l) timeFromUll=TimeUtils.nanoTime();  entra en conflicte amb les altres animacions hauria de ferlo en totes les opcions*/
            if (TimeUtils.nanoTime() - flapTime > 100000000l) actual = textures[0];


            if (Gdx.input.justTouched()) {
                speedy = 400f;
                flapeja();
                timeFromFlap= TimeUtils.nanoTime();
            /*Gdx.input.vibrate(300);    Falta mirar-s'ho  sino hi ha aixó
             // Get instance of Vibrator from current Context
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                // Vibrate for 300 milliseconds
            v.vibrate(300);    i la melodia de star wars diu :   v.vibrate(new long[]{0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500}, -1); */
            } else if (Gdx.input.isTouched()&&energiaPlaneig>0&& TimeUtils.nanoTime()-timeFromFlap>500000000) {
                planeja();
                planeigMoviment();
                energiaPlaneig-=7;
            }
            if (box.y+ box.height>480){
                speedy=-50;
            }

            speedy = actualitzarGravetat(speedy, gravity);
        }
        return speedy;
    }

    public void esCrema(){
        if (actual==textures[1])actual=textures[5];
        else if(actual==textures[0])actual=textures[4];
        else if (actual==textures[2]||actual==textures[3]){}
        else actual=textures[5];
    }


    private float actualitzarGravetat(float speedy, float gravity) {
        if (speedy < 0) {
            speedy -= (gravity / divisioPlaneig) * Gdx.graphics.getDeltaTime();
        } else {
            speedy -= gravity * Gdx.graphics.getDeltaTime();
        }
        box.y += speedy * Gdx.graphics.getDeltaTime();

        return speedy;
    }

    public void flapeja() {
        actual = textures[1];
        flapTime = TimeUtils.nanoTime();
    }

    void volaMort() {
        if (TimeUtils.nanoTime() - flapTime > 55555555) {
            if (actual.equals(textures[3])) actual = textures[2];
            else actual = textures[3];
            flapTime = TimeUtils.nanoTime();
        }
    }

    private void planeigMoviment() {
        if (TimeUtils.nanoTime() - tempsAleteigPlanejant > 55555555) {
            if (actual.equals(textures[1])) actual = textures[0];
            else actual = textures[1];
            tempsAleteigPlanejant = TimeUtils.nanoTime();
        }
    }

    long tempsAleteigPlanejant = TimeUtils.nanoTime();

    void planeja() {
        divisioPlaneig = 5f;
        actual = textures[1];
    }
}
