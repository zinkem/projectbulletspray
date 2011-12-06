package pbs;

import jig.engine.util.Vector2D;
import pbs.Entity.*;
//import pbs.GameRun.*;
import pbs.Level.Layer;
import pbs.Updater.*;
import pbs.Animations.*;

public class Weapons {

	public static class TriShot implements CustomWeapon {

		public Entity shoot_bullet(Vector2D pos, Vector2D vel, double arc) {
			Entity e = new Entity("resources/pbs-spritesheet.png#ball");

			e.setPosition(pos);
			e.setVelocity(vel);
			e.setCustomUpdate(new MissileArc(arc));
			return e;
		}

		@Override
		public void shoot(Level ld, Entity e, long deltaMs) {
			Entity m;
			Vector2D pos = e.getCenterPosition();

			for (int i = 0, j = -1; i < 3; i++, j++) {
				m = shoot_bullet(pos, new Vector2D(20, j), 0.0);
				ld.add(m, Layer.ENEMY);
			}
		}
	}

	// base class for all weapons
	public static abstract class AbstractWeapon implements CustomWeapon {
		Layer targetLayer;

		public AbstractWeapon(Layer l) {
			targetLayer = l;
		}
	}

	// base class for all player's weapons
	public static abstract class FriendlyWeapon extends AbstractWeapon {
		public FriendlyWeapon() {
			super(Layer.FRIENDLY);
		}
	}

    public static class FriendlySpread extends FriendlyWeapon {
	public FriendlySpread() {
	    super();
	}
	
	public void shoot(Level lvl, Entity e, long deltaMs) {
	    Entity shot;
	    
	    for (int i = 0; i < 4; i++) {
		shot = new Entity("resources/pbs-spritesheet.png#green_laser");
		shot.setPosition(e.getCenterPosition().translate(new Vector2D(0, -8 + (i * 4))));
		shot.setVelocity(new Vector2D(50, (i * 2) - 4));
		shot.setCustomUpdate(new Strait());
		lvl.add(shot, targetLayer);
	    }

	    shot = new Entity("resources/pbs-spritesheet.png#laser_trail");
	    shot.setPosition(e.getCenterPosition().translate(new Vector2D(0, -8)));
	    shot.setVelocity(new Vector2D(-Math.random()*20, -Math.random()*40+20));
	    shot.setCustomAnimation(new AnimateOnce(64));
	    lvl.add(shot, Layer.FX);
	}
    }
    
    // base class for all enemy weapons
    public static abstract class HostileWeapon extends AbstractWeapon {
	public HostileWeapon() {
	    super(Layer.HOSTILE);
	}
    }

    public static class SurroundShot extends HostileWeapon {
	int numShots;
	double increment;
	double speed;
	long repeatTimer;
	long lastShot;

	double angleoffset;

	public SurroundShot(int ns, double s, long rt){ 
	    super();
	    numShots = ns;
	    increment = numShots/(2*Math.PI);
	    speed = s;
	    repeatTimer = rt;
	    lastShot = rt;
	    
	    angleoffset = 0.0;
	}

	public void shoot(Level lvl, Entity e, long deltaMs){
	    Entity shot;
	    double xv;
	    double yv;
	    double ang = angleoffset; 
	    angleoffset += Math.PI/60.0;

	    lastShot += deltaMs;
	    if(lastShot >= repeatTimer){
		lastShot -= repeatTimer;
		for(int i = 0; i < numShots; i++){
	    
		    xv = Math.cos(ang);
		    yv = Math.sin(ang);
		    ang += increment;
		    
		    shot = new Entity("resources/pbs-spritesheet.png#red_bullet");
		    shot.setPosition(e.getCenterPosition().translate(new Vector2D(xv, yv)));
		    shot.setVelocity(new Vector2D(xv*speed, yv*speed));
		    lvl.add(shot, targetLayer);
		    
		}
	    }

	}
    }

    
}
