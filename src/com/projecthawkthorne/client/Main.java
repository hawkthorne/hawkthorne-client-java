package com.projecthawkthorne.client;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Main {
	public static void main (String[] argv) {
		new LwjglApplication(new HawkthorneGame(), "JttCoH", 912, 528, false);
	}
}
