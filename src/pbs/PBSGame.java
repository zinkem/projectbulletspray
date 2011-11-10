package pbs;

import pbs.Level.Layer;

import jig.engine.*;
import jig.engine.hli.*;
import jig.engine.physics.*;
import jig.engine.util.*;


public class PBSGame extends ScrollingScreenGame {

    public static int SCREEN_WIDTH = 640;
    public static int SCREEN_HEIGHT = 480;
    public static String SPRITE_SHEET = "resources/pbs-spritesheet.png";

    ResourceFactory rf;

    Level levelData;

    Entity e;
    BodyLayer<Body> l;

    public PBSGame(){
	super(SCREEN_WIDTH, SCREEN_HEIGHT, false);
	
	rf = ResourceFactory.getFactory();
	rf.loadResources("resources/", "pbs-resources.xml");
	
	l = new AbstractBodyLayer.IterativeUpdate<Body>();
	e = Entity.getWavyMover(SPRITE_SHEET + "#generic_ship");
	l.add(e);
	e = Entity.getWavyMover(SPRITE_SHEET + "#generic_ship");
	e.setPosition(new Vector2D(400,400));
	l.add(e);


	levelData = new Level();
	levelData.add(e, Layer.PLAYER);

	gameObjectLayers = levelData.getLayers();

	GameClock.TimeManager tm = new GameClock.SleepIfNeededTimeManager(60.0);
	theClock.setTimeManager(tm);
    }

    public void update(long deltaMs){
	l.update(deltaMs);
    }

    public static void main(String[] args){

	PBSGame game = new PBSGame();
	game.run();
    }
}