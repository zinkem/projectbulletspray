package pbs;

import java.util.*;

import jig.engine.*;
import jig.engine.physics.BodyLayer;
import jig.engine.util.Vector2D;

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

    //important level info
    protected int score;
    public int getScore(){ return score; }

    protected int gametime;
    public int getTime(){ return gametime/1000; }

    //list of layers
    private List<PBSQuadLayer<Entity>> allTheLayers;

    //list of collision handlers 
    private List<CollisionHandler> collisionHandlers;
    
    public Level(){
		score = 0;
		gametime = 0;
	
	
		collisionHandlers = new ArrayList<CollisionHandler>();
		allTheLayers = new ArrayList<PBSQuadLayer<Entity>>();
	
		for(int i = 0; i < NUM_LAYERS; i++){
		    //here we create a new empty layer for 
		    //each entity category
		    //enumerated in Layer
		    allTheLayers.add(new PBSQuadLayer<Entity>(new Vector2D(0,0), new Vector2D(1000,1000)));
		}
	
		//after all layers are added, we can add collision handlers
		setupCollisionHandlers();
    }

    public Level(Vector2D min, Vector2D max){
		score = 0;
		gametime = 0;
	
	
		collisionHandlers = new ArrayList<CollisionHandler>();
		allTheLayers = new ArrayList<PBSQuadLayer<Entity>>();
	
		for(int i = 0; i < NUM_LAYERS; i++){
		    //here we create a new empty layer for 
		    //each entity category
		    //enumerated in Layer
		    allTheLayers.add(new PBSQuadLayer<Entity>(min, max));
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
    public List<ViewableLayer> getLayers(){
    	List<ViewableLayer> layerList = new ArrayList<ViewableLayer>();
    	layerList.addAll(allTheLayers);
    	return layerList;
    }

    public BodyLayer<Entity> getLayer(Layer l){
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

    /* stwMin, stwMax are ScreenToWorld coordinates for QuadTree */
    protected void updateLayers(long deltaMs, Vector2D stwMin, Vector2D stwMax) {
		//pass the task of updating individual entities to the layer
		for(PBSQuadLayer<Entity> v : allTheLayers){
		    gametime += deltaMs;
		    v.update(deltaMs);
		}
    }

    protected void checkForCollisions(){
		for(CollisionHandler c : collisionHandlers){
		    c.findAndReconcileCollisions();
		}
    }

    public void update(long deltaMs, Vector2D stwMin, Vector2D stwMax){
		//call update on each layer
		updateLayers(deltaMs, stwMin, stwMax);
		
		//check for collisions
		checkForCollisions();
    }

}