package pbs;

import jig.engine.Keyboard;
import jig.engine.util.Vector2D;
import java.awt.event.KeyEvent;
import java.math.*;
import pbs.Entity.CustomUpdate;

public class Updater {

	public static Vector2D ccw_vector(Entity e) {
		return new Vector2D(-e.getVelocity().getY(), e.getVelocity().getX());
	}

	public static Vector2D cw_vector(Entity e) {
		return new Vector2D(e.getVelocity().getY(), -e.getVelocity().getX());
	}

	public static class Target implements CustomUpdate {
		public Entity target;
		protected double angle, angleVelocity;

		public Target(Entity target) {
			this.target = target;
		}

		@Override
		public void update(Entity e, long deltaMs) {
			Vector2D velocity = e.getVelocity();

			double targetAngle = e.getPosition().angleTo(target.getPosition());
			angle = velocity.scale(-1).angleTo(new Vector2D(0, 0));
			if (angle > 0) {
				if (targetAngle >= angle - Math.PI && targetAngle <= angle) {
					e.setVelocity(velocity.rotate(-angleVelocity * deltaMs
							/ 100.0));
				} else {
					e.setVelocity(velocity.rotate(angleVelocity * deltaMs
							/ 100.0));
				}
			} else {
				if (targetAngle <= angle + Math.PI && targetAngle >= angle) {
					e.setVelocity(velocity.rotate(angleVelocity * deltaMs
							/ 100.0));
				} else {
					e.setVelocity(velocity.rotate(-angleVelocity * deltaMs
							/ 100.0));
				}
			}
			e.setPosition(e.getPosition().translate(
					e.getVelocity().scale(deltaMs / 100.0)));

		}
	}

	public static class TargetPoint implements CustomUpdate {
		Vector2D target;
		protected double angle, angleVelocity;

		public TargetPoint(Vector2D target) {
			this.target = target;
		}

		@Override
		public void update(Entity e, long deltaMs) {
			Vector2D velocity = e.getVelocity();

			double targetAngle = e.getPosition().angleTo(target);
			angle = velocity.scale(-1).angleTo(new Vector2D(0, 0));
			if (angle > 0) {
				if (targetAngle >= angle - Math.PI && targetAngle <= angle) {
					e.setVelocity(velocity.rotate(-angleVelocity * deltaMs
							/ 100.0));
				} else {
					e.setVelocity(velocity.rotate(angleVelocity * deltaMs
							/ 100.0));
				}
			} else {
				if (targetAngle <= angle + Math.PI && targetAngle >= angle) {
					e.setVelocity(velocity.rotate(angleVelocity * deltaMs
							/ 100.0));
				} else {
					e.setVelocity(velocity.rotate(-angleVelocity * deltaMs
							/ 100.0));
				}
			}
			e.setPosition(e.getPosition().translate(
					e.getVelocity().scale(deltaMs / 100.0)));

		}
	}

	public static class XOcil implements CustomUpdate {

		@Override
		public void update(Entity e, long deltaMs) {
			double dx;
			e.age += deltaMs;
			dx = -50 * Math.cos(e.age / 128.0 * Math.PI);
			e.setVelocity(new Vector2D(dx, e.getVelocity().getY()));
			e.setPosition(e.getPosition().translate(
					e.getVelocity().scale(deltaMs / 100.0)));
		}
	}

	public static class YOcil implements CustomUpdate {

		@Override
		public void update(Entity e, long deltaMs) {
			double dy;
			e.age += deltaMs;
			dy = -50 * Math.cos(e.age / 512.0 * Math.PI);
			e.setVelocity(new Vector2D(e.getVelocity().getX(), dy));
			e.setPosition(e.getPosition().translate(
					e.getVelocity().scale(deltaMs / 100.0)));

		}
	}

	public static class CCWrotate implements CustomUpdate {

		@Override
		public void update(Entity e, long deltaMs) {
			Vector2D s = e.getVelocity();
			s = s.rotate(0.05);
			e.setVelocity(s);
			// System.out.println("Vel:"+s.getX()+" "+s.getY());
			e.setPosition(e.getPosition().translate(
					e.getVelocity().scale(deltaMs / 100.0)));
		}

	}

	public static class CWrotate implements CustomUpdate {

		@Override
		public void update(Entity e, long deltaMs) {
			Vector2D s = e.getVelocity();
			s = s.rotate(-0.05);
			e.setVelocity(s);
			// System.out.println("Vel:"+s.getX()+" "+s.getY());
			e.setPosition(e.getPosition().translate(
					e.getVelocity().scale(deltaMs / 100.0)));
		}

	}

	public static class MissileArc implements CustomUpdate {
		private double arc;

		public MissileArc(double a) {
			this.arc = a;
		}

		@Override
		public void update(Entity e, long deltaMs) {
			Vector2D s = e.getVelocity();
			s = s.rotate(this.arc);
			e.setVelocity(s);
			e.setPosition(e.getPosition().translate(
					e.getVelocity().scale(deltaMs / 100.0)));
		}
	}

	public static class Strait implements CustomUpdate {
		@Override
		public void update(Entity e, long deltaMs) {
			e.setPosition(e.getPosition().translate(
					e.getVelocity().scale(deltaMs / 100.0)));
		}
	}

	public class BossCharge implements CustomUpdate {
		private Vector2D basePos;
		private double cooldown;
		private double speed;
		private double dist;
		private boolean atking;
		private double accel;
		private int direct;

		public BossCharge(Entity e) {
			basePos = e.getCenterPosition();
			direct = -1;
			atking = false;
			cooldown = 2000;
			dist = 300;
			speed = 30;
			accel = 1.0;
		}

		@Override
		public void update(Entity e, long deltaMs) {
			cooldown -= deltaMs;
			if (!atking && cooldown < 0.0) {
				//Time to return to base
				e.setVelocity(new Vector2D(35, 0));
			} else if (atking) {
				this.speed -= 0.5;
				e.setVelocity(new Vector2D(speed, 0));
			
			} else if(!atking){
				
				
			}
			e.setPosition(e.getPosition().translate(
					e.getVelocity().scale(deltaMs / 100.0)));
		}
	}

	public class BossShuffle implements CustomUpdate {

		@Override
		public void update(Entity e, long deltaMs) {

		}
	}
}
