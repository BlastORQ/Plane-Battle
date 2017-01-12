package ua.pp.blastorq.planebattle;

import com.badlogic.gdx.Game;

import ua.pp.blastorq.planebattle.screens.MainMenuScreen;
public class PlaneBattle extends Game {

	@Override
	public void create() {
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
	@Override
	public void dispose() {
		super.dispose();
	}
}
