package pbs;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;

import jig.engine.*;
import jig.engine.hli.*;
import jig.engine.physics.*;
import jig.engine.util.*;

import pbs.Level.Layer;
import pbs.Entity.*;
import pbs.Weapons.*;
import pbs.Updater.*;
import pbs.parser.*;
import pbs.Renders.*;

public class PBSGame extends ScrollingScreenGame {
    
    public static int SCREEN_WIDTH = 640;
    public static int SCREEN_HEIGHT = 480;
    public static int X_MID = SCREEN_WIDTH / 2;
    public static int Y_MID = SCREEN_HEIGHT / 2;
    public static long FRAME_SIZE = 16;
    public static String SPRITE_SHEET = "resources/pbs-spritesheet.png";
    
    ResourceFactory rf;
    
    Level levelData;

    EntityFactory ef;
    
    Entity player;

    String currentLevel;
    
    // hud variables
    protected FontResource hudFont;
    
    public PBSGame() {
	super(SCREEN_WIDTH, SCREEN_HEIGHT, false);
	
	ef = new EntityFactory();
	
	rf = ResourceFactory.getFactory();
	rf.loadResources("resources/", "pbs-resources.xml");
	Font sFont = new Font("Sans Serif", Font.PLAIN, 24);
	try {
	    sFont = Font.createFont(Font.TRUETYPE_FONT, 
				    new File("resources/PrStart.ttf"));
		sFont = sFont.deriveFont(24f);
	} catch (FontFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//	hudFont = rf.getFontResource(new Font("Sans Serif", Font.PLAIN, 24), Color.white, null);
	hudFont = rf.getFontResource(sFont, Color.white, null);

	currentLevel = "resources/test.lvl";
	resetLevel();
	
	//e = ef.get_bullet_arc(new Vector2D(200, 200), new Vector2D(10, 0), -0.01);
	//e = ef.get_yocil(new Vector2D(400, 100), new Vector2D(0, 5));
	Entity e = ef.Boss(new Vector2D(400, 200));
	e.setCustomRender(new SetStatic(50.0));
	levelData.add(e, Layer.STATIC);
	//e = ef.get_directed(new Vector2D(400, 0), new Vector2D(-5,5), plr,  );
	/*
	levelData.add(e, Layer.ENEMY);

	e = ef.get_chaser(new Vector2D(400,100), new Vector2D(-5, 5), plr);
	// e = ef.target_point(new Vector2D(400,100), new Vector2D(1,1), 
	levelData.add(e, Layer.ENEMY);
	*/



	GameClock.TimeManager tm = new GameClock.SleepIfNeededTimeManager(60.0);
	theClock.setTimeManager(tm);
    }

    // this method renders the hud
    public void render(RenderingContext rc) {
	super.render(rc);

	String message = "High Score: " + levelData.getScore();
	int x = X_MID - hudFont.getStringWidth(message) / 2;
	int y = 10;
	hudFont.render(message, rc, AffineTransform.getTranslateInstance(x, y));

	message = "Score: ";
	x = X_MID - hudFont.getStringWidth(message);
	y = SCREEN_HEIGHT - hudFont.getHeight() - 10;
	hudFont.render(message, rc, AffineTransform.getTranslateInstance(x, y));

	message = "" + levelData.getScore();
	x = X_MID;
	hudFont.render(message, rc, AffineTransform.getTranslateInstance(x, y));

    }

    public void update(long deltaMs) {

	//centerOnPoint(levelData.getCam()); // center on level camera
	Vector2D topleft = screenToWorld(new Vector2D(0, 0));
	Vector2D botright = screenToWorld(new Vector2D(SCREEN_WIDTH, SCREEN_HEIGHT));

	centerOnPoint(levelData.getCam());
	pushPlayerToBounds(topleft, botright);
	player.setPosition(player.getPosition().translate(levelData.getScrollSpeed()
							  .scale(deltaMs/100.0)));
	levelData.update(FRAME_SIZE, topleft, botright);

	//if level complete, get next level
	if(levelData.levelComplete()){
	    currentLevel = levelData.getNextLevel();
	    resetLevel();
	}

	//if player dead, reset current level
	if(player.alive() == false){
	    resetLevel();
	}
	    
    }

    public void resetLevel(){
	LevelParser lp = new LevelParser(currentLevel);
	levelData = lp.createLevel();
	
	player = new Entity(SPRITE_SHEET + "#defender2");
	player.setPosition(new Vector2D(300, 300));
	player.setCustomUpdate(new KeyboardControls(keyboard));
	player.setCustomWeapon(new FriendlySpread());
	levelData.add(player, Layer.PLAYER);

	gameObjectLayers.clear();
	gameObjectLayers = levelData.getLayers();
    }

    public void pushPlayerToBounds(Vector2D tl, Vector2D br){

	Vector2D p = player.getPosition();
	double newx = p.getX();
	double newy = p.getY();

	if(p.getX() < tl.getX()){
	    newx = tl.getX();
	} else if(p.getX() + player.getWidth()-1 > br.getX()){
	    newx = br.getX() - player.getWidth()-1;
	}

	if(p.getY() < tl.getY()){
	    newy = tl.getY();
	} else if(p.getY() + player.getHeight()-1 > br.getY()){
	    newy = br.getY() - player.getHeight()-1;

	}

	player.setPosition(new Vector2D(newx, newy));
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

	    if (fire) {
		e.shoot(levelData, deltaMs);
	    }

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
	}
    }

    public static void main(String[] args) {

	PBSGame game = new PBSGame();
	game.run();

    }

}