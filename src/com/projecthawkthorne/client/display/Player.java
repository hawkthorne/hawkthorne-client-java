package com.projecthawkthorne.client.display;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.projecthawkthorne.client.Direction;


public class Player extends Node{
	Character character;
	public Player(String type, String name) {
		super("abed", "base");
		character = new Character("abed","base");
		// TODO Auto-generated constructor stub
	}
	public static Player unpack(String params){
		// TODO remove player/node unpack redundancy
		Player n=new Player(null,null);
		String[] nodeArgs = params.split("["+NULL+"]");

		for(int i=0; i<nodeArgs.length;i++){
			String[] chunks = nodeArgs[i].split(ONE);
			if(chunks.length!=3){
				throw new UnsupportedOperationException("incorrect argument amount");
			}
			String argType = chunks[0];
			String argName = chunks[1];
			String argValue = chunks[2];

			if(argName.equals("level")){
				n.levelName = argValue;
			}else if(argName.equals("id")){
				n.id = argValue;
			}else if(argName.equals("x")){
				n.x = Float.parseFloat(argValue);
			}else if(argName.equals("y")){
				n.y = Float.parseFloat(argValue);
			}else if(argName.equals("state")){
				n.state = argValue;
			}else if(argName.equals("direction")){
				n.direction = Direction.valueOf(argValue.toUpperCase());
			}else if(argName.equals("position")){
				n.position = Integer.parseInt(argValue);
			}else if(argName.equals("name")){
				n.name = argValue;
			}else if(argName.equals("type")){
				n.type = argValue;
			}else if(argName.equals("costume")){
				n.character.costume = argValue;
			}else if(argName.equals("name")){
				n.character.name = argValue;
			}else{
				throw new UnsupportedOperationException("argName=="+argName+", argValue=="+argValue);
			}

		}
		return n;
	}
}
