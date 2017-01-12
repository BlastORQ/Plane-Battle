package ua.pp.blastorq.planebattle;

import com.badlogic.gdx.Game;

import ua.pp.blastorq.planebattle.screens.GameScreen;
public class PlaneBattle extends Game {

	@Override
	public void create() {
		this.setScreen(new GameScreen());
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
