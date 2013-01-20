package com.projecthawkthorne.client;

import java.net.DatagramPacket;
import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.tests.utils.OrthoCamController;
import com.projecthawkthorne.client.audio.AudioCache;
import com.projecthawkthorne.client.display.Assets;
import com.projecthawkthorne.client.display.Node;
import com.projecthawkthorne.client.display.Player;
import com.projecthawkthorne.socket.Client;


public class HawkthorneGame extends Game {
	Client client = Client.getSingleton();
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	private TiledMap map;
	private TileAtlas atlas;
	private TileMapRenderer tileMapRenderer;
	private OrthographicCamera cam;
	private OrthoCamController camController;
	private Vector2 maxCamPosition = new Vector2();

	@Override
	public void create() {
		Assets.load();
		
		long startTime, endTime;
		font = new BitmapFont();
		font.setColor(Color.RED);
		Gdx.files.internal(Node.IMAGES_FOLDER + "defaultObject.png");

		spriteBatch = new SpriteBatch();

		final String path = "data/maps/";
		
		//TODO:remove town default
		String mapname = "town";

		
		FileHandle mapHandle = Gdx.files.internal(path + mapname + ".tmx");
		FileHandle baseDir = Gdx.files.internal("data/images/");

		startTime = System.currentTimeMillis();
		map = TiledLoader.createMap(mapHandle);
		endTime = System.currentTimeMillis();
		System.out.println("Loaded map in " + (endTime - startTime) + "ms");

		//atlas = new TileAtlas(map, baseDir);

		int blockWidth = map.tileWidth;
		int blockHeight = map.tileHeight;

		startTime = System.currentTimeMillis();

		//tileMapRenderer = new TileMapRenderer(map, atlas, blockWidth, blockHeight, 16, 16);
		endTime = System.currentTimeMillis();
		System.out.println("Created cache in " + (endTime - startTime) + "mS");

//		for (TiledObjectGroup group : map.objectGroups) {
//			for (TiledObject object : group.objects) {
//				// TODO: Draw sprites where objects occur
//				System.out.println("Object " + object.type+"."+object.name + " x,y = " + object.x + "," + object.y + " width,height = "
//					+ object.width + "," + object.height);
//			}
//		}

		//float aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
		//cam = new OrthographicCamera(100f * aspectRatio, 100f);
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		float camX = 0;//this.client.players.get(this.client.getEntity()).x;
		float camY = 0;//this.client.players.get(this.client.getEntity()).y;
		
		cam.position.set(camX, camY, 40);
		//cam.position.set(tileMapRenderer.getMapWidthUnits() / 2, tileMapRenderer.getMapHeightUnits() / 2, 0);
		//camController = new OrthoCamController(cam);
		//Gdx.input.setInputProcessor(camController);

		maxCamPosition.set(map.width,map.height);

	}

	@Override
	public void resize(int width, int height) {
		//spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}

	@Override
	public void render() {
		float camX;
		float camY;
		Gdx.gl.glClearColor( 0, 0, 0.2f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		try{
			Player player = this.client.players.get(this.client.getEntity());
			camX = player.x;
			camY = player.y;
		}catch(Exception e){
			camX = 0;
			camY = 0;
		}
		
		cam.position.set(camX, camY, 40);
		client.update();
		
		//receive a new bundle
		DatagramPacket bundle = client.receive();

		while(bundle!=null){
			//process bundle if necessary
			processBundle(bundle);
			bundle = client.receive();
		}

		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		client.draw(spriteBatch);
		spriteBatch.end();
		//Gdx.gl.glEnable(GL10.GL_CULL_FACE);
	}

	private void processBundle(DatagramPacket bundle) {
		if(bundle==null){return;}
		byte[] msg = bundle.getData();
		String[] tokens = new String(msg).split("\\s+", 3);
		String entity = tokens[0];
		String cmd = tokens[1];
		String params = tokens[2].trim();
		if(cmd.equals("updatePlayer")){
			Player p = Player.unpack(params);
			this.client.players.put(p.id, p);
		}else if(cmd.equals("updateObject")){
			Node n = Node.unpack(params);
			HashMap<String, Node> levelObjs = this.client.world.get(n.levelName);
			if(levelObjs==null){
				levelObjs = this.client.world.put(n.levelName, new HashMap<String,Node>());
			}
			levelObjs.put(n.id, n);
	    }else if(cmd.equals("stateSwitch")){
			String[] chunks = params.split(" ");
			String fromLevel = chunks[0];
			String toLevel = chunks[1];
			if(entity==this.client.getEntity()){
				stateSwitch(fromLevel,toLevel);
			}else{
				//TODO:confirm it's a player
				this.client.players.get(entity).levelName = toLevel;
			}
		}else if(cmd.equals("sound")){
			AudioCache.play(params);
		}
		
	}

	private void stateSwitch(String fromLevel, String toLevel) {
		// TODO Auto-generated method stub
		this.client.setLevel(toLevel);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
