package pbs;

import java.awt.geom.AffineTransform;

import jig.engine.RenderingContext;
import jig.engine.util.Vector2D;
import pbs.Entity.*;
import pbs.Updater.*;
import pbs.Renders.*;


public class EntityFactory {
	public static String sheet_path, ship, ball, hex;
	private Level lvl;
	public EntityFactory(){
		sheet_path = "resources/pbs-spritesheet.png";
		ship = sheet_path+"#generic_ship";
		ball = sheet_path+"#ball";
		hex  = sheet_path+"#hex";
		//up = new Updater();
	}
	
	public void setLevel(Level l){
		this.lvl = l;
	}
	
	public Entity get_yocil(Vector2D pos, Vector2D vel){
		
		Entity e = new Entity( ship);
		
		e.setPosition(pos);
		e.setVelocity(vel);
		e.setCustomUpdate(new YOcil());
		
		return e;
	}
	
	public Entity get_xocil(Vector2D pos, Vector2D vel){
		Entity e = new Entity(ship);
		
		e.setPosition(pos);
		e.setVelocity(vel);
		e.setCustomUpdate(new XOcil());
		return e;
	}
	
	public Entity get_bullet_arc(Vector2D pos, Vector2D vel, double arc){
		Entity e = new Entity( hex);
		
		e.setPosition(pos);
		e.setVelocity(vel);
		e.setCustomUpdate(new MissileArc(0.0005));
		return e;
	}
	
	public Entity get_directed(Vector2D pos, Vector2D vel, Entity t, RenderingContext rc){
		Entity e = new Entity(hex);
		
		Vector2D target = t.getCenterPosition();
		AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
		double angle = e.getCenterPosition().angleTo(target);
		at.translate(e.getCenterPosition().getX(), e.getCenterPosition().getY());
		at.rotate(angle);
		at.translate(-e.getWidth()/2, -e.getWidth()/2);
		e.getImage().render(rc, at);
		
		e.setPosition(pos);
		e.setVelocity(vel);
		e.setCustomUpdate(new MissileArc(0.05));
		e.setCustomRender(new PointDirection(t));
		
		return e;
	}
	
	public Entity shoot_bullet(Vector2D pos, Vector2D vel, double arc){
		Entity e = new Entity(ball);
	
		e.setPosition(pos);
		e.setVelocity(vel);
		e.setCustomUpdate(new MissileArc(arc));
		return e;
	}
	
	public Entity get_chaser(Vector2D pos, Vector2D vel, Entity target){
		Entity e = new Entity(hex);
		
		e.setPosition(pos);
		e.setVelocity(vel);
		e.setCustomUpdate(new Target(target));
		return e;
	}
	
	public Entity target_point(Vector2D pos, Vector2D vel, final Vector2D target_pos){
		Entity e = new Entity( hex);
		
		e.setPosition(pos);
		e.setVelocity(vel);
		e.setCustomUpdate(new TargetPoint(target_pos));
		return e;
	}
	
	
}
