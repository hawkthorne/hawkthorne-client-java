/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projecthawkthorne.client.display;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
	public static final String MAPS_OUTPUT_FOLDER = "data/packs/";
	private float x;
	private float y;
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
	private final long creationTime = System.currentTimeMillis();
	private long lastUpdate = System.currentTimeMillis();

	/**
	 * @param type
	 * @param name
	 */
	public Node(String type, String name) {	
		this.name = name;
		this.type = type;
	}

	/**
	 * draws the node if the type,name, and state are available
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		Animation anim;
		try{
			if(this instanceof Player){
				Player player = (Player) this;
				anim = Assets.characters.get(player.name).
						get(player.character.costume).
						get(player.getState());
			}else{
				anim = Assets.nodes.get(this.type).
						get(this.name).
						get(this.getState());
			}
			long nowTime = Node.getCurrentTime();
			long thenTime = this.getCreationTime();
			float stateTime = Node.convertToSeconds(nowTime-thenTime);
			TextureRegion tr = anim.getKeyFrame(stateTime);

			if(this.direction==Direction.LEFT){
				batch.draw(tr, this.x, this.y+tr.getRegionHeight(), tr.getRegionWidth(),-tr.getRegionHeight());
			}else{
				batch.draw(tr, this.x+tr.getRegionWidth(), this.getY()+tr.getRegionHeight(), -tr.getRegionWidth(), -tr.getRegionHeight());
			}
		}catch(NullPointerException e){
			System.err.println(this.id);
			System.err.println(this.type);
			System.err.println(this.name);
			if(this instanceof Player){
				Player player = (Player) this;
				System.err.println("> "+player.character.costume);
			}
			System.err.println(this.getState());
			System.err.println();
		}
		if(this instanceof Player){
			Player player = (Player) this;
			batch.draw(player.getIndicator(), x+player.width/2-player.getIndicator().getWidth()/2,this.y-10);
		}
		//TODO:instantiate the font once
//		BitmapFont font = new BitmapFont(true);

		//write object details for debugging
//		String tmp = this.getClass().getSimpleName() + "\n"
//				+ this.id + "\n"
//				+ this.name + "\n"
//				+ this.type;
//		font.drawMultiLine(batch, tmp, this.x, this.y+30);
	}

	private static float convertToSeconds(long ms) {
		return ms/1000.0f;
	}


	private long getCreationTime() {
		return creationTime;
	}


	/**
	 * current time since Jan 1, 1970 in milliseconds
	 * @return
	 */
	private static long getCurrentTime() {
		return System.currentTimeMillis();
	}


	public static Node unpack(HashMap<String, HashMap<String, Node>> world, String params) {
		String levelName = null;
		String id = null;
		float x = 0;
		float y = 0;
		String state = "default";
		Direction direction = Direction.RIGHT;
		int position = 1;
		String name = null;
		String type = null;
		float width;
		float height;

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
			}else if(argName.equals("width")){
				width = Float.parseFloat(argValue);
				//System.err.println("width is currently unused");
			}else if(argName.equals("height")){
				height = Float.parseFloat(argValue);
				//System.err.println("width is currently unused");
			}else{
				throw new UnsupportedOperationException("argName=="+argName+", argValue=="+argValue);
			}

		}
		
		HashMap<String, Node> levelObjs = world.get(levelName);
		Node n = null;
		n = levelObjs.get(id);
		if(n==null){
			n = new Node(type,name);
			n.id = id;
			levelObjs.put(id, n);
		}

		n.levelName = levelName;
		n.setX(x);
		n.setY(y);
		n.state = state;
		n.direction = direction;
		n.position = position;
		n.name = name;
		n.type = type;
		n.resetUpdateTime();
		return n;
	}


	private void resetUpdateTime() {
		this.lastUpdate = System.currentTimeMillis();
	}
	public long getLastUpdate(){
		return this.lastUpdate;
	}

	public String getState() {
		return state;
	}


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}
}
