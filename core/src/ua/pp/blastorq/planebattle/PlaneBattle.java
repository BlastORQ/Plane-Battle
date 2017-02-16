package ua.pp.blastorq.planebattle;

import com.badlogic.gdx.Game;
import ua.pp.blastorq.planebattle.loader.data;
import ua.pp.blastorq.planebattle.screens.MainMenuScreen;
import ua.pp.blastorq.planebattle.screens.SplashScreen;

public class PlaneBattle extends Game {
	private data loader;
	private Bill b;
	private boolean isDesktop;

	 PlaneBattle(Bill bill, boolean isDesktop) {
		this.b = bill;
		this.isDesktop = isDesktop;
		loader = new data();
	}

	public PlaneBattle(boolean isDesktop) {
		this.isDesktop = isDesktop;
		loader = new data();
	}
	@Override
	public void create() {
			loader.load();
			this.setScreen(new SplashScreen(this, new MainMenuScreen(this)));
		}

	@Override
	public void render() {
		super.render();
	}
	@Override
	public void dispose() {
		super.dispose();
	}

	public Bill getB() {
		return b;
	}

	public boolean getDesktop() {
		return isDesktop;
	}
}
