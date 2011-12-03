package pbs;

import jig.engine.Keyboard;
import jig.engine.util.Vector2D;
import java.awt.event.KeyEvent;
import java.math.*;
import pbs.Entity.CustomUpdate;

public abstract class Updater implements CustomUpdate{

	public Updater(){}

/*	public void y_ocilation(Entity e, long deltaMs) {
		double dy;
		e.age += deltaMs;
		dy = -50 * Math.cos(e.age / 128.0 * Math.PI);
		e.setVelocity(new Vector2D(e.getVelocity().getX(), dy));
		e.setPosition(e.getPosition().translate(
				e.getVelocity().scale(deltaMs / 100.0)));
	}
	
	public void x_ocilation(Entity e, long deltaMs){
		double dx;
		e.age += deltaMs;
		dx = -50 * Math.cos(e.age/128.0 *Math.PI);
		e.setVelocity(new Vector2D(dx, e.getVelocity().getY()));
		e.setPosition(e.getPosition().translate(e.getVelocity().scale(deltaMs/ 100.0)));
	}

	public Vector2D ccw_vector(Entity e) {
		return new Vector2D(-e.getVelocity().getY(), e.getVelocity().getX());
	}

	public Vector2D cw_vector(Entity e) {
		return new Vector2D(e.getVelocity().getY(), -e.getVelocity().getX());
	}

	public void ccwrotate(Entity e, long deltaMs){
		Vector2D s = e.getVelocity();
		s = s.rotate(0.05);
		e.setVelocity(s);
		//System.out.println("Vel:"+s.getX()+" "+s.getY());
		e.setPosition(e.getPosition().translate(e.getVelocity().scale(deltaMs/100.0)));
	}
	
	public void cw_rotate(Entity e, long deltaMs){
		Vector2D s = e.getVelocity();
		s = s.rotate(-0.05);
		e.setVelocity(s);
		//System.out.println("Vel:"+s.getX()+" "+s.getY());
		e.setPosition(e.getPosition().translate(e.getVelocity().scale(deltaMs/100.0)));
	}
	
	public void missile_arc(Entity e, long deltaMs){
		Vector2D s = e.getVelocity();
		s = s.rotate(e.theta);
		e.setVelocity(s);
		e.setPosition(e.getPosition().translate(e.getVelocity().scale(deltaMs/100.0)));
		
	}
	
	public void chase_target(Entity e, Vector2D target, long deltaMs){
		Vector2D velocity = e.getVelocity();

		double targetAngle = e.getPosition().angleTo(target);
		e.angle = velocity.scale(-1).angleTo(new Vector2D(0, 0));
		if (e.angle > 0) {
			if (targetAngle >= e.angle - Math.PI && targetAngle <= e.angle) {
				e.setVelocity(velocity.rotate(-e.angleVelocity * deltaMs
						/ 100.0));
			} else {
				e.setVelocity(velocity.rotate(e.angleVelocity * deltaMs
						/ 100.0));
			}
		} else {
			if (targetAngle <= e.angle + Math.PI && targetAngle >= e.angle) {
				e.setVelocity(velocity.rotate(e.angleVelocity * deltaMs
						/ 100.0));
			} else {
				e.setVelocity(velocity.rotate(-e.angleVelocity * deltaMs
						/ 100.0));
			}
		}
		e.setPosition(e.getPosition().translate(e.getVelocity().scale(deltaMs/100.0)));
	}

	public void boss_charge(Entity e, long deltaMs, double cooldown, double distance) {
		
		
	}

	public void boss_shuffle(Entity e, long deltaMs, int cooldown) {

	}*/
	
	public static Vector2D ccw_vector(Entity e) {
		return new Vector2D(-e.getVelocity().getY(), e.getVelocity().getX());
	}

	public static Vector2D cw_vector(Entity e) {
		return new Vector2D(e.getVelocity().getY(), -e.getVelocity().getX());
	}
	
	/*public static class KeyboardControls implements CustomUpdate{
		
		Keyboard key;

		public KeyboardControls(Keyboard k) {
			key = k;
		}

		public void update(Entity e, long deltaMs) {
			System.out.println("X:"+e.getPosition().getX()+" Y:"+e.getPosition().getY());
			boolean left = key.isPressed(KeyEvent.VK_LEFT);
			boolean right = key.isPressed(KeyEvent.VK_RIGHT);
			boolean up = key.isPressed(KeyEvent.VK_UP);
			boolean down = key.isPressed(KeyEvent.VK_DOWN);
			
			boolean reset = left && right;

			boolean fire = key.isPressed(KeyEvent.VK_SPACE);

			Vector2D pos = e.getPosition();
			e.setVelocity(new Vector2D(0, 0));

			if (left && !right) {
				e.setVelocity(e.getVelocity().translate(new Vector2D(-30, 0)));
			}

			if (right && !left) {
				e.setVelocity(e.getVelocity().translate(new Vector2D(30, 0)));
			}

			if (up && !down) {
				e.setVelocity(e.getVelocity().translate(new Vector2D(0, -30)));
			}

			if (down && !up) {
				e.setVelocity(e.getVelocity().translate(new Vector2D(0, 30)));
			}

			e.setPosition(pos.translate(e.getVelocity().scale(deltaMs / 100.0)));
			e.age += deltaMs;

		}
	}*/
	
	public static class Target implements CustomUpdate{
		public Entity target;
		public Target(Entity target){
			this.target = target;
		}

		@Override
		public void update(Entity e, long deltaMs) {
			Vector2D velocity = e.getVelocity();

			double targetAngle = e.getPosition().angleTo(target.getPosition());
			e.angle = velocity.scale(-1).angleTo(new Vector2D(0, 0));
			if (e.angle > 0) {
				if (targetAngle >= e.angle - Math.PI && targetAngle <= e.angle) {
					e.setVelocity(velocity.rotate(-e.angleVelocity * deltaMs
							/ 100.0));
				} else {
					e.setVelocity(velocity.rotate(e.angleVelocity * deltaMs
							/ 100.0));
				}
			} else {
				if (targetAngle <= e.angle + Math.PI && targetAngle >= e.angle) {
					e.setVelocity(velocity.rotate(e.angleVelocity * deltaMs
							/ 100.0));
				} else {
					e.setVelocity(velocity.rotate(-e.angleVelocity * deltaMs
							/ 100.0));
				}
			}
			e.setPosition(e.getPosition().translate(e.getVelocity().scale(deltaMs/100.0)));
			
		}
	}
	
	public static class TargetPoint implements CustomUpdate{
		Vector2D target;
		public TargetPoint(Vector2D target){
			this.target = target;
		}

		@Override
		public void update(Entity e, long deltaMs) {
			Vector2D velocity = e.getVelocity();

			double targetAngle = e.getPosition().angleTo(target);
			e.angle = velocity.scale(-1).angleTo(new Vector2D(0, 0));
			if (e.angle > 0) {
				if (targetAngle >= e.angle - Math.PI && targetAngle <= e.angle) {
					e.setVelocity(velocity.rotate(-e.angleVelocity * deltaMs
							/ 100.0));
				} else {
					e.setVelocity(velocity.rotate(e.angleVelocity * deltaMs
							/ 100.0));
				}
			} else {
				if (targetAngle <= e.angle + Math.PI && targetAngle >= e.angle) {
					e.setVelocity(velocity.rotate(e.angleVelocity * deltaMs
							/ 100.0));
				} else {
					e.setVelocity(velocity.rotate(-e.angleVelocity * deltaMs
							/ 100.0));
				}
			}
			e.setPosition(e.getPosition().translate(e.getVelocity().scale(deltaMs/100.0)));
			
		}
	}
	
	public static class XOcil implements CustomUpdate{

		@Override
		public void update(Entity e, long deltaMs) {
			double dx;
			e.age += deltaMs;
			dx = -50 * Math.cos(e.age/128.0 *Math.PI);
			e.setVelocity(new Vector2D(dx, e.getVelocity().getY()));
			e.setPosition(e.getPosition().translate(e.getVelocity().scale(deltaMs/ 100.0)));
		}
	}
	
	public static class YOcil implements CustomUpdate{

		@Override
		public void update(Entity e, long deltaMs) {
			double dy;
			e.age += deltaMs;
			dy = -50 * Math.cos(e.age / 128.0 * Math.PI);
			e.setVelocity(new Vector2D(e.getVelocity().getX(), dy));
			e.setPosition(e.getPosition().translate(
					e.getVelocity().scale(deltaMs / 100.0)));
			
		}
	}
	
	public static class CCWrotate implements CustomUpdate{

		@Override
		public void update(Entity e, long deltaMs) {
			Vector2D s = e.getVelocity();
			s = s.rotate(0.05);
			e.setVelocity(s);
			//System.out.println("Vel:"+s.getX()+" "+s.getY());
			e.setPosition(e.getPosition().translate(e.getVelocity().scale(deltaMs/100.0)));
		}
		
	}
	public static class CWrotate implements CustomUpdate{

		@Override
		public void update(Entity e, long deltaMs) {
			Vector2D s = e.getVelocity();
			s = s.rotate(-0.05);
			e.setVelocity(s);
			//System.out.println("Vel:"+s.getX()+" "+s.getY());
			e.setPosition(e.getPosition().translate(e.getVelocity().scale(deltaMs/100.0)));
		}
		
	}	
	
	public static class MissileArc implements CustomUpdate{
		private double arc;
		public MissileArc(double a){
			this.arc = a;
		}
		@Override
		public void update(Entity e, long deltaMs) {
			Vector2D s = e.getVelocity();
			s = s.rotate(this.arc);
			e.setVelocity(s);
			e.setPosition(e.getPosition().translate(e.getVelocity().scale(deltaMs/100.0)));
		}
	}
	
	public static class Strait implements CustomUpdate{
		@Override
		public void update(Entity e, long deltaMs){
			e.setPosition(e.getPosition().translate(e.getVelocity().scale(deltaMs/100.0)));
		}
	}
	
	
	public class BossCharge implements CustomUpdate{
		private double cooldown;
		private double speed;
		
		public BossCharge(){
			
		}
		
		@Override
		public void update(Entity e, long deltaMs){
			
		}
	}

	public class BossShuffle implements CustomUpdate{
		
		@Override
		public void update(Entity e, long deltaMs){
			
		}
	}
}
