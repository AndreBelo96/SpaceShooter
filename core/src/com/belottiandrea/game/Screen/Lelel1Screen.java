package com.belottiandrea.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.belottiandrea.game.GameManager;
import com.belottiandrea.game.Sprite.Aereo;
import com.belottiandrea.game.Sprite.Bullet;
import com.belottiandrea.game.Sprite.Enemy;
import com.belottiandrea.game.Sprite.Explosion;
import com.belottiandrea.game.Tools.ScrollingBackground;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Belotti Andrea on 05/04/2018.
 */

public class Lelel1Screen implements Screen {

    public static final float SHOOT_WAIT_TIME = 0.5f;
    public static final float MIN_ENEMY_SPAWN_TIME = 1f;
    public static final float MAX_ENEMY_SPAWN_TIME = 3f;

    private static final int START_TIME = 6;

    private GameManager game;
    private float startTime;

    //Score's variables
    private BitmapFont scoreFont,scoreFont2;
    int score;

    //Variable for Enemy
    public Random random;
    float enemySpawnTimer;

    // Player's's Variables
    public float timeToChange;
    public int speed;
    float shootTimer;
    private Aereo player1, player2;
    private float alphaTimer;
    private float alpha;

    // Health's Variables
    public float health =1; //0 dead 1 full health
    private Texture blank, mask;

    //Lists
    ArrayList<Bullet> bullets;
    ArrayList<Enemy> enemies;
    ArrayList<Explosion> explosions;



    public Lelel1Screen(GameManager game) {
        this.game = game;
        speed =100;

        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        explosions = new ArrayList<Explosion>();

        blank = new Texture("blank.png");
        mask = new Texture("Maschera.png");
        scoreFont = new BitmapFont();
        scoreFont2 = new BitmapFont();
        score = 0;
        random = new Random();
        enemySpawnTimer = random.nextFloat() * (MAX_ENEMY_SPAWN_TIME - MIN_ENEMY_SPAWN_TIME) + MIN_ENEMY_SPAWN_TIME;

        player1 = new Aereo(GameManager.WIDTH / 4);
        player2 = new Aereo(GameManager.WIDTH *3 / 4);

        alpha = game.min /10;
        alphaTimer = 0;
        shootTimer = 0;
        timeToChange = 0;
        startTime = 0;

        game.scrollingBackground.setSpeedFixed(false);
        game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);


    }

    public void update(float dt) {

        handleInput(dt);

        //timers
        timeToChange += dt;
        shootTimer += dt;
        enemySpawnTimer -= dt;
        alphaTimer += dt;
        startTime += dt;

        if (startTime > START_TIME) {
            //spawn enemies
            if (enemySpawnTimer <= 0) {
                enemySpawnTimer = random.nextFloat() * (MAX_ENEMY_SPAWN_TIME - MIN_ENEMY_SPAWN_TIME) + MIN_ENEMY_SPAWN_TIME;
                float rand = random.nextInt(GameManager.WIDTH / 2 - Enemy.WIDTH * 2);
                enemies.add(new Enemy(rand, speed));
                enemies.add(new Enemy(rand + GameManager.WIDTH / 2, speed));
            }

            //update player
            player1.update(dt);
            player2.update(dt);

            //update collision


            //update enemies
            ArrayList<Enemy> enemiesToRemove = new ArrayList<Enemy>();
            for (Enemy enemy : enemies) {
                enemy.update(dt);
                if (enemy.remove)
                    enemiesToRemove.add(enemy);
            }

            //update bullets
            ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
            for (Bullet bullet : bullets) {
                bullet.update(dt);
                if (bullet.remove)
                    bulletsToRemove.add(bullet);
            }

            //Update Explosions
            ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
            for (Explosion explosion : explosions) {
                explosion.update(dt);
                if (explosion.remove)
                    explosionsToRemove.add(explosion);
            }

            //collision
            for (Bullet bullet : bullets) {
                for (Enemy enemy : enemies) {
                    if (bullet.getCollisionRect().collidesWith(enemy.getCollisionRect())) {
                        bulletsToRemove.add(bullet);
                        enemiesToRemove.add(enemy);
                        explosions.add(new Explosion(enemy.getX(), enemy.getY()));
                        score += 10;
                    }
                }
            }


            for (Enemy enemy : enemies) {
                if (enemy.getCollisionRect().collidesWith(player1.getCollisionRect()) || enemy.getCollisionRect().collidesWith(player2.getCollisionRect())) {
                    enemiesToRemove.add(enemy);
                    explosions.add(new Explosion(enemy.getX(), enemy.getY()));
                }
            }

            for (Enemy enemy : enemies) {
                if (enemy.getCollisionRect().collidesWith(player1.getCollisionRect())) {
                    health -= 0.1;

                    if (health <= 0) {
                        explosions.add(new Explosion(player1.getX(), player1.getY()));
                        explosions.add(new Explosion(player2.getX(), player2.getY()));
                        this.dispose();
                        game.setScreen(new GameOverScreen(game, score));
                        return;
                    }

                }
            }
            enemies.removeAll(enemiesToRemove);
            bullets.removeAll(bulletsToRemove);
            explosions.removeAll(explosionsToRemove);

            //increment speed
            if (timeToChange >= 5) {
                speed = speed + 25;
                timeToChange = 0;
            }

            if (alphaTimer >= (game.time)) {
                if (game.eyeRight) {
                    player2.setAlpha(1 - alpha);

                    if (alpha < (game.max / 10))
                        alpha = alpha + 0.1f;
                    else
                        alpha = (game.max / 10);

                    alphaTimer = 0;
                } else {
                    player1.setAlpha(1 - alpha);

                    if (alpha < (game.max / 10))
                        alpha = alpha + 0.1f;
                    else
                        alpha = (game.max / 10);

                    alphaTimer = 0;
                }

            }

        }
    }


    private void handleInput(float dt) {
        if( isLeft() ) {
            player1.posx -= player1.velocity * dt;
            player2.posx -= player1.velocity * dt;
            player1.setFrame(1);
            player2.setFrame(1);

            if(player1.posx < 0) {
                player1.posx = 0;
                player2.posx = player1.posx + GameManager.WIDTH/2;
            }
        }

        if(isRight() && player1.posx + player1.getWidth() <= GameManager.WIDTH / 2 ) {
            player1.posx += player1.velocity * dt;
            player2.posx += player1.velocity * dt;
            player1.setFrame(2);
            player2.setFrame(2);
        }


        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && (shootTimer >= SHOOT_WAIT_TIME)){

            bullets.add(new Bullet(player1.posx + 9));
            bullets.add(new Bullet(player1.posx + player1.getWidth() - 18));

            bullets.add(new Bullet(player2.posx + 9));
            bullets.add(new Bullet(player2.posx + player1.getWidth() - 18));
            shootTimer = 0;
        }

        if( !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player1.setFrame(0);
            player2.setFrame(0);
        }


    }

    @Override
    public void render(float delta) {


        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if( startTime > START_TIME ) {
            game.batch.begin();

            game.scrollingBackground.updateAndRenderer(delta, game.batch, 0);
            game.scrollingBackground.updateAndRenderer(delta, game.batch, GameManager.WIDTH / 2);

            player1.draw(game.batch);
            player2.draw(game.batch);

            GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score : " + score);
            scoreFont.draw(game.batch, scoreLayout, GameManager.WIDTH / 4 - scoreLayout.width / 2, GameManager.HEIGHT - scoreLayout.height - 10);
            scoreFont2.draw(game.batch, scoreLayout, GameManager.WIDTH * 3 / 4 - scoreLayout.width / 2, GameManager.HEIGHT - scoreLayout.height - 10);

            for (Bullet bullet : bullets) {
                bullet.render(game.batch);
            }

            for (Enemy enemy : enemies) {
                enemy.render(game.batch);
            }

            for (Explosion explosion : explosions) {
                explosion.render(game.batch);
            }

            //draw health
            if (health >= 0.6)
                game.batch.setColor(Color.GREEN);
            else if (health > 0.2)
                game.batch.setColor(Color.ORANGE);
            else
                game.batch.setColor(Color.RED);

            game.batch.draw(blank, 0, 45, GameManager.WIDTH / 2 * health, 5);
            game.batch.draw(blank, GameManager.WIDTH / 2, 45, GameManager.WIDTH / 2 * health, 5);

            game.batch.setColor(Color.WHITE);

            //draw mask
            game.batch.draw(mask, 0, 0, GameManager.WIDTH, GameManager.HEIGHT);


            game.batch.end();
        }
        else
        {
            game.batch.begin();
            GlyphLayout scoreLayout = new GlyphLayout(scoreFont, " " + (START_TIME -(int)(startTime)));
            scoreFont.draw(game.batch, scoreLayout, GameManager.WIDTH / 4 - scoreLayout.width / 2, GameManager.HEIGHT / 2);
            scoreFont2.draw(game.batch, scoreLayout, GameManager.WIDTH * 3 / 4 - scoreLayout.width / 2, GameManager.HEIGHT / 2);
            game.batch.end();
        }

    }

    private boolean isRight () {
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT) || (Gdx.input.isTouched() && game.cam.getInputInGameWorld().x >= GameManager.WIDTH/2 );
    }

    private boolean isLeft () {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isTouched() && game.cam.getInputInGameWorld().x < GameManager.WIDTH/2 );

    }


    @Override
    public void dispose() {

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


}
