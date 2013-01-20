package com.projecthawkthorne.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GLU;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.projecthawkthorne.client.display.Character;
import com.projecthawkthorne.client.display.Node;
import com.projecthawkthorne.client.display.Player;


public class Client {

    private DatagramSocket clientSocket;
	private String entity;
	public HashMap<String,Player> players;
	//private HashMap<String, Character> characters;
	private static InetAddress serverIp;
	public HashMap<String,HashMap<String,Node>> world;
	
    //TODO: create overworld
	private String level = "town";

    //private byte[] receiveData = new byte[1024];
    //private byte[] sendData = new byte[1024];
    private static final Client singleton= new Client();
	private static int serverPort;
	private static Character clientCharacter;
        
	public HashMap<Integer,Boolean> keyDown = new HashMap<Integer,Boolean>();
	/**
	 * private because this is invoked as a singleton
	 * @param port
	 */
    private Client() {
        try {
            this.clientSocket = new DatagramSocket();
            Client.serverIp = InetAddress.getByName("localhost");
            Client.serverPort = 12345;
            this.entity = ("player"+Math.round(Math.random()*(99999)));
            this.players = new HashMap<String,Player>();
            //this.characters = new HashMap<String,Character>();
            this.world = new HashMap<String,HashMap<String,Node>>();
            
            initKeys();
            
            clientCharacter = new Character();
            String message = this.getEntity()+" register "+clientCharacter.name+" "+clientCharacter.costume;
            this.send(message);
            this.players.put(this.entity, new Player(clientCharacter.name,clientCharacter.costume));
            

            //Client should only assign his first level by himself, the rest
            this.level = "town";
            this.world.put(this.level, new HashMap<String,Node>());
            message = this.getEntity()+" enterLevel "+level;
            this.send(message);
            message = this.getEntity()+" update "+level;
            this.send(message);
            
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
        	Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

	private void initKeys() {
		// TODO Auto-generated method stub

        keyDown.put(Keys.LEFT, false);
        keyDown.put(Keys.RIGHT, false);
        keyDown.put(Keys.UP, false);
        keyDown.put(Keys.DOWN, false);
        keyDown.put(Keys.SPACE, false);
        keyDown.put(Keys.SHIFT_LEFT, false);
        keyDown.put(Keys.ESCAPE, false);
        keyDown.put(Keys.ENTER, false);

	}

	/**
     * returns one identical server every time
     * @return 
     */
    public static Client getSingleton(){
        return singleton;
    }
    
    /**
     * receives a UDP datagram from a server
     * @return datagram or null if none are found
     */
    public DatagramPacket receive() {
    	try{
    		byte[] receiveData = new byte[1024];
    		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    		clientSocket.setSoTimeout(17);// ~1/60 seconds
    		clientSocket.receive(receivePacket);
    		String modifiedSentence = new String(receivePacket.getData());
    		System.out.println("FROM SERVER: '" + modifiedSentence.trim()+"'");
    		//clientSocket.close();

    		return receivePacket;
    	}catch(SocketTimeoutException e){
    		return null;
    	}catch(Exception e){
    		return null;
    	}
    }

    /**
     * send a message in the following format:
     *      (entity) (cmd) (params)
     * @param message formatted message to send to the server
     * @return true if the message was sent
     * @throws IOException 
     */
    public boolean send(String message){
    	try{
    		byte[] sendData = new byte[1024];
    		sendData = message.getBytes();
    		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, Client.serverIp, Client.serverPort);
    		clientSocket.send(sendPacket);
    		System.out.println("TO SERVER: '" + message+"'");
    		//clientSocket.close();
    		return true;
    	}catch(Exception e){
    		return false;
    	}
    }

	public String getEntity() {
		return entity;
	}

	public void draw(SpriteBatch batch) {

		if(this.world==null){
			throw new NullPointerException("world can't be null");
		}
		if(this.level==null){
			throw new NullPointerException("level can't be null");
		}
		if(this.world.get(this.level)==null){
			throw new NullPointerException("world has no level by the name: "+this.level);
		}
		Iterator<Node> nit = this.world.get(this.level).values().iterator();
		while(nit.hasNext()){
			Node n = nit.next();
			n.draw(batch);
		}
		
		Iterator<Player> pit = this.players.values().iterator();
		while(pit.hasNext()){
			Player p = pit.next();
			if(p.levelName.equals(this.level)){
				p.draw(batch);
			}
		}
	}

	public void update() {
		// TODO Auto-generated method stub
		String message = String.format("%s %s %s", entity, "update", this.level);
		this.send(message);
		
		updateKeys();
		
	}

	/**
	 * sends messages to the server indicating a new keypress or
	 * release
	 */
	private void updateKeys() {
		// TODO Auto-generated method stub

		String message;
		if(keyDown.get(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.LEFT)){
        	keyDown.put(Keys.LEFT, false);
    		message = String.format("%s %s %s", this.entity, "keyreleased", "LEFT");
    		this.send(message);
        }else if(!keyDown.get(Keys.LEFT) && Gdx.input.isKeyPressed(Keys.LEFT)){
        	keyDown.put(Keys.LEFT, true);
    		message = String.format("%s %s %s", this.entity, "keypressed", "LEFT");
    		this.send(message);
        }
		
		if(keyDown.get(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.RIGHT)){
        	keyDown.put(Keys.RIGHT, false);
    		message = String.format("%s %s %s", this.entity, "keyreleased", "RIGHT");
    		this.send(message);
        }else if(!keyDown.get(Keys.RIGHT) && Gdx.input.isKeyPressed(Keys.RIGHT)){
        	keyDown.put(Keys.RIGHT, true);
    		message = String.format("%s %s %s", this.entity, "keypressed", "RIGHT");
    		this.send(message);
        }
		
		if(keyDown.get(Keys.UP) && !Gdx.input.isKeyPressed(Keys.UP)){
        	keyDown.put(Keys.UP, false);
    		message = String.format("%s %s %s", this.entity, "keyreleased", "UP");
    		this.send(message);
        }else if(!keyDown.get(Keys.UP) && Gdx.input.isKeyPressed(Keys.UP)){
        	keyDown.put(Keys.UP, true);
    		message = String.format("%s %s %s", this.entity, "keypressed", "UP");
    		this.send(message);
        }

		if(keyDown.get(Keys.DOWN) && !Gdx.input.isKeyPressed(Keys.DOWN)){
        	keyDown.put(Keys.DOWN, false);
    		message = String.format("%s %s %s", this.entity, "keyreleased", "DOWN");
    		this.send(message);
        }else if(!keyDown.get(Keys.DOWN) && Gdx.input.isKeyPressed(Keys.DOWN)){
        	keyDown.put(Keys.DOWN, true);
    		message = String.format("%s %s %s", this.entity, "keypressed", "DOWN");
    		this.send(message);
        }
		
		
		if(keyDown.get(Keys.SPACE) && !Gdx.input.isKeyPressed(Keys.SPACE)){
        	keyDown.put(Keys.SPACE, false);
    		message = String.format("%s %s %s", this.entity, "keyreleased", "JUMP");
    		this.send(message);
        }else if(!keyDown.get(Keys.SPACE) && Gdx.input.isKeyPressed(Keys.SPACE)){
        	keyDown.put(Keys.SPACE, true);
    		message = String.format("%s %s %s", this.entity, "keypressed", "JUMP");
    		this.send(message);
        }

		if(keyDown.get(Keys.SHIFT_LEFT) && !Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
        	keyDown.put(Keys.SHIFT_LEFT, false);
    		message = String.format("%s %s %s", this.entity, "keyreleased", "ACTION");
    		this.send(message);
        }else if(!keyDown.get(Keys.SHIFT_LEFT) && Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
        	keyDown.put(Keys.SHIFT_LEFT, true);
    		message = String.format("%s %s %s", this.entity, "keypressed", "ACTION");
    		this.send(message);
        }

		if(keyDown.get(Keys.ESCAPE) && !Gdx.input.isKeyPressed(Keys.ESCAPE)){
        	keyDown.put(Keys.ESCAPE, false);
    		message = String.format("%s %s %s", this.entity, "keyreleased", "START");
    		this.send(message);
        }else if(!keyDown.get(Keys.ESCAPE) && Gdx.input.isKeyPressed(Keys.ESCAPE)){
        	keyDown.put(Keys.ESCAPE, true);
    		message = String.format("%s %s %s", this.entity, "keypressed", "START");
    		this.send(message);
        }

		if(keyDown.get(Keys.ENTER) && !Gdx.input.isKeyPressed(Keys.ENTER)){
        	keyDown.put(Keys.ENTER, false);
    		message = String.format("%s %s %s", this.entity, "keyreleased", "SELECT");
    		this.send(message);
        }else if(!keyDown.get(Keys.ENTER) && Gdx.input.isKeyPressed(Keys.ENTER)){
        	keyDown.put(Keys.ENTER, true);
    		message = String.format("%s %s %s", this.entity, "keypressed", "SELECT");
    		this.send(message);
        }

	}

	public void setLevel(String toLevel) {
		// TODO Auto-generated method stub
		this.level = toLevel;
	}
}