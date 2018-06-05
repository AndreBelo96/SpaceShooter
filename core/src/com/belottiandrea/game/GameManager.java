package com.belottiandrea.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.belottiandrea.game.Screen.GameOverScreen;
import com.belottiandrea.game.Screen.MainMenuScreen;
import com.belottiandrea.game.Screen.OptionScreen;
import com.belottiandrea.game.Tools.GameCamera;
import com.belottiandrea.game.Tools.ScrollingBackground;

public class GameManager extends Game {
	public static final int WIDTH = 800;
	public  static final int HEIGHT = 400;

	public float min, max , time;

	public static SpriteBatch batch;
	public ScrollingBackground scrollingBackground;
	public GameCamera cam;
	public boolean eyeRight;

	public GameManager() {
		super();
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		cam = new GameCamera(WIDTH, HEIGHT);
		this.scrollingBackground = new ScrollingBackground();
		min=0;
		max=10f;
		time=10f;
		eyeRight = true;
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		batch.setProjectionMatrix(cam.combined());
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		cam.update(width, height);
		super.resize(width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();
		super.dispose();
	}

}
