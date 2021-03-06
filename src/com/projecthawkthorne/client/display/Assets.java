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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	private static final String SRC_IMAGES = "data/images/";

	/** */
	public static Map<String, Texture> spriteCache = new HashMap<String, Texture>();

	/** top level map for all non-player modes */
	public static Map<String, Map<String, Map<String, Animation>>> nodes;

	private static Texture enemyTexture;
	private static Map<String, Map<String, Animation>> enemy;
	private static Map<String, Animation> acorn;
	private static Map<String, Animation> hippy;

	private static Texture materialTexture;
	private static HashMap<String, Map<String, Animation>> material;
	private static HashMap<String, Animation> leaf;
	private static HashMap<String, Animation> rock;
	private static HashMap<String, Animation> stick;

	private static Texture tokenTexture;
	private static HashMap<String, Map<String, Animation>> token;
	private static HashMap<String, Animation> coin;
	private static HashMap<String, Animation> health;

	/** top level map for all characters */
	public static Map<String, Map<String, Map<String, Animation>>> characters;
	private static Texture abedBaseTexture;
	private static Map<String, Map<String, Animation>> abed;
	private static Map<String, Animation> base;

	/** map for default sprites */
	public static HashMap<String, Animation> standard;
	private static Texture defaultTexture;

	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load() {
		//
		standard = new HashMap<String, Animation>();
		defaultTexture = loadTexture(SRC_IMAGES + "defaultTexture.png");

		// create blank nodes map
		nodes = new HashMap<String, Map<String, Map<String, Animation>>>();

		// create enemy list
		enemyTexture = loadTexture(SRC_IMAGES + "enemies.png");
		enemy = new HashMap<String, Map<String, Animation>>();

		// create each enemy
		// 1) make new map
		// 2) add animation states to it
		// 3) add the map to the enemy mapp
		acorn = new HashMap<String, Animation>();
		acorn.put("default", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.LOOP,
				new TextureRegion(enemyTexture, 40, 0, 20, 20),
				new TextureRegion(enemyTexture, 60, 0, 20, 20),
				new TextureRegion(enemyTexture, 80, 0, 20, 20)));
		acorn.put("attack", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.LOOP,
				new TextureRegion(enemyTexture, 140, 0, 20, 20),
				new TextureRegion(enemyTexture, 160, 0, 20, 20),
				new TextureRegion(enemyTexture, 180, 0, 20, 20)));
		enemy.put("acorn", acorn);

		hippy = new HashMap<String, Animation>();
		hippy.put("default", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.LOOP,
				new TextureRegion(enemyTexture, 94, 118, 47, 47),
				new TextureRegion(enemyTexture, 141, 118, 47, 47)));
		hippy.put("attack", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.LOOP,
				new TextureRegion(enemyTexture, 0, 118, 47, 47),
				new TextureRegion(enemyTexture, 47, 118, 47, 47)));
		hippy.put("dying", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(enemyTexture, 192, 118, 47, 47)));
		enemy.put("hippy", hippy);

		materialTexture = loadTexture(SRC_IMAGES + "materials.png");
		material = new HashMap<String, Map<String, Animation>>();

		leaf = new HashMap<String, Animation>();
		leaf.put("default", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(materialTexture, 0, 0, 24, 24)));
		material.put("leaf", leaf);
		rock = new HashMap<String, Animation>();
		rock.put("default", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(materialTexture, 24, 0, 24, 24)));
		material.put("rock", rock);
		stick = new HashMap<String, Animation>();
		stick.put("default", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(materialTexture, 48, 0, 24, 24)));
		material.put("stick", stick);

		tokenTexture = loadTexture(SRC_IMAGES + "tokens.png");
		token = new HashMap<String, Map<String, Animation>>();
		health = new HashMap<String, Animation>();
		health.put("default", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(materialTexture, 0, 0, 8, 8),
				new TextureRegion(materialTexture, 8, 0, 8, 8)));
		token.put("health", health);
		coin = new HashMap<String, Animation>();
		coin.put("default", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(tokenTexture, 0, 9, 13, 10),
				new TextureRegion(tokenTexture, 13, 9, 13, 10)));
		token.put("coin", coin);

		// add each node type to the node list
		nodes.put("enemy", enemy);
		nodes.put("material", material);
		nodes.put("token", token);

		abedBaseTexture = loadTexture(SRC_IMAGES + "characters/abed/base.png");

		characters = new HashMap<String, Map<String, Map<String, Animation>>>();
		abed = new HashMap<String, Map<String, Animation>>();
		base = new HashMap<String, Animation>();
		base.put("idle", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(abedBaseTexture, 0, 0, 48, 48)));
		base.put("walk", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.LOOP,
				new TextureRegion(abedBaseTexture, 48, 0, 48, 48),
				new TextureRegion(abedBaseTexture, 96, 0, 48, 48),
				new TextureRegion(abedBaseTexture, 144, 0, 48, 48)));
		base.put("jump", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(abedBaseTexture, 288, 0, 48, 48)));
		base.put("crouch", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(abedBaseTexture, 8 * 48, 2 * 48, 48, 48)));
		base.put("gaze", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(abedBaseTexture, 7 * 48, 0 * 48, 48, 48)));
		abed.put("base", base);
		characters.put("abed", abed);

		standard.put("node", new Animation(0.2f,
				com.badlogic.gdx.graphics.g2d.Animation.NORMAL,
				new TextureRegion(defaultTexture, 288, 0, 48, 48)));
	}

	public static void playSound(Sound sound) {
		sound.play(1);
	}

}
