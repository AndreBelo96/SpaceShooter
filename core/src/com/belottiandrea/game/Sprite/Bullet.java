package com.belottiandrea.game.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.belottiandrea.game.GameManager;
import com.belottiandrea.game.Tools.CollisionRect;

/**
 * Created by Belotti Andrea on 05/04/2018.
 */

public class Bullet  {

    public static final int SPEED = 500;
    public static final int DEFAULT_Y = 70;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 20;
    private static Sprite texture;

    public CollisionRect rect;
    float x, y ;
    public boolean remove;


    public Bullet(float x){
        this.x= x;
        this.y = DEFAULT_Y;
        this.rect = new CollisionRect(x, y, WIDTH, HEIGHT);

        if(texture == null) {
            texture = new Sprite(new Texture(Gdx.files.internal("bullet.png")));
        }
    }


    public void update(float deltaTime){
        y += SPEED * deltaTime;
        if(y > GameManager.HEIGHT)
            remove = true;

        rect.move(x,y);
    }


    public void render (
            SpriteBatch batch){batch.draw(texture, x ,y);
    }


    public CollisionRect getCollisionRect(){
        return rect;
    }

}
