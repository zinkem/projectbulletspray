package pbs;

import jig.engine.Keyboard;
import jig.engine.util.Vector2D;

import java.awt.event.KeyEvent;
import java.math.*;

import pbs.Entity.CustomUpdate;

public class Updater {

	public Updater(){}

	public void y_ocilation(Entity e, long deltaMs) {
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

	}
}
