package pbs;

import jig.engine.util.Vector2D;
import pbs.Entity.*;

public class EntityFactory {
	public static String sheet_path, ship, ball, hex;
	Updater up;
	public EntityFactory(){
		sheet_path = "resources/pbs-spritesheet.png";
		ship = "#generic_ship";
		ball = "#ball";
		hex  = "#hex";
		up = new Updater();
	}
	
	public Entity get_yocil(Vector2D pos, Vector2D vel){
		Entity e = new Entity(sheet_path + ship);
		e.set_up(new Updater());
		e.setPosition(pos);
		e.setVelocity(vel);
		e.setCustomUpdate(new CustomUpdate(){
			@Override
			public void update(Entity e, long deltaMs) {
				up.y_ocilation(e, deltaMs);
			}
		});
		
		return e;
	}
	
	public Entity get_xocil(Vector2D pos, Vector2D vel){
		Entity e = new Entity(sheet_path + ship);
		e.set_up(up);
		e.setPosition(pos);
		e.setVelocity(vel);
		e.setCustomUpdate(new CustomUpdate(){
			@Override
			public void update(Entity e, long deltaMs){
				up.x_ocilation(e, deltaMs);
			}
		});
		return e;
	}
	public Entity get_bullet_arc(Vector2D pos, Vector2D vel, double arc){
		Entity e = new Entity(sheet_path + ball);
		e.set_up(up);
		e.theta = arc;
		e.setPosition(pos);
		e.setVelocity(vel);
		e.setCustomUpdate(new CustomUpdate(){
			@Override
			public void update(Entity e, long deltaMs){
				up.missile_arc(e, deltaMs);
			}
		});
		return e;
	}
	
	public Entity get_chaser(Vector2D pos, Vector2D vel, Entity target){
		Entity e = new Entity(sheet_path + hex);
		e.set_up(up);
		e.target = target;
		e.setPosition(pos);
		e.setVelocity(vel);
		e.setCustomUpdate(new CustomUpdate(){
			@Override
			public void update(Entity e, long deltaMs){
				up.chase_target(e, e.target.getCenterPosition(), deltaMs);
			}
		});
		return e;
	}
	
	public Entity target_point(Vector2D pos, Vector2D vel, final Vector2D target_pos){
		Entity e = new Entity(sheet_path + hex);
		e.set_up(up);
		e.setPosition(pos);
		e.setVelocity(vel);
		e.tar = target_pos;
		e.setCustomUpdate(new CustomUpdate(){
			@Override
			public void update(Entity e, long deltaMs){
				up.chase_target(e, e.tar, deltaMs);
			}
		});
		return e;
	}
	
	
}
