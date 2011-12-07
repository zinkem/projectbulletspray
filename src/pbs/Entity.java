package pbs;

import jig.engine.*;
import jig.engine.physics.*;
import jig.engine.util.*;

public class Entity extends Body {

    //these are interfaces used by the entity class
    public interface CustomUpdate {
	public void update(Entity e, long deltaMs);
    }

    public interface CustomRender {
	public void render(RenderingContext rc, Entity e);
    }

    public interface CustomWeapon {
	public void shoot(Level lvl, Entity type, long deltaMs);
    }

    public interface CustomAnimation {
	public void animate(Entity e, long deltaMs);
    }

    public interface CustomTrigger {
	public void fire(Level l, Vector2D v);   
    }

    //entity members
    protected long age; //age of entity
    public void setAge(long a){ age = a; } //set this negative to spawn later
    public long age() { return age; }

    protected long ttl;
    public void setTimeToLive(long t){ ttl = t; }
    public long ttl(){ return ttl; }

    protected boolean alive; //is the entity alive? (dead entities get removed)
    public boolean alive() { return this.alive; }
    public void kill(){ 
	alive = false; 
	setActivation(false);
    }
    
    protected int hp; //hit points left, hitting zero turns alive to false
    public int hp() { return hp; }
    public boolean modhp(int m) { 
	hp += m;
	if(hp <= 0) kill(); //if hp hits or falls below 0, entity dies
	return alive;
    }

    protected int score; //score value of this entity
    public int value() { return score; }
    public void setValue(int v) { score = v; }
    
    //custom methods for entity specialization
    protected CustomUpdate cu;
    public void setCustomUpdate(CustomUpdate u){ cu = u; }

    protected CustomRender cr;
    public void setCustomRender(CustomRender c) { cr = c; }
    
    protected CustomWeapon cw;
    public void setCustomWeapon(CustomWeapon w){ cw = w; }    

    protected CustomAnimation ca;
    public void setCustomAnimation(CustomAnimation a){ ca = a; }
      
    protected CustomTrigger ct;
    public void setCustomTrigger(CustomTrigger t) {ct = t; }
  
    public Entity(String imgrsc) {
	super(imgrsc);
	cu = null;
	cr = null;
	cw = null;
	ca = null;
	age = 0;
	alive = true;
	hp = 10;
	score = 100;
	ttl = -1;
    }

    public void render(RenderingContext rc) {
	if(active){
	    if (cr != null) {
		cr.render(rc, this);
	    } else {
		super.render(rc);
	    }
	}
    }
    
    public ImageResource getImage(){
	return frames.get(visibleFrame);
    }
    
    @Override
	public void update(long deltaMs) {
	if(active){
	    if(cu != null){
		cu.update(this, deltaMs);
	    }else{
		position = position.translate(velocity.scale(deltaMs/100.0));
	    }
	    
	    if(ca != null)
		ca.animate(this, deltaMs);
	}
	
	age += deltaMs;
	if( ttl > 0 && age > ttl){
	    kill();
	}
	setActivation(age > 0 && alive);
    }

    public void shoot(Level lvl, long deltaMs){
	if(active){
	    if(cw != null){
		cw.shoot(lvl, this, deltaMs);
	    }else{
		//System.out.println("Custom Weapon class not found for:"+this.toString());
	    }
	}
    }

    public void fireTrigger(Level l, long deltaMs){
	if(ct != null){
	    ct.fire(l, position);
	}
    }
}
