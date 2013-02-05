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
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	private static Texture enemyTexture;
	//key is a state
	private static Map<String, Animation> acorn;
	private static Map<String, Animation> hippy;
	private static Map<String, Map<String, Animation>> enemy;
	public static Map<String, Map<String, Map<String, Animation>>> nodes;

	private static Texture abedBaseTexture;
	private static Map<String, Animation> base;
	private static Map<String, Map<String, Animation>> abed;
	public static Map<String, Map<String, Map<String, Animation>>> characters;
	private static Texture materialTexture;
	private static HashMap<String, Map<String, Animation>> material;
	private static HashMap<String, Animation> leaf;
	private static HashMap<String, Animation> rock;
	private static HashMap<String, Animation> stick;
	public static HashMap<String, Animation> standard;
	private static Texture defaultTexture;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		//
		standard = new HashMap<String,Animation>();
		defaultTexture = loadTexture("data/images/defaultTexture.png");
		
		//create blank nodes map
		nodes = new HashMap<String, Map<String, Map<String,Animation>>>();
		
		//create enemy list
		enemyTexture = loadTexture("data/images/enemies.png");
		enemy = new HashMap<String, Map<String, Animation>>();

		//create each enemy
		// 1) make new map
		// 2) add animation states to it
		// 3) add the map to the enemy mapp
		acorn = new HashMap<String,Animation>();
		acorn.put("default", new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.LOOP,
				new TextureRegion(enemyTexture, 40, 0, 20, 20),
				new TextureRegion(enemyTexture, 60, 0, 20, 20),
				new TextureRegion(enemyTexture, 80, 0, 20, 20)
		));
		acorn.put("attack", new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.LOOP,
				new TextureRegion(enemyTexture, 140, 0, 20, 20),
				new TextureRegion(enemyTexture, 160, 0, 20, 20), 
				new TextureRegion(enemyTexture, 180, 0, 20, 20)
		));
		enemy.put("acorn",acorn);

		hippy = new HashMap<String,Animation>();
		hippy.put("default", new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.LOOP,
				new TextureRegion(enemyTexture, 94, 118, 47, 47),
				new TextureRegion(enemyTexture, 141, 118, 47, 47)
		));
		hippy.put("attack", new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.LOOP,
				new TextureRegion(enemyTexture, 0, 118, 47, 47),
				new TextureRegion(enemyTexture, 47, 118, 47, 47)
		));
		hippy.put("dying", new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(enemyTexture, 192, 118, 47, 47)
		));
		enemy.put("hippy",hippy);

		
		materialTexture = loadTexture("data/images/materials.png");
		material = new HashMap<String, Map<String, Animation>>();
		


		leaf = new HashMap<String,Animation>();
		leaf.put("default", new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(materialTexture, 0, 0, 24, 24)
		));
		material.put("leaf", leaf);
		rock = new HashMap<String,Animation>();
		rock.put("default", new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(materialTexture, 24, 0, 24, 24)
		));
		material.put("rock", rock);
		stick = new HashMap<String,Animation>();
		stick.put("default", new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(materialTexture, 48, 0, 24, 24)
		));
		material.put("stick", stick);

		
		
		//add each node type to the node list
		nodes.put("enemy",enemy);
		nodes.put("material",material);
		
		
		
		abedBaseTexture = loadTexture("data/images/characters/abed/base.png");

		characters = new HashMap<String,Map<String,Map<String,Animation>>>();
		abed = new HashMap<String,Map<String,Animation>>();
		base = new HashMap<String,Animation>();
		base.put("idle", new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(abedBaseTexture, 0, 0, 48, 48)
		));
		base.put("walk", new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.LOOP,
				new TextureRegion(abedBaseTexture, 48, 0, 48, 48),
				new TextureRegion(abedBaseTexture, 96, 0, 48, 48),
				new TextureRegion(abedBaseTexture, 144, 0, 48, 48)
		));
		base.put("jump", new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(abedBaseTexture, 288, 0, 48, 48)
		));
		base.put("crouch", new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(abedBaseTexture, 8*48, 2*48, 48, 48)
		));
		abed.put("base",base);
		characters.put("abed",abed);
		
		
		standard.put("node",new Animation(0.2f, com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(defaultTexture, 288, 0, 48, 48)
		));
    }
	
	public static void playSound (Sound sound) {
		sound.play(1);
	}

}
