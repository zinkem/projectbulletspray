package pbs;

import jig.engine.util.Vector2D;
import pbs.Entity.*;

public class EntityFactory {
	public static String sheet_path, type;
	Updater up;
	public EntityFactory(){
		sheet_path = "resources/pbs-spritesheet.png";
		type = "#generic_ship";
		up = new Updater();
	}
	
	public Entity get_yocil(Vector2D pos, Vector2D vel){
		Entity e = new Entity(sheet_path + type);
		e.set_up(up);
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
		Entity e = new Entity(sheet_path + type);
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
	public Entity get_missile(Vector2D pos, Vector2D vel, double arc){
		Entity e = new Entity(sheet_path + type);
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
	
	
}
