package com.belottiandrea.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.belottiandrea.game.GameManager;
import com.belottiandrea.game.Tools.ScrollingBackground;

/**
 * Created by Belotti Andrea on 05/04/2018.
 */

public class GameOverScreen implements Screen {

    private static final int BANNER_WIDTH = 350;
    private static final int BANNER_HEIGHT = 90;

    public GameManager game;
    int score, highscore;

    public Texture gameOverBanner, mask ;
    public BitmapFont scorefont;

    public GameOverScreen(GameManager game, int score){
        this.game = game;
        this.score = score;

        mask = new Texture("Maschera.png");
        //Get highscore form save files
        Preferences prefs = Gdx.app.getPreferences("spacegame");
        this.highscore = prefs.getInteger("highscore", 0);


        //Check if score beats highscore
        if(score > highscore){
            prefs.putInteger("highscore", score);
            prefs.flush();
        }

        game.scrollingBackground.setSpeedFixed(true);
        game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);


        //Load textures and fonts
        gameOverBanner = new Texture("GameOver.png");
        scorefont = new BitmapFont();


    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {


        //Clear the game screen with Black
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.scrollingBackground.updateAndRenderer(delta, game.batch , 0);
        game.scrollingBackground.updateAndRenderer(delta, game.batch , GameManager.WIDTH /2);


        game.batch.draw(gameOverBanner, GameManager.WIDTH / 4 - BANNER_WIDTH / 2,
                GameManager.HEIGHT  - BANNER_HEIGHT - 15,
                BANNER_WIDTH, BANNER_HEIGHT);

        game.batch.draw(gameOverBanner, GameManager.WIDTH * 3 / 4 - BANNER_WIDTH / 2,
                GameManager.HEIGHT  - BANNER_HEIGHT - 15,
                BANNER_WIDTH, BANNER_HEIGHT);

        GlyphLayout scoreLayout = new GlyphLayout(scorefont, "Score : " + score, Color.WHITE, 0 , Align.left, false);
        GlyphLayout highScoreLayout = new GlyphLayout(scorefont, "Highscore : " + highscore, Color.ORANGE, 0 , Align.left, false);
        GlyphLayout restart = new GlyphLayout(scorefont, "Shoot To Restart!", Color.WHITE, 0 , Align.left, false);


        scorefont.draw(game.batch, scoreLayout,
                GameManager.WIDTH / 4 - scoreLayout.width / 2,
                GameManager.HEIGHT - BANNER_HEIGHT - 15 *2);

        scorefont.draw(game.batch, scoreLayout,
                GameManager.WIDTH *3 / 4 - scoreLayout.width / 2,
                GameManager.HEIGHT - BANNER_HEIGHT - 15 *2);

        scorefont.draw(game.batch, highScoreLayout,
                GameManager.WIDTH / 4 - highScoreLayout.width / 2,
                GameManager.HEIGHT - BANNER_HEIGHT - scoreLayout.height - 15 * 3);

        scorefont.draw(game.batch, highScoreLayout,
                GameManager.WIDTH * 3 / 4 - highScoreLayout.width / 2,
                GameManager.HEIGHT - BANNER_HEIGHT - scoreLayout.height - 15 * 3);

        scorefont.draw(game.batch, restart,
                GameManager.WIDTH / 4 - restart.width / 2,
                GameManager.HEIGHT / 2 - 20 );

        scorefont.draw(game.batch, restart,
                GameManager.WIDTH * 3 / 4 - restart.width / 2,
                GameManager.HEIGHT / 2 - 20);


        //if try again and main menu is pressed
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.dispose();
            game.batch.end();
            game.setScreen(new Lelel1Screen(game));
            return;
        }

        //draw mask
        game.batch.draw(mask, 0, 0, GameManager.WIDTH, GameManager.HEIGHT);

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
