package pbs;

import jig.engine.util.Vector2D;
import pbs.Entity.*;
//import pbs.GameRun.*;
import pbs.Level.Layer;
import pbs.Updater.*;

public class Weapons {

    public static class TriShot implements CustomWeapon{

	public Entity shoot_bullet(Vector2D pos, Vector2D vel, double arc){
	    Entity e = new Entity("resources/pbs-spritesheet.png#ball");
			
	    e.setPosition(pos);
	    e.setVelocity(vel);
	    e.setCustomUpdate(new MissileArc(arc));
	    return e;
	}
		
	@Override
	    public void shoot(Level ld, Entity e, long deltaMs) {
	    Entity m0, m1, m2;
	    Vector2D pos = e.getCenterPosition();
			
	    m0 = shoot_bullet(pos, new Vector2D(20, -1), 0.0);
	    m1 = shoot_bullet(pos, new Vector2D(20, 0), 0.0);
	    m2 = shoot_bullet(pos, new Vector2D(20, 1), 0.0);
			
	    ld.add(m0, Layer.ENEMY);
	    ld.add(m1, Layer.ENEMY);
	    ld.add(m2, Layer.ENEMY);
	}
    }

    //base class for all weapons
    public static abstract class AbstractWeapon implements CustomWeapon {
	Layer targetLayer;
	public AbstractWeapon(Layer l){
	    targetLayer = l;
	}
    }

    //base class for all player's weapons
    public static abstract class FriendlyWeapon extends AbstractWeapon {
	public FriendlyWeapon(){
	    super(Layer.FRIENDLY);
	}
    }

    public static class FriendlySpread extends FriendlyWeapon {
	public FriendlySpread() { super(); } 
	
	public void shoot(Level lvl, Entity e, long deltaMs){
	    Entity shot;

	    for(int i = 0; i < 4; i++){
		shot = new Entity("resources/pbs-spritesheet.png#green_laser");
		shot.setPosition(e.getCenterPosition().
				 translate(new Vector2D(0, -16 + (i*8))));
		shot.setVelocity(new Vector2D(50, (i*2)-4));
		shot.setCustomUpdate(new Strait());
		lvl.add(shot, targetLayer);
	    }
	}
    }
    

    //base class for all enemy weapons
    public static abstract class HostileWeapon extends AbstractWeapon {
	public HostileWeapon(){
	    super(Layer.HOSTILE);
	}
    }
    
	
}
