package com.belottiandrea.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.belottiandrea.game.GameManager;
import com.belottiandrea.game.Tools.ScrollingBackground;

/**
 * Created by Belotti Andrea on 05/04/2018.
 */

public class MainMenuScreen implements Screen {

    public static final int LOGO_WIDHT = 360;
    public static final int LOGO_HEIGHT = 180;
    public static final int LOGO_Y = 240;

    public static final int PLAY_BUTTON_WIDTH = 150;
    public static final int PLAY_BUTTON_HEIGHT = 70;
    public static final int PLAY_BUTTON_Y = 180;

    public static final int OPTION_BUTTON_WIDTH = 150;
    public static final int OPTION_BUTTON_HEIGHT = 70;
    public static final int OPTION_BUTTON_Y = 120;

    public static final int EXIT_BUTTON_WIDTH = 150;
    public static final int EXIT_BUTTON_HEIGHT = 70;
    public static final int EXIT_BUTTON_Y = 60;
    private BitmapFont scoreFont;
    public final GameManager game;

    public static Stage stage;
    public Viewport viewport;
    private Skin skin;
    private Table table;
    private final TextField nameText;
    private final Label label;

    private final Preferences prefs;

    String name;
    private Texture logo;

    public MainMenuScreen(final GameManager game){
        this.game = game;
        logo = new Texture("Logo.png");

        scoreFont = new BitmapFont();
        game.scrollingBackground.setSpeedFixed(true);
        game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);

        viewport = new StretchViewport(game.WIDTH, game.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        final MainMenuScreen mainMenuScreen = this;

        skin = new Skin(Gdx.files.internal("skin.json"));

        prefs = Gdx.app.getPreferences("spacegame");

        nameText = new TextField(prefs.getString("name", ""), skin);
        label = new Label("Name: ", skin);



        //Check if name is different from the real name


        table = new Table();
        table.left();
        table.setPosition(GameManager.WIDTH/2+ 40, GameManager.HEIGHT/2);
        table.add(label);
        table.add(nameText).width(100);

        stage.addActor(table);

        //ImageTextButton
        //Play
        ImageTextButton button4 = new ImageTextButton("Play",skin);
        button4.setSize(PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);
        button4.setPosition(GameManager.WIDTH / 4 - PLAY_BUTTON_WIDTH / 2, PLAY_BUTTON_Y);
        button4.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                mainMenuScreen.dispose();
                game.setScreen(new Lelel1Screen(game));
                return true;
            }
        });
        stage.addActor(button4);

        //Option
        ImageTextButton button3 = new ImageTextButton("Option",skin);
        button3.setSize(OPTION_BUTTON_WIDTH,OPTION_BUTTON_HEIGHT);
        button3.setPosition(GameManager.WIDTH / 4 - OPTION_BUTTON_WIDTH / 2, OPTION_BUTTON_Y);
        button3.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                mainMenuScreen.dispose();
                game.setScreen(new OptionScreen(game));
                return true;
            }
        });
        stage.addActor(button3);

        //Exit
        ImageTextButton button2 = new ImageTextButton("Exit",skin);
        button2.setSize(EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);
        button2.setPosition(GameManager.WIDTH / 4 - EXIT_BUTTON_WIDTH /2, EXIT_BUTTON_Y);
        button2.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                mainMenuScreen.dispose();
                Gdx.app.exit();
                return true;
            }
        });
        stage.addActor(button2);


        Gdx.input.setInputProcessor(MainMenuScreen.stage);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!nameText.getText().equals(name)){
            prefs.putString("name", nameText.getText());
            prefs.flush();
        }

        game.batch.begin();

        game.scrollingBackground.updateAndRenderer(delta, game.batch , 0);


        game.batch.draw(logo, GameManager.WIDTH / 2 - LOGO_WIDHT / 2 , LOGO_Y, LOGO_WIDHT /1.2f, LOGO_HEIGHT/1.2f);

        game.batch.end();

        stage.draw();
        stage.act();
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

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

}
