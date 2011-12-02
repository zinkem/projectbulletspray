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
    public int modScore(int m){ return score += m; }
    public int getScore(){ return score; }
    
    protected int gametime;
    public int getTime(){ return gametime/1000; }
    
    //center position of screen
    protected Vector2D camera;
    public void setCam(Vector2D cam){ camera = cam; }
    public Vector2D getCam(){ return camera; }

    //scroll vector
    protected Vector2D scrollspeed;
    public void setScrollSpeed(Vector2D ss){ scrollspeed = ss; }
    public Vector2D getScrollSpeed(){ return scrollspeed; }

    //list of layers
    private List<PBSQuadLayer<Entity>> allTheLayers;

    //list of collision handlers 
    private List<QuadLayerCollisionHandler> collisionHandlers;
    
    public Level(){
		score = 0;
		gametime = 0;
	
		camera = new Vector2D(120,240);
		scrollspeed = new Vector2D(10, 0);
		
		collisionHandlers = new ArrayList<QuadLayerCollisionHandler>();
		allTheLayers = new ArrayList<PBSQuadLayer<Entity>>();
		
		for(int i = 0; i < NUM_LAYERS; i++){
		    //here we create a new empty layer for 
		    //each entity category
		    //enumerated in Layer
		    allTheLayers.add(new PBSQuadLayer<Entity>(new Vector2D(0,0), camera.scale(2.0)));
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
    
    public PBSQuadLayer<Entity> getLayer(Layer l){
    	return allTheLayers.get(l.ordinal());
    }
    
    public void setupCollisionHandlers(){
		collisionHandlers.add(new QuadLayerCollisionHandler(getLayer(Layer.ENEMY), 
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
		    v.updateTreeBounds(stwMin, stwMax);
		    v.update(deltaMs);
		}
    }
    
    protected void checkForCollisions(){
		for(QuadLayerCollisionHandler c : collisionHandlers){
		    c.findAndReconcileCollisions();
		}
	}
	
	public void update(long deltaMs, Vector2D stwMin, Vector2D stwMax){
		
		camera = camera.translate(scrollspeed.scale(deltaMs/100.0));
	
		//call update on each layer
		updateLayers(deltaMs, stwMin, stwMax);
		
		//check for collisions
		checkForCollisions();	

   }

}