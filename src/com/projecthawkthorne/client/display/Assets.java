/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.projecthawkthorne.client.display;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture background;
	public static TextureRegion backgroundRegion;

	public static Texture items;
	public static TextureRegion mainMenu;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScoresRegion;
	public static TextureRegion logo;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion spring;
	public static TextureRegion castle;
	public static Animation coinAnim;
	public static Animation bobJump;
	public static Animation bobFall;
	public static TextureRegion bobHit;
	public static Animation squirrelFly;
	public static TextureRegion platform;
	public static Animation brakingPlatform;
	public static BitmapFont font;

	public static Music music;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound clickSound;
	
	private static Texture enemyTexture;
	//key is a state
	public static Map<String, Animation> acorn;
	public static Map<String, Map<String, Animation>> enemy;
	public static Map<String, Map<String, Map<String, Animation>>> nodes;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		//create blank nodes map
		nodes = new HashMap<String, Map<String, Map<String,Animation>>>();
		
		//create each enemy
		enemyTexture = loadTexture("data/images/enemies.png");
		acorn = new HashMap<String,Animation>();
		acorn.put("default", new Animation(0.2f, 
				new TextureRegion(enemyTexture, 40, 0, 20, 20),
				new TextureRegion(enemyTexture, 60, 0, 20, 20), 
				new TextureRegion(enemyTexture, 80, 0, 20, 20)));
		acorn.put("rage", new Animation(0.2f,
				new TextureRegion(enemyTexture, 140, 0, 20, 20),
				new TextureRegion(enemyTexture, 160, 0, 20, 20), 
				new TextureRegion(enemyTexture, 180, 0, 20, 20)));
		//create enemy list
		enemy = new HashMap<String, Map<String, Animation>>();
		
		//add each enemy to the list
		enemy.put("acorn",acorn);

		//add each node type to the node list
		nodes.put("enemy",enemy);
    }
	
	public static void playSound (Sound sound) {
		sound.play(1);
	}
}
