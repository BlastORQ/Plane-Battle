package ua.pp.blastorq.planebattle;

import com.badlogic.gdx.*;

import ua.pp.blastorq.planebattle.screens.GameScreen;
import ua.pp.blastorq.planebattle.screens.SplashScreen;
import ua.pp.blastorq.planebattle.screens.TeamChoose;

public class PlaneBattle extends Game {
	String text;

	@Override
	public void create() {
		this.setScreen(new SplashScreen(this, new TeamChoose(this)));

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