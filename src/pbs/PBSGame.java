package pbs;

import java.awt.event.*;

import jig.engine.*;
import jig.engine.hli.*;
import jig.engine.physics.*;
import jig.engine.util.*;

import pbs.Level.Layer;
import pbs.Entity.*;

import pbs.Updater.*;
import pbs.parser.*;

public class PBSGame extends ScrollingScreenGame {

    public static int SCREEN_WIDTH = 640;
    public static int SCREEN_HEIGHT = 480;
    public static String SPRITE_SHEET = "resources/pbs-spritesheet.png";
    
    ResourceFactory rf;
    
    Level levelData;

    EntityFactory ef;
    long deltaMs;

    Entity e, plr;

    public PBSGame() {
	super(SCREEN_WIDTH, SCREEN_HEIGHT, false);

	deltaMs = (long) 10.0;
	ef = new EntityFactory();

	rf = ResourceFactory.getFactory();
	rf.loadResources("resources/", "pbs-resources.xml");

	LevelParser lp = new LevelParser("resources/test.lvl");
	lp.createLevel();

	levelData = new Level();

	plr = new Entity(SPRITE_SHEET + "#hex");
	plr.setPosition(new Vector2D(300, 300));
	plr.setCustomUpdate(new KeyboardControls(keyboard));
	levelData.add(plr, Layer.PLAYER);

	e = ef.get_bullet_arc(new Vector2D(200, 200), new Vector2D(10, 0),
			      -0.005);
	levelData.add(e, Layer.ENEMY);

	// e = ef.get_chaser(new Vector2D(400,100), new Vector2D(-5, 5), plr);
	// e = ef.target_point(new Vector2D(400,100), new Vector2D(1,1), new
	// Vector2D(0,0));
	// levelData.add(e, Layer.ENEMY);

	gameObjectLayers = levelData.getLayers();

	GameClock.TimeManager tm = new GameClock.SleepIfNeededTimeManager(60.0);
	theClock.setTimeManager(tm);
    }

    protected class KeyboardControls implements CustomUpdate {
	Keyboard key;

	public KeyboardControls(Keyboard k) {
	    key = k;
	}

	public void update(Entity e, long deltaMs) {

	    boolean left = key.isPressed(KeyEvent.VK_LEFT);
	    boolean right = key.isPressed(KeyEvent.VK_RIGHT);
	    boolean up = key.isPressed(KeyEvent.VK_UP);
	    boolean down = key.isPressed(KeyEvent.VK_DOWN);

	    boolean reset = left && right;

	    boolean fire = key.isPressed(KeyEvent.VK_SPACE);

	    Vector2D pos = e.getPosition();
	    e.setVelocity(new Vector2D(0, 0));

	    if (left && !right) {
		e.setVelocity(e.getVelocity().translate(new Vector2D(-30, 0)));
	    }

	    if (right && !left) {
		e.setVelocity(e.getVelocity().translate(new Vector2D(30, 0)));
	    }

	    if (up && !down) {
		e.setVelocity(e.getVelocity().translate(new Vector2D(0, -30)));
	    }

	    if (down && !up) {
		e.setVelocity(e.getVelocity().translate(new Vector2D(0, 30)));
	    }

	    e.setPosition(pos.translate(e.getVelocity().scale(deltaMs / 100.0)));
	    e.age += deltaMs;

	}
    }

    public void update(long deltaMs) {

	centerOnPoint(levelData.getCam()); // method to use to centerOn any body
	levelData.update(deltaMs, 
			 screenToWorld(new Vector2D(0,0)), 
			 screenToWorld(new Vector2D(SCREEN_WIDTH, SCREEN_HEIGHT)));

    }

    public static void main(String[] args) {

	PBSGame game = new PBSGame();
	game.run();

    }

}