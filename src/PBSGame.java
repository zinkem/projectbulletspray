package pbs;

import jig.engine.*;
import jig.engine.hli.*;
import jig.engine.physics.*;

public class PBSGame extends ScrollingScreenGame {

    public static int SCREEN_WIDTH = 640;
    public static int SCREEN_HEIGHT = 480;
    public static String SPRITE_SHEET = "resources/pbs-spritesheet.png";

    ResourceFactory rf;

    Entity e;
    BodyLayer<Body> l;

    public PBSGame(){
	super(SCREEN_WIDTH, SCREEN_HEIGHT, false);
	
	rf = ResourceFactory.getFactory();
	rf.loadResources("resources/", "pbs-resources.xml");
	
	l = new AbstractBodyLayer.IterativeUpdate<Body>();
	e = Entity.getWavyMover(SPRITE_SHEET + "#generic_ship");
	l.add(e);
	gameObjectLayers.add(l);
	
    }

    public static void main(String[] args){

	PBSGame game = new PBSGame();
	game.run();
    }
}