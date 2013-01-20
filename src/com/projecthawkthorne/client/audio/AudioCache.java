package com.projecthawkthorne.client.audio;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioCache {
    private static Map<String,Sound> cache = new HashMap<String,Sound>();

	public static void play(String soundFile){
		Sound s;
		if(cache.containsKey(soundFile)){
			s = cache.get(soundFile);
		}else{
			s = Gdx.audio.newSound(Gdx.files.internal("data/audio/sfx/"+soundFile+".ogg"));
			cache.put(soundFile, s);
		}
		s.play();
	}
}
