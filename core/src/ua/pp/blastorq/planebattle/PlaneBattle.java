package ua.pp.blastorq.planebattle;

import com.badlogic.gdx.Game;

import ua.pp.blastorq.planebattle.loader.ResourceLoader;
import ua.pp.blastorq.planebattle.screens.MainMenuScreen;
import ua.pp.blastorq.planebattle.screens.SplashScreen;

public class PlaneBattle extends Game {
	ResourceLoader loader;
	Bill b;

	public PlaneBattle(Bill bill) {
		this.b = bill;
	}

	@Override
	public void create() {
		this.setScreen(new SplashScreen(this, new MainMenuScreen(this)));
		loader = new ResourceLoader();
		loader.load();
	}

	public Bill getB() {
		return b;
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
