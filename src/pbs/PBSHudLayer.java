/**
 * 
 */
package pbs;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;

import jig.engine.FontResource;
import jig.engine.RenderingContext;
import jig.engine.ResourceFactory;
import jig.engine.util.Vector2D;

/**
 * @author Skylar Hiebert
 *
 */
public class PBSHudLayer extends PBSQuadLayer<Entity> {
	Level level;
	private FontResource hudFont;

	public PBSHudLayer(Vector2D min, Vector2D max, Level level) {
		super(min, max);
		this.level = level;
		hudFont = ResourceFactory.getFactory().getFontResource(new Font("Sans Serif", Font.PLAIN, 24), Color.white, null);
		
	}
	
	public void render(RenderingContext rc) {

	    //alternate method, requires less math and allows using screen coords...
	    //upon writing this i realized maybe we should consider ditching the layer
	    //completely, and add this stuff to the "render" method in PBSGame
	    
	    AffineTransform aft = rc.getTransform();
	    rc.setTransform(AffineTransform.getTranslateInstance(0,0));

	    String message = "High Score: " + level.getScore();

	    hudFont.render(message, rc,
			   AffineTransform.getTranslateInstance(30, 30));


	    rc.setTransform(aft);

	    /*

	super.render(rc);
		
	String info = "High Score: " + level.getScore();
	
	hudFont.render(info, rc,
		       AffineTransform.getTranslateInstance(30,30));
	
	hudFont.render(info, rc, 
		       AffineTransform.getTranslateInstance(level.getCam().getX() - hudFont.getStringWidth(info) / 2, 10));
	info = "Score: ";
	hudFont.render(info, rc, 
		       AffineTransform.getTranslateInstance(level.getCam().getX() - hudFont.getStringWidth(info) - 75, 
							    level.getCam().getY() * 2 - hudFont.getHeight() - 10));
	info = Integer.toString(level.getScore());
	hudFont.render(info, rc, 
		       AffineTransform.getTranslateInstance(level.getCam().getX() - hudFont.getStringWidth(info) - 10, 
		       level.getCam().getY() * 2 - hudFont.getHeight() - 10));*/
	    
	}

}
