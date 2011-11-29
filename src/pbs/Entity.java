package pbs;

import jig.engine.*;
import jig.engine.physics.*;
import jig.engine.util.*;

public class Entity extends Body {
	
	protected Vector2D location;
	protected Updater up;
	protected long age;
	protected boolean alive;
	protected int hp = 0;
	protected int score;
	
	protected double theta;
	protected double cooldown;
	
	protected double angle, angleVelocity;
	
	protected Entity target;
	protected Vector2D tar;
	
	
	protected CustomUpdate cu;
	protected CustomRender cr;
	protected CustomWeapon cw;
	
	public Entity(String imgrsc) {
		super(imgrsc);
		age = (long)0.0;
		alive = true;
		theta = 0.005;
		angle = 0.0;
		angleVelocity = Math.PI/32.0;
		score = 100;
	}
	
	public void set_up(Updater u){
		up = u;
	}
	
	public interface CustomUpdate {
		public void update(Entity e, long deltaMs);
	}
	public interface CustomRender {
		public void render(RenderingContext rc, ImageResource ir);
	}
	public interface CustomWeapon{
		public void shoot(Level lvl, Entity type, long deltaMs);
	}
	
	public void setCustomRender(CustomRender c) {
		cr = c;
	}
	public void setCustomUpdate(CustomUpdate u){
		cu = u;
	}
	public void setCustomWeapon(CustomWeapon w){
		cw = w;
	}
	
	public void render(RenderingContext rc) {
		if (cr != null) {
			cr.render(rc, frames.get(visibleFrame));
		} else {
			super.render(rc);
		}
	}

	@Override
	public void update(long deltaMs) {
		if(cu != null){
			cu.update(this, deltaMs);
		}else{
			System.out.println("Custom update for object:"+this.toString()+" Not found");
		}
	}
	
	public void shoot(Level lvl, long deltaMs){
		if(cw != null){
			cw.shoot(lvl, this, deltaMs);
		}else{
			System.out.println("Custom Weapon class not found for:"+this.toString());
		}
		
	}
	
	public boolean alive() {
		return this.alive;
	}

	

}
