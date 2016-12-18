package ua.pp.blastorq.planebattle.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ua.pp.blastorq.planebattle.PlaneBattle;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 420;
		config.height = 700;
		new LwjglApplication(new PlaneBattle(), config);
	}
}
