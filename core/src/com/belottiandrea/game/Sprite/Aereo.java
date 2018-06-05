package com.belottiandrea.game.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.belottiandrea.game.Tools.CollisionRect;

/**
 * Created by Belotti Andrea on 05/04/2018.
 */

public class Aereo extends Sprite {


    private final int WIDTH_SHIP = 53;
    private final int HEIGHT_SHIP = 61;
    private final int SHIP_Y = 45;

    public float score;
    private TextureRegion texture;
    CollisionRect rect;
    public float posx,posy;
    public float velocity;

    public Aereo(float x) {

        this.posx = x - WIDTH_SHIP/2;
        this.posy = SHIP_Y;
        this.rect = new CollisionRect(x, SHIP_Y, WIDTH_SHIP, HEIGHT_SHIP);
        score = 0;
        velocity = 200;
        setPosition(posx, posy);
        setFrame(0);

        setBounds(0, 0, WIDTH_SHIP,HEIGHT_SHIP);

    }


    public void setFrame(int frame){
        if (frame == 0){

            texture = new Sprite(new Texture(Gdx.files.internal("Aereo.png")));

        }
        else if(frame == 1) {
            texture = new Sprite(new Texture(Gdx.files.internal("AereoSX.png")));
        }
        else if (frame == 2) {
            texture = new Sprite(new Texture(Gdx.files.internal("AereoDX.png")));
        }
    }


    public void update(float dt) {
        setPosition(posx, posy);
        rect.move(posx,posy);
        setRegion(texture);
    }



    public void draw(Batch batch) {
        super.draw(batch);
    }

    public CollisionRect getCollisionRect(){
        return rect;
    }
}