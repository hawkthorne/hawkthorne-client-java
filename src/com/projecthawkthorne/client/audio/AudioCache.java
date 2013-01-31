package com.projecthawkthorne.client.audio;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioCache {
    private static Map<String,Sound> sfxCache = new HashMap<String,Sound>();
    private static Map<String,Sound> musicCache = new HashMap<String,Sound>();

	public static void playSfx(String soundFile){
		Sound s;
		if(sfxCache.containsKey(soundFile)){
			s = sfxCache.get(soundFile);
		}else{
			s = Gdx.audio.newSound(Gdx.files.internal("data/audio/sfx/"+soundFile+".ogg"));
			sfxCache.put(soundFile, s);
		}
		s.play(0.3f);
	}

	public static void playMusic(String soundFile) {
		Sound s;
		if(soundFile==null){
			soundFile = "level";
			System.err.println("soundtrack was null");
		}else if(soundFile.equals("null")){
			soundFile = "level";
			System.err.println("soundtrack was 'null'");
		}
		
		if(musicCache.containsKey(soundFile)){
			s = musicCache.get(soundFile);
		}else{
			s = Gdx.audio.newSound(Gdx.files.internal("data/audio/music/"+soundFile+".ogg"));
			musicCache.put(soundFile, s);
		}
		s.loop(0.15f);
	}
	
	/**
	 * removes music from the cache
	 * @param soundFile
	 * @return true if the soundFile was previously cached
	 */
	public static boolean removeMusic(String soundFile) {
		Sound s;
		if(musicCache.containsKey(soundFile)){
			s = musicCache.remove(soundFile);
			s.dispose();
			return true;
		}else{
			//sound not found
			return false;
		}
	}
	
}
