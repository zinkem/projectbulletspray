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

    //entity members
    protected long age; //age of entity
    protected long age() { return age; }

    protected boolean alive; //is the entity alive? (dead entities get removed)
    public boolean alive() { return this.alive; }
    
    protected int hp; //hit points left, hitting zero turns alive to false
    public int hp() { return hp; }
    public boolean modhp(int m) { 
	hp += m;
	alive = (hp > 0); //if hp hits or falls below 0, entity dies
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
        
    public Entity(String imgrsc) {
	super(imgrsc);
	age = 0;
	alive = true;
	hp = 1;
	score = 100;
    }

    public void render(RenderingContext rc) {
	if (cr != null) {
	    cr.render(rc, this);
	} else {
	    super.render(rc);
	}
    }
    
    public ImageResource getImage(){
		return frames.get(visibleFrame);
    }
    
    @Override
	public void update(long deltaMs) {
	if(cu != null){
	    cu.update(this, deltaMs);
	}else{
	    //System.out.println("Custom update for object:"+this.toString()+" Not found");
	}

	age += deltaMs;
    }
	
    public void shoot(Level lvl, long deltaMs){
	if(cw != null){
	    cw.shoot(lvl, this, deltaMs);
	}else{
	    System.out.println("Custom Weapon class not found for:"+this.toString());
	}
	
    }

}
