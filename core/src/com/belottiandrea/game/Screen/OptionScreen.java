package com.belottiandrea.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.belottiandrea.game.GameManager;
import com.belottiandrea.game.Tools.ScrollingBackground;

/**
 * Created by Belotti Andrea on 22/05/2018.
 */

public class OptionScreen implements Screen {
    private static final int TITLE_WIDHT = 360;
    private static final int TITLE_HEIGHT = 140;
    private static final int TITLE_Y = 255;

    private static final int LEFT_RIGHT_WIDTH = 100;
    private static final int LEFT_RIGHT_HEIGHT = 50;
    private static final int LEFT_RIGHT_Y = 30;

    private static final int BACK_WIDHT = 200;
    private static final int BACK_HEIGHT = 100;
    private static final int BACK_Y = 0;

    private static final int LATO_X = 60;
    private static final int LATO_Y = 52;

    private static final int MAX_MORE_Y = 210;

    private static final int MIN_MORE_Y = 150;

    private static final int CONT_Y = 90;

    private Label maxLabel, minLabel , timeLabel;
    Table table, table1, table2;

    final GameManager game;

    private boolean update;

    private Stage stage;
    public Viewport viewport;

    private boolean right;
    public float min, max, time;

    private Texture plus,less, back, title, Left ,Right;

    public OptionScreen(final GameManager game) {
        this.game = game;
        title = new Texture("option_button_inactive.png");
        plus = new Texture("Plus_Button.png");
        less = new Texture("Less_Button.png");
        back = new Texture("Back_Button.png");
        Right = new Texture("LeftRight_Right.png");
        Left = new Texture("LeftRight_Left.png");

        right = game.eyeRight;
        game.scrollingBackground.setSpeedFixed(true);
        game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);

        viewport = new StretchViewport(game.WIDTH, game.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        update = false;

        min = game.min;
        max = game.max;
        time = game.time;

        final OptionScreen optionScreen =  this;

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                //Max +
                int x = GameManager.WIDTH / 2 - LATO_X / 2;
                if(game.cam.getInputInGameWorld().x < x + LATO_X && game.cam.getInputInGameWorld().x > x && GameManager.HEIGHT - game.cam.getInputInGameWorld().y > MAX_MORE_Y && GameManager.HEIGHT - game.cam.getInputInGameWorld().y < MAX_MORE_Y + LATO_Y) {
                    if(max < 10 )
                        max = max + 1;
                    update=true;
                }

                //Max -
                x = GameManager.WIDTH /2 + LATO_X ;
                if(game.cam.getInputInGameWorld().x < x + LATO_X && game.cam.getInputInGameWorld().x > x && GameManager.HEIGHT - game.cam.getInputInGameWorld().y > MAX_MORE_Y && GameManager.HEIGHT - game.cam.getInputInGameWorld().y < MAX_MORE_Y + LATO_Y) {
                    if( max > 0)
                        max = max -1;
                    update=true;
                }

                //Min +
                 x = GameManager.WIDTH /2 - LATO_X / 2;
                if(game.cam.getInputInGameWorld().x < x + LATO_X && game.cam.getInputInGameWorld().x > x && GameManager.HEIGHT - game.cam.getInputInGameWorld().y > MIN_MORE_Y && GameManager.HEIGHT - game.cam.getInputInGameWorld().y < MIN_MORE_Y + LATO_Y) {
                    if(min < 10)
                        min = min + 1;
                    update=true;
                }

                //Min -
                x = GameManager.WIDTH /2 + LATO_X;
                if(game.cam.getInputInGameWorld().x < x + LATO_X && game.cam.getInputInGameWorld().x > x && GameManager.HEIGHT - game.cam.getInputInGameWorld().y > MIN_MORE_Y && GameManager.HEIGHT - game.cam.getInputInGameWorld().y < MIN_MORE_Y + LATO_Y) {
                    if(min > 0)
                        min = min - 1;
                    update=true;
                }

                //Cont +
                x = GameManager.WIDTH /2 + LATO_X;
                if(game.cam.getInputInGameWorld().x < x + LATO_X && game.cam.getInputInGameWorld().x > x && GameManager.HEIGHT - game.cam.getInputInGameWorld().y > CONT_Y && GameManager.HEIGHT - game.cam.getInputInGameWorld().y < CONT_Y + LATO_Y) {
                    if(time < 20)
                        time = time + 1;
                    update=true;
                }

                //Cont -
                x = GameManager.WIDTH /2 + LATO_X *2 + LATO_X /2;
                if(game.cam.getInputInGameWorld().x < x + LATO_X && game.cam.getInputInGameWorld().x > x && GameManager.HEIGHT - game.cam.getInputInGameWorld().y > CONT_Y && GameManager.HEIGHT - game.cam.getInputInGameWorld().y < CONT_Y + LATO_Y) {
                    if(time > 0)
                        time = time - 1;
                    update=true;
                }

                x = GameManager.WIDTH / 2 - BACK_WIDHT / 2;
                if(game.cam.getInputInGameWorld().x < x + BACK_WIDHT && game.cam.getInputInGameWorld().x > x && GameManager.HEIGHT - game.cam.getInputInGameWorld().y > BACK_Y && GameManager.HEIGHT - game.cam.getInputInGameWorld().y < BACK_Y + BACK_HEIGHT) {
                    optionScreen.dispose();
                    game.setScreen(new MainMenuScreen(game));

                }

                x = GameManager.WIDTH / 4 - LEFT_RIGHT_WIDTH / 2;
                if(game.cam.getInputInGameWorld().x < x + LEFT_RIGHT_WIDTH && game.cam.getInputInGameWorld().x > x && GameManager.HEIGHT - game.cam.getInputInGameWorld().y > LEFT_RIGHT_Y && GameManager.HEIGHT - game.cam.getInputInGameWorld().y < LEFT_RIGHT_Y + LEFT_RIGHT_HEIGHT) {
                    right = !right;

                }

                return super.touchUp(screenX, screenY, pointer, button);
            }

        });





        maxLabel = new Label("Max trasparenza: " +  max ,new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        minLabel = new Label("Min trasparenza: " + min ,new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Tempo di trasparenza: " + time + " secondi" ,new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        maxLabel.setFontScale(2f);
        minLabel.setFontScale(2f);
        timeLabel .setFontScale(2f);

        table = new Table();
        table.left();
        table.setPosition(10, MAX_MORE_Y + 20);
        table.add(maxLabel);

        table1 = new Table();
        table1.left();
        table1.setPosition(10, MIN_MORE_Y + 20);
        table1.add(minLabel);

        table2 = new Table();
        table2.left();
        table2.setPosition(10, CONT_Y + 20);
        table2.add(timeLabel);


        stage.addActor(table);
        stage.addActor(table1);
        stage.addActor(table2);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(update) {
            stage.dispose();
            maxLabel = new Label("Max trasparenza: " + max, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            minLabel = new Label("Min trasparenza: " + min, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            timeLabel = new Label("Tempo di trasparenza: " + (time) + " secondi",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            maxLabel.setFontScale(2f);
            minLabel.setFontScale(2f);
            timeLabel .setFontScale(2f);

            Table table = new Table();
            table.left();
            table.setPosition(10, MAX_MORE_Y + 20);
            table.add(maxLabel);

            Table table1 = new Table();
            table1.left();
            table1.setPosition(10, MIN_MORE_Y + 20);
            table1.add(minLabel);

            Table table2 = new Table();
            table2.left();
            table2.setPosition(10, CONT_Y + 20);
            table2.add(timeLabel);


            stage.addActor(table);
            stage.addActor(table1);
            stage.addActor(table2);

            game.min = min;
            game.max = max;
            game.time = time;
            update = false;
        }

        game.batch.begin();

        game.scrollingBackground.updateAndRenderer(delta, game.batch , 0);

        game.batch.draw(title, GameManager.WIDTH / 2 - TITLE_WIDHT / 2, TITLE_Y, TITLE_WIDHT, TITLE_HEIGHT);

        game.batch.draw(plus, GameManager.WIDTH / 2 - LATO_X / 2, MAX_MORE_Y, LATO_X, LATO_Y);

        game.batch.draw(plus, GameManager.WIDTH /2 - LATO_X / 2, MIN_MORE_Y , LATO_X, LATO_Y);

        game.batch.draw(plus, GameManager.WIDTH /2 + LATO_X , CONT_Y , LATO_X, LATO_Y);

        game.batch.draw(less, GameManager.WIDTH / 2 + LATO_X, MAX_MORE_Y, LATO_X, LATO_Y);

        game.batch.draw(less, GameManager.WIDTH / 2 + LATO_X, MIN_MORE_Y , LATO_X, LATO_Y);

        game.batch.draw(less, GameManager.WIDTH / 2 + LATO_X * 2 + LATO_X /2, CONT_Y , LATO_X, LATO_Y);

        game.batch.draw(back, GameManager.WIDTH / 2 - BACK_WIDHT / 2,  BACK_Y, BACK_WIDHT, BACK_HEIGHT);
        //game.eyeRight = right;
        if(right){
            game.batch.draw(Right, GameManager.WIDTH / 4 - LEFT_RIGHT_WIDTH / 2, LEFT_RIGHT_Y, LEFT_RIGHT_WIDTH, LEFT_RIGHT_HEIGHT);
        }
        else{
            game.batch.draw(Left, GameManager.WIDTH / 4 - LEFT_RIGHT_WIDTH / 2, LEFT_RIGHT_Y, LEFT_RIGHT_WIDTH, LEFT_RIGHT_HEIGHT);
        }

        game.eyeRight = right;

        game.batch.end();

        stage.draw();


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
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
