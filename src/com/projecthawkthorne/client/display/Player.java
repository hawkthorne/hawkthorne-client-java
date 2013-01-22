package com.projecthawkthorne.client.display;

import java.util.HashMap;

import com.projecthawkthorne.client.Direction;


public class Player extends Node{
	Character character;
	protected String type = "player";
	//TODO: change constructor to take a name and a costume
	public Player(String type, String name) {
		super("player", name);
		character = new Character(name,"base");
		// TODO Auto-generated constructor stub
	}
	public static Player unpack(HashMap<String,Player> players, String params){
		// TODO remove player/node unpack redundancy
		String levelName = null;
		String id = null;
		float x = 0;
		float y = 0;
		String state = "default";
		Direction direction = Direction.RIGHT;
		int position = 1;
		String name = null;
		String costume = null;
		String type = null;
		
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
				levelName = argValue;
			}else if(argName.equals("id")){
				id = argValue;
			}else if(argName.equals("x")){
				x = Float.parseFloat(argValue);
			}else if(argName.equals("y")){
				y = Float.parseFloat(argValue);
			}else if(argName.equals("state")){
				state = argValue;
			}else if(argName.equals("direction")){
				direction = Direction.valueOf(argValue.toUpperCase());
			}else if(argName.equals("position")){
				position = Integer.parseInt(argValue);
			}else if(argName.equals("name")){
				name = argValue;
			}else if(argName.equals("type")){
				type = argValue;
			}else if(argName.equals("costume")){
				costume = argValue;
			}else{
				throw new UnsupportedOperationException("argName=="+argName+", argValue=="+argValue);
			}

		}

		Player p = players.get(id);
		if(p==null){
			p = new Player(type,name);
			players.put(id, p);
		}

		p.levelName = levelName;
		p.x = x;
		p.y = y;
		p.state = state;
		p.direction = direction;
		p.position = position;
		p.name = name;
		p.type = type;
		
		p.character.costume = costume;
		p.character.name = name;
		
		return p;
	}
}
