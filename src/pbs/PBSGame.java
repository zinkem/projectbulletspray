package pbs;

import java.awt.event.*;



import jig.engine.*;
import jig.engine.hli.*;
import jig.engine.physics.*;
import jig.engine.util.*;

import pbs.Level.Layer;
import pbs.Entity.*;


public class PBSGame extends ScrollingScreenGame {

    public static int SCREEN_WIDTH = 640;
    public static int SCREEN_HEIGHT = 480;
    public static String SPRITE_SHEET = "resources/pbs-spritesheet.png";

    ResourceFactory rf;

    Level levelData;

    Entity e;

    public PBSGame(){
	super(SCREEN_WIDTH, SCREEN_HEIGHT, false);
	
	rf = ResourceFactory.getFactory();
	rf.loadResources("resources/", "pbs-resources.xml");

	levelData = new Level();

	e = Entity.getWavyMover(SPRITE_SHEET + "#generic_ship");
	levelData.add(e, Layer.ENEMY);
	e = Entity.getWavyMover(SPRITE_SHEET + "#generic_ship");
	e.setPosition(new Vector2D(400,400));
	e.setVelocity(new Vector2D(5, 0));
	levelData.add(e, Layer.ENEMY);

	e = new Entity(SPRITE_SHEET  + "#generic_ship");
	e.setPosition(new Vector2D(300, 300));
	e.setCustomUpdate(new KeyboardControls(keyboard));
	levelData.add(e, Layer.PLAYER);

	gameObjectLayers = levelData.getLayers();

	GameClock.TimeManager tm = new GameClock.SleepIfNeededTimeManager(60.0);
	theClock.setTimeManager(tm);
    }

    public void update(long deltaMs){
	levelData.update(deltaMs);
	centerOn(e); //method to use to centerOn any body
    }

    public static void main(String[] args){

	PBSGame game = new PBSGame();
	game.run();

    }


    protected class KeyboardControls implements CustomUpdate {
	Keyboard key;
	public KeyboardControls(Keyboard k){
	    key = k;	    
	}

	public void update(Entity e, long deltaMs){
	    boolean left = key.isPressed(KeyEvent.VK_LEFT);
	    boolean right = key.isPressed(KeyEvent.VK_RIGHT);
	    boolean up = key.isPressed(KeyEvent.VK_UP);
	    boolean down = key.isPressed(KeyEvent.VK_DOWN);
	    boolean reset = left && right;

	    boolean fire = key.isPressed(KeyEvent.VK_SPACE);

	    Vector2D pos = e.getPosition();

	    if(left && !right){
		e.setPosition(pos.translate(new Vector2D(-10, 0)));
	    }
	    
	    if(right && !left){
		e.setPosition(pos.translate(new Vector2D(10, 0)));
	    }
	    
	    if(up && !down){
		e.setPosition(pos.translate(new Vector2D(0, -10)));
	    }
	    
	    if(down && !up){
		e.setPosition(pos.translate(new Vector2D(0, 10)));
	    }
	}
    }
}