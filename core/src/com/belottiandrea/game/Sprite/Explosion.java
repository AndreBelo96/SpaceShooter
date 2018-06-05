package com.belottiandrea.game.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Belotti Andrea on 05/04/2018.
 */

public class Explosion {

    public static final float FRAME_LENGTH = 0.1f;
    public static int OFFSET = 20;
    public static final int SIZE = 200;
    public static final int IMAGE_SIZE =100;
    public static Animation anim = null;
    float x, y , stateTime;
    public boolean remove = false;

    public Explosion(float x, float y ){
        this.x = x - OFFSET;
        this.y = y - OFFSET;
        stateTime = 0;

        if(anim == null){
            anim = new Animation(FRAME_LENGTH, TextureRegion.split(new Texture("ExplosionSet.png"), SIZE,SIZE )[0]);
        }

    }

    public void update(float dt) {
        stateTime += dt;
        if(anim.isAnimationFinished(stateTime))
            remove = true;
    }

    public void render (SpriteBatch batch){
        batch.draw((TextureRegion) anim.getKeyFrame(stateTime), x , y , IMAGE_SIZE, IMAGE_SIZE);
    }

}
