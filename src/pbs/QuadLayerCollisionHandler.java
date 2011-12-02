/**
 * 
 */
package pbs;

import jig.engine.physics.BodyLayer;
import jig.engine.util.Vector2D;

import java.awt.geom.Rectangle2D;

/**
 * @author Skylar Hiebert
 *
 */

public abstract class QuadLayerCollisionHandler {

    protected PBSQuadLayer<Entity> left;
    protected PBSQuadLayer<Entity> right;
    
    public QuadLayerCollisionHandler(PBSQuadLayer<Entity> layer1, 
    		PBSQuadLayer<Entity> layer2){
    	/* Make the smaller layer, layer1 */
    	if(layer1.size() < layer2.size()) {
    		left = layer1;
    		right = layer2;
    	} else { 
    		left = layer2;
    		right = layer1;
    	}
    }
    
    private Rectangle2D getBoundingBoxForBody(Entity body){
	return new Rectangle2D.Double(body.getPosition().getX(), 
				      body.getPosition().getY(), 
				      body.getWidth()-1,body.getHeight()-1);
    }

    //treat everything like a rectangle, n^2 algorithm
    public void findAndReconcileCollisions(){
		Rectangle2D bodyBoundingBox1 = null;
		Rectangle2D bodyBoundingBox2 = null;
		
		for( Entity body1 : left ) {
			Vector2D b1Pos = body1.getPosition();
		    for( Entity body2 : right.get(b1Pos, 
		    		new Vector2D(b1Pos.getX() + body1.getWidth(), 
		    				b1Pos.getY() + body1.getHeight())) ){
				bodyBoundingBox1 = getBoundingBoxForBody(body1);
				bodyBoundingBox2 = getBoundingBoxForBody(body2);
		
				if(bodyBoundingBox1 != null && bodyBoundingBox2 != null){
					if (bodyBoundingBox1.intersects(bodyBoundingBox2)) {
						System.out.println("collision!");
						collide(body1, body2);
				    }			    
				}
		    }
		}
    }
    
    public abstract void collide(Entity body1, Entity body2);
    
}
