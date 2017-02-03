package ua.pp.blastorq.planebattle.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ua.pp.blastorq.planebattle.PlaneBattle;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 320;
		config.height = 600;
		new LwjglApplication(new PlaneBattle(true), config);
	}
}
