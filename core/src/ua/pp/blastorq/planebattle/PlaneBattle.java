package ua.pp.blastorq.planebattle;

import com.badlogic.gdx.*;

import ua.pp.blastorq.planebattle.screens.MainMenuScreen;
import ua.pp.blastorq.planebattle.screens.SplashScreen;

public class PlaneBattle extends Game {
	String text;

	@Override
	public void create() {
		this.setScreen(new SplashScreen(this, new MainMenuScreen(this)));
	}


	@Override
	public void render() {
		super.render();
	}


	@Override
	public void pause() {}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {

	}
}