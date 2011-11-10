package pbs;

import java.util.*;

import jig.engine.*;

public class Level {

    public static enum Layer { 
	BACKGROUND, 
	    STATIC, 
	    ENEMY, 
	    HOSTILE, 
	    FRIENDLY, 
	    PLAYER, 
	    FOREGROUND }

    //list of layers
    ArrayList<ViewableLayer> allTheLayers;

    //list of collision handlers 
    ArrayList<CollisionHandler> collisionHandlers;

    public Level(){
	collisionHandlers = new ArrayList<CollisionHandler>();
	
	allTheLayers = new ArrayList<ViewableLayer>();
	
	for(int i = 0; i < 7; i++){
	    //allTheLayers.add(new ViewableLayer());
	}
	

    }

    //setters
    public void add(Entity e, Layer l){
	System.out.println(l.ordinal());
	//allTheLayers.get(l.ordinal()).add(e);
    }

    //getters
    public List getLayers(){
	return allTheLayers;
    }

	

    //data init
    public void initLevel(){
	
    }

    public void update(long deltaMs){
	//call update on each layer

	//check for collisions
    }

}