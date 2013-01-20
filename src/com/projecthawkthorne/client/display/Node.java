/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projecthawkthorne.client.display;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.projecthawkthorne.client.Direction;

/**
 *
 * @author Patrick
 */
public class Node {

	protected final static String ONE = "!";
	protected final static String NULL = "?";
	public static final String IMAGES_FOLDER = "data/images/";
	public static final String MAPS_FOLDER = "data/maps/";
	public float x;
	public float y;
	public float width;
	public float height;
	protected String state = "default";
	protected String type;
	protected String name;
	//!!!!!Warning if you change level from type Level to type String
	// this will be structurally different from the back end Node
	public String levelName;
	public int position; //frame position

	//protected SpriteBatch batch = new SpriteBatch();
	protected Texture objectTexture = null;// = new Texture(Gdx.files.internal(IMAGES_FOLDER + "defaultObject.png"));
	//private BitmapFont font;// = new BitmapFont();
	protected HashMap<String, String> properties;
	private Texture bboxTexture;// = new Texture(Gdx.files.internal(IMAGES_FOLDER + "boundingBox.png"));
	protected int srcX = 0;
	protected int srcY = 0;
	protected int srcWidth = 10;
	protected int srcHeight = 10;
	public String id;
	protected Direction direction;
	private TextureRegion objRegion;

	/**
	 * 
	 * @param type
	 * @param name
	 */
	public Node(String type, String name) {	
		this.name = name;
		this.type = type;
	}

	
	public void draw(SpriteBatch batch) {
		Animation anim;
		try{
		anim = Assets.nodes.get(this.type).
				get(this.name).
				get(this.state);
         		batch.draw(anim.getKeyFrame(1.0f), this.x, this.y);
		}catch(NullPointerException e){
			System.err.println(this.type);
			System.err.println(this.name);
			System.err.println(this.state);
			System.err.println();
		}
		//TODO:instantiate the font once
		BitmapFont font = new BitmapFont(true);

		//write object details for debugging
		String tmp = this.getClass().getSimpleName() + "\n"
				+ this.id + "\n"
				+ this.name + "\n"
				+ this.type;
		font.drawMultiLine(batch, tmp, this.x, this.y);
	}

	public static Node unpack(String params) {
		// TODO Auto-generated method stub
		Node n=new Node(null,null);
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
			}else{
				throw new UnsupportedOperationException("argName=="+argName+", argValue=="+argValue);
			}
			
		}
		return n;
	}


}
