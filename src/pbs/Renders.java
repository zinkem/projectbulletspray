package pbs;
import java.awt.geom.AffineTransform;

import jig.engine.ImageResource;
import jig.engine.RenderingContext;
import jig.engine.util.*;
import pbs.Entity.*;

public class Renders {
	
	public static class FollowVelocity implements CustomRender{
		private Vector2D origin;
		public FollowVelocity(){ this.origin = new Vector2D(0,0); }
		
		@Override
		public void render(RenderingContext rc, Entity e) {
			Vector2D vel = e.getVelocity();
			
			
			AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
			double angle = this.origin.angleTo(vel);
			at.translate(e.getCenterPosition().getX(), e.getCenterPosition().getY());
			at.rotate(angle);
			at.translate(-e.getWidth()/2, -e.getHeight()/2);
			e.getImage().render(rc, at);
		}
	}
	
	public static class PointDirection implements CustomRender{
		private Entity tar;
		public PointDirection(Entity t){ this.tar = t; }
	
		@Override
		public void render(RenderingContext rc, Entity e) {
			Vector2D target = tar.getCenterPosition();
			
			AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
			double angle = e.getCenterPosition().angleTo(target);
			at.translate(e.getCenterPosition().getX(), e.getCenterPosition().getY());
			at.rotate(angle);
			at.translate(-e.getWidth()/2, -e.getWidth()/2);
			e.getImage().render(rc, at);
		}	
	}
	
	public static class Spin implements CustomRender{
		private double angle, inc;
		public Spin(double a, double inc){
			this.angle = a;
			this.inc = inc;
		}
		
		@Override
		public void render(RenderingContext rc, Entity e) {
			angle += inc;
			AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
			at.translate(e.getCenterPosition().getX(), e.getCenterPosition().getY());
			at.rotate(angle);
			at.translate(-e.getWidth()/2, -e.getHeight()/2);
			e.getImage().render(rc, at);
		}
	}
	
	public static class SetStatic implements CustomRender{
		private double angle;
		public SetStatic(double a){
			this.angle = a;
		}

		@Override
		public void render(RenderingContext rc, Entity e) {
				AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
				at.translate(e.getCenterPosition().getX(), e.getCenterPosition().getY());
				at.rotate(angle);
				at.translate(-e.getWidth()/2, -e.getHeight()/2);
				e.getImage().render(rc, at);
				set = true;

		}
		
	}

}
