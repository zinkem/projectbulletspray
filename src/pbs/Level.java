package pbs;

import java.util.*;

import jig.engine.*;
import jig.engine.util.*;
import jig.engine.audio.jsound.*;

import pbs.parser.Statements.*;
import pbs.parser.Elements.*;
import pbs.Animations.*;

public class Level {


    public static final String SPRITE_SHEET = "resources/pbs-spritesheet.png";

    public static enum Layer { 
	BACKGROUND, 
	    TRIGGERS,
	    STATIC, 
	    FX,
	    HOSTILE, 
	    ENEMY, 
	    FRIENDLY, 
	    PLAYER, 
	    FOREGROUND,
	    HUD }
    
    public static int NUM_LAYERS = Layer.values().length;

    //audio stuffs
    AudioClip enemydeath;
    AudioClip playerdeath;
    AudioClip laserfire;
    AudioClip laserhit;

    protected long lastaudio;
    protected long audiopause;

    
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

    //indicate if level has been completed
    protected boolean levelComplete;
    public boolean levelComplete(){ return levelComplete; }
    public void setLevelComplete(boolean b){ 
	message = "Congratulations, Level Complete!";
	levelComplete = b; }
    
    //next level stuffs
    protected String nextLevel; //filename of next level
    public String getNextLevel(){ return nextLevel; };
    public void setNextLevel(String n){ nextLevel = n; }

    //message to user
    protected String message;
    public String getMessage(){ return message; }
    public void setMessage(String s){ message = s; }

    //list of layers
    private List<PBSQuadLayer<Entity>> allTheLayers;

    //list of collision handlers 
    private List<QuadLayerCollisionHandler> collisionHandlers;

    //statement list
    private Stack<Statement> events;
    public void addStatement(Statement s){ events.push(s); }
    public void execute(){
	Statement s;
	while(!events.empty()){
	    s = events.pop();
	    s.execute(this);
	}
    }

    Entity player;
    
    //symbol table with templated entities/triggers
    HashMap<String, ObjectDescription> templates;
    public ObjectDescription getTemplate(String s){
	ObjectDescription od = templates.get(s);
	if(od == null)
	    System.out.println("No object description named " + s);
	return od;
    }
    public void addTemplate(String s, ObjectDescription od){
	templates.put(s, od);
    }


    public Level(){

	ResourceFactory rf = ResourceFactory.getFactory();
	
	enemydeath = rf.getAudioClip("resources/explosion3.wav");
	playerdeath = rf.getAudioClip("resources/explosion2.wav");
	laserfire = rf.getAudioClip("resources/lasershot1.wav");
	laserhit = rf.getAudioClip("resources/laserhit1.wav");

	audiopause = 16;
	lastaudio = 16;

	score = 0;
	gametime = 0;
	message = "";
	
	camera = new Vector2D(320,240);
	scrollspeed = new Vector2D(1, 0);
	
	events = new Stack<Statement>();
	templates = new HashMap<String, ObjectDescription>();
	
	collisionHandlers = new ArrayList<QuadLayerCollisionHandler>();
	allTheLayers = new ArrayList<PBSQuadLayer<Entity>>();
	
	ImageResource bgImage = ResourceFactory.getFactory().getFrames("resources/terrain.png#bedrock").get(0);
	allTheLayers.add(new ScrollingBackgroundLayer(bgImage, 
			1000, 800, new Vector2D(20, 0)));
	for(int i = 1; i < NUM_LAYERS; i++){
	    //here we create a new empty layer for 
	    //each entity category
	    //enumerated in Layer
	    allTheLayers.add(new PBSQuadLayer<Entity>(new Vector2D(0,0), camera.scale(2.0)));
	}
	
	//after all layers are added, we can add collision handlers
	setupCollisionHandlers();


    }


    // setters
    public void add(Entity e, Layer l) {

	if(l == Layer.PLAYER)
	    player = e;
	
	getLayer(l).add(e);
    }

    // getters
    public List<ViewableLayer> getLayers() {
	List<ViewableLayer> layerList = new ArrayList<ViewableLayer>();
	layerList.addAll(allTheLayers);
	return layerList;
    }

    public PBSQuadLayer<Entity> getLayer(Layer l) {
	return allTheLayers.get(l.ordinal());
    }

    public void setupCollisionHandlers() {
	collisionHandlers.add(new QuadLayerCollisionHandler(getLayer(Layer.FRIENDLY), getLayer(Layer.ENEMY)) {
		public void collide(Entity body1, Entity body2) {
		    // this is where you define behavior for a collision
		    // body1 is from friendly bullet layer
		    // body2 is from enemy layer

		    if (body1.isActive() && body2.isActive()) {

			double rx = 4.0 * Math.random() - 2;
			double ry = 4.0 * Math.random() - 2;

			Entity e = new Entity(SPRITE_SHEET + "#small_burst");
			e.setPosition(body1.getCenterPosition().translate(
									  new Vector2D(rx, ry)));
			e.setCustomAnimation(new AnimateOnce(75));
			add(e, Layer.FX);
			body1.kill();
			body2.modhp(-1);

			if (body2.alive() == false) {
			    if(lastaudio >= audiopause){
				enemydeath.play();
				lastaudio = 0;
			    }


			    score += body2.value();

			    for (int i = 0; i < 5; i++) {
				rx = 100.0 * Math.random() - 50;
				ry = 100.0 * Math.random() - 50;
				e = new Entity(SPRITE_SHEET + "#large_burst");
				e.setPosition(body1.getCenterPosition().translate(
										  new Vector2D(rx, ry)));
				e.setCustomAnimation(new AnimateOnce(75));
				e.setAge((long) (-rx));
				add(e, Layer.FX);
			    }
			}
			
			if(lastaudio >= audiopause){
			    laserhit.play();
			    lastaudio = 0;
			}
		    }
		}
	    });

	collisionHandlers.add(new QuadLayerCollisionHandler(getLayer(Layer.PLAYER), getLayer(Layer.STATIC)){

		@Override
		    public void collide(Entity body1, Entity body2) {
		    if(body1.isActive() && body2.isActive()){
			double rx = 4.0*Math.random() -2.0;
			double ry = 4.0*Math.random() -2.0;
				
			Entity e = new Entity(SPRITE_SHEET + "#large_burst");
			e.setPosition(body1.getCenterPosition().translate(new Vector2D(rx, ry)));
			e.setCustomAnimation(new AnimateOnce(75));
			add(e, Layer.FX);
				
			//Kill the player, do any sort of updates to the game necessary
			body1.kill();

			if(lastaudio >= audiopause){
			    playerdeath.play();
			    lastaudio = 0;
			}

		    }
		}
	    });
	
	collisionHandlers.add(new QuadLayerCollisionHandler(getLayer(Layer.PLAYER), getLayer(Layer.ENEMY)){
		
		@Override
		    public void collide(Entity body1, Entity body2) {
		    if(body1.isActive() && body2.isActive()){
			double rx = 8.0*Math.random() -4.0;
			double ry = 8.0*Math.random() -4.0;
				
			Entity e = new Entity(SPRITE_SHEET + "#large_burst");
			e.setPosition(body1.getCenterPosition().translate(new Vector2D(rx, ry)));
			e.setCustomAnimation(new AnimateOnce(75));
			add(e, Layer.FX);
				
			e = new Entity(SPRITE_SHEET + "#small_burst");
			e.setPosition(body2.getCenterPosition().translate(new Vector2D(ry, rx)));
			e.setCustomAnimation(new AnimateOnce(75));
			add(e, Layer.FX);
			
			body2.kill();
			body1.modhp(-1);

			if(body1.alive()){
			    if(lastaudio >= audiopause){
				laserhit.play();
				lastaudio = 0;
			    }
			} else{
			    if(lastaudio >= audiopause){
				playerdeath.play();
				lastaudio = 0;
			    }
			}
		    }
		}
	    });
	
	collisionHandlers.add(new QuadLayerCollisionHandler(getLayer(Layer.PLAYER), getLayer(Layer.HOSTILE)){
		
		@Override
		    public void collide(Entity body1, Entity body2) {
		    if(body1.isActive() && body2.isActive()){
			double rx = 8.0*Math.random() -4.0;
			double ry = 8.0*Math.random() -4.0;
				
			Entity e = new Entity(SPRITE_SHEET + "#small_burst");
			e.setPosition(body2.getCenterPosition().translate(new Vector2D(ry, rx)));
			e.setCustomAnimation(new AnimateOnce(75));
			add(e, Layer.FX);
			
			body2.kill();
			body1.modhp(-1);
			if(body1.alive()){
			    if(lastaudio >= audiopause){
				laserhit.play();
				lastaudio = 0;
			    }
			} else {
			    if(lastaudio >= audiopause){
				playerdeath.play();
				lastaudio = 0;
			    }
			}
		    }
		}
	    });


	// System.out.println("col handlers size " + collisionHandlers.size());
    }

    /* stwMin, stwMax are ScreenToWorld coordinates for QuadTree */
    protected void updateLayers(long deltaMs, Vector2D stwMin, Vector2D stwMax) {
	// pass the task of updating individual entities to the layer
	for (PBSQuadLayer<Entity> v : allTheLayers) {
	    gametime += deltaMs;
	    v.updateTreeBounds(stwMin, stwMax);
	    v.update(deltaMs);
	}
    }

    protected void checkForCollisions() {
	for (QuadLayerCollisionHandler c : collisionHandlers) {
	    c.findAndReconcileCollisions();
	}
    }

    public void update(long deltaMs, Vector2D stwMin, Vector2D stwMax) {

	camera = camera.translate(scrollspeed.scale(deltaMs / 100.0));
	lastaudio += deltaMs;
	
	// execute all statements on queue if the player is alivee
	if(player != null)
	    if(player.alive())
		execute();
	    
	// call update on each layer
	updateLayers(deltaMs, stwMin, stwMax);
	    
	//behavior custom to each layer goes here
	    
	//check to see which triggers need to be fired...
	Iterator<Entity> elist = getLayer(Layer.TRIGGERS).iterator();
	if(elist != null)
	    while(elist.hasNext()){
		Entity t = elist.next();
		t.fireTrigger(this, deltaMs);
	    }
	
	//call "shoot" on all enemy entities in case they have weapons to fire
	elist = getLayer(Layer.ENEMY).iterator();
	if(elist != null)
	    while(elist.hasNext()){
		Entity e = elist.next();
		e.shoot(this, deltaMs);
	    }

	//check for collisions
	checkForCollisions();	
	
    }

}