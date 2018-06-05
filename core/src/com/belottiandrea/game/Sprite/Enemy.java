package com.belottiandrea.game.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.belottiandrea.game.GameManager;
import com.belottiandrea.game.Tools.CollisionRect;

/**
 * Created by Belotti Andrea on 05/04/2018.
 */

public class Enemy  {

    private static TextureRegion texture;
    private int rotation_speed;
    public static final int WIDTH = 74;
    public static final int HEIGHT = 74;
    public int speed;

    CollisionRect rect;
    public float x,y;
    public boolean remove = false;

    public Enemy(float x , int speed) {
        this.x = x;
        this.y = GameManager.HEIGHT;
        this.speed = speed;
        this.rect = new CollisionRect( this.x , y, WIDTH , HEIGHT);
        rotation_speed=0;
        if(texture == null) {
            texture = new TextureRegion(new Texture ("Enemy.png"));
        }
    }




    public void update(float dt){
        y -= speed * dt;
        if(y < - HEIGHT)
            remove = true;



        rect.move(x ,y);
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y ,WIDTH/2, HEIGHT/2, WIDTH, HEIGHT, 1,1,rotation_speed);
        rotation_speed ++;
    }

    public CollisionRect getCollisionRect(){
        return rect;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}