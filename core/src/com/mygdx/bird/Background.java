package com.mygdx.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

public class Background {


    Texture[] texture;
    float x,y;
    Texture actual;
    long lastLlamp=TimeUtils.nanoTime();
    long lastLlum= TimeUtils.nanoTime();


    public Background() {
        texture=new Texture[3];
        texture[0] = new Texture(Gdx.files.internal("background.png"));
        texture[1] = new Texture(Gdx.files.internal("background_llamp.png"));
        texture[2] = new Texture(Gdx.files.internal("background_vibra.png"));
        x=0;
        y=0;
        actual=texture[0];
    }

    public void update() {
        if (TimeUtils.nanoTime()-lastLlum>300&&TimeUtils.nanoTime()-lastLlamp>300)
        actual=texture[0];
        if (TimeUtils.nanoTime()-lastLlamp>Math.random()*150000000000l+100000000l){
            actual=texture[1];
            lastLlamp=TimeUtils.nanoTime();
        }
        if (TimeUtils.nanoTime()-lastLlamp>Math.random()*15000000000l+100000l){
            actual=texture[2];
            lastLlum=TimeUtils.nanoTime();
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(actual,x,y);
    }

    public void dispose() {
        for (Texture t:
             texture) {
            t.dispose();
        }
        actual.dispose();
    }
}
