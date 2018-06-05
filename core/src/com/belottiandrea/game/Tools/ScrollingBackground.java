package com.belottiandrea.game.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.belottiandrea.game.GameManager;

/**
 * Created by Belotti Andrea on 05/04/2018.
 */

public class ScrollingBackground {

    public static final int DEFAULT_SPEED = 100;
    public static final int ACCELERATION = 200;
    public static final int GOAL_REACH_ACCELERATION = 200;

    private Texture image;
    public float y1, y2;
    int speed; //in pixel al secondo
    int goalSpeed;
    float imageScale;
    boolean  speedFixed;

    public ScrollingBackground(){
        image = new Texture("ProvaSfondo.png");

        y1 = 0;
        y2 = image.getHeight();
        imageScale = GameManager.WIDTH / image.getWidth();
        speed=0;
        goalSpeed = DEFAULT_SPEED;
        speedFixed = true;

    }

    public void updateAndRenderer(float dt, SpriteBatch batch , int posx){

        if(speed < goalSpeed){
            speed += GOAL_REACH_ACCELERATION * dt;
            if(speed > goalSpeed)
                speed = goalSpeed;
        } else if(speed > goalSpeed){
            speed -= GOAL_REACH_ACCELERATION * dt;
            if(speed < goalSpeed)
                speed = goalSpeed;
        }

        if(!speedFixed)
            speed += ACCELERATION * dt;


        y1 -= speed * dt;
        y2 -= speed * dt;

        //Se raggiungo il bordo inferiore dello screen e non è visibile

        if(y1 + image.getHeight() / 2 * imageScale <= 0)
            y1 = y2 + image.getHeight() / 2 * imageScale;

        if(y2 + image.getHeight() / 2 * imageScale <= 0)
            y2 = y1 + image.getHeight() / 2  * imageScale;

        //render
        batch.draw(image, posx, y1, GameManager.WIDTH , image.getHeight() / 2 * imageScale);
        batch.draw(image, posx, y2, GameManager.WIDTH , image.getHeight() / 2 * imageScale);

    }


    public void setSpeed(int goalSpeed){
        this.goalSpeed = goalSpeed;
    }

    public void setSpeedFixed(boolean speedFixed){
        this.speedFixed = speedFixed;
    }


}
