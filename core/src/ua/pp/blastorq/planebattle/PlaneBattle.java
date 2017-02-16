package ua.pp.blastorq.planebattle;

import com.badlogic.gdx.Game;
import ua.pp.blastorq.planebattle.loader.ResourceLoader;
import ua.pp.blastorq.planebattle.screens.MainMenuScreen;
import ua.pp.blastorq.planebattle.screens.SplashScreen;
import ua.pp.blastorq.planebattle.sprite.Plane;

public class PlaneBattle extends Game {
	private ResourceLoader loader;
	private Bill b;
	private boolean isDesktop;

	 PlaneBattle(Bill bill, boolean isDesktop) {
		 this.b = bill;
		 this.isDesktop = isDesktop;
		 loader = new ResourceLoader(this);
	}

	public PlaneBattle(boolean isDesktop) {
		this.isDesktop = isDesktop;
		loader = new ResourceLoader(this);
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
