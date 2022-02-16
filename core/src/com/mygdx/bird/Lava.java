package com.mygdx.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Lava {

    Texture[] lavas;
    Texture lava;
    long movimentLava = TimeUtils.nanoTime();

    public Lava() {
        lavas = new Texture[2];
        lavas[0] = new Texture(Gdx.files.internal("lava1.png"));
        lavas[1] = new Texture(Gdx.files.internal("lava2.png"));
        lava = lavas[0];

    }

    public void update() {
        if (TimeUtils.nanoTime() - movimentLava > 155000000) {
            if (lava.equals(lavas[0])) lava = lavas[1];
            else lava = lavas[0];
            movimentLava = TimeUtils.nanoTime();
        }
    }
    public  void render(SpriteBatch batch){
        batch.draw(lava,0,0);

    }

    public void dispose() {
        for (Texture t :
                lavas) {
            t.dispose();
        }
        lava.dispose();
    }
}
