package pbs;

import java.util.*;

import jig.engine.*;

public class Level {

    public static enum Layer { 
	BACKGROUND, 
	    STATIC, 
	    FX,
	    ENEMY, 
	    HOSTILE, 
	    FRIENDLY, 
	    PLAYER, 
	    FOREGROUND }

    public static int NUM_LAYERS = Layer.values().length;

    //list of layers
    ArrayList<EntityLayer> allTheLayers;

    //list of collision handlers 
    ArrayList<CollisionHandler> collisionHandlers;

    public Level(){
	collisionHandlers = new ArrayList<CollisionHandler>();
	allTheLayers = new ArrayList<EntityLayer>();

	for(int i = 0; i < NUM_LAYERS; i++){
	    //here we create a new empty layer for 
	    //each entity category
	    //enumerated in Layer
	    allTheLayers.add(new EntityLayer());
	}

	//after all layers are added, we can add collision handlers
	setupCollisionHandlers();
    }

    //setters
    public void add(Entity e, Layer l){
	System.out.println(l.ordinal());
	getLayer(l).add(e);
    }

    //getters
    public List getLayers(){
	return allTheLayers;
    }

    public EntityLayer getLayer(Layer l){
	return allTheLayers.get(l.ordinal());
    }

	
    public void setupCollisionHandlers(){
	collisionHandlers.add(new CollisionHandler(getLayer(Layer.ENEMY), 
						   getLayer(Layer.PLAYER)){
		public void collide(Entity body1, Entity body2){
		    //this is where you define behavior for a collision
		    //body1 is from static layer
		    //body2 is from player layer
		}
	    });

	collisionHandlers.add(new ElasticCollisionHandler(getLayer(Layer.ENEMY), 
							  getLayer(Layer.PLAYER)));

	System.out.println("col handlers size " + collisionHandlers.size());
    }

    protected void updateLayers(long deltaMs){
	//pass the task of updating individual entities to the layer
	for(EntityLayer v : allTheLayers){
	    v.update(deltaMs);
	}
    }

    protected void checkForCollisions(){
	for(CollisionHandler c : collisionHandlers){
	    c.findAndReconcileCollisions();
	}
    }

    public void update(long deltaMs){
	//call update on each layer
	updateLayers(deltaMs);
	
	//check for collisions
	checkForCollisions();
	
    }

}