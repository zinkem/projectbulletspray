package pbs;

import jig.engine.*;
import jig.engine.physics.*;
import jig.engine.util.*;

public class Entity extends Body {

    long age;
    boolean alive;

    CustomUpdate cu;
    public void setCustomUpdate(CustomUpdate c){cu = c;}

    CustomRender cr;
    public void setCustomRender(CustomRender c){cr = c;}

    public Entity(String img){
	super(img);
	age = 0;
	alive = true;
    }

    public void render(RenderingContext rc){
	if(cr != null) {
	    cr.render(rc, frames.get(visibleFrame));
	} else {
	    super.render(rc);
	}
    }


    public void update(long deltaMs){
	if(cu != null){
	    cu.update(this, deltaMs);
	} else {
	    age += deltaMs;
	    position.translate(velocity.scale(deltaMs/100.0));
	}

    }

    public interface CustomUpdate {
	public void update(Entity e, long deltaMs);
    }

    public interface CustomRender {
	public void render(RenderingContext rc, ImageResource ir);
    }


    public static Entity getWavyMover(String img){
	
	Entity widget = new Entity(img);
	widget.setPosition(new Vector2D(200,200));
	widget.setVelocity(new Vector2D(20, 0));
	widget.setCustomUpdate(new CustomUpdate(){
		public void update(Entity e, long deltaMs){
		    //this method isnt stable, it produces drift... 

		    double dy;
		    e.age += deltaMs;
		    dy = -50* Math.cos(e.age/80.0);
		    e.setVelocity(new Vector2D(e.velocity.getX(), dy));
		    e.setPosition(e.position.translate(e.velocity.scale(deltaMs/100.0)));
		}
	    });

	return widget;

    }
}