package com.projecthawkthorne.client;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Main {
	public static void main (String[] argv) {

		//run server within client
//		new Thread() {
//			public void run() {
//				com.projecthawkthorne.server.Main.main(null);
//			}
//		}.start();
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		new LwjglApplication(new HawkthorneGame(), "JttCoH", 912, 528, false);
	}
}
