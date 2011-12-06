/**
 * 
 */
package pbs;

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
    	left = layer1;
    	right = layer2;
    }
    
    private Rectangle2D getBoundingBoxForBody(Entity body){
	return new Rectangle2D.Double(body.getPosition().getX(), 
				      body.getPosition().getY(), 
				      body.getWidth()-1,body.getHeight()-1);
    }

    /* 
     * Find the smallest layer and compare the layers
     * Use the smaller layers bounding box as the min/max to
     * get from the larger layers quad tree 
     */
    public void findAndReconcileCollisions(){	
		Rectangle2D bodyBoundingBox1 = null;
		Rectangle2D bodyBoundingBox2 = null;
		PBSQuadLayer<Entity> inner, outer;
		Boolean leftRight = true;
		
		if(left.size() < right.size()) {
			outer = left; inner = right;			
		} else {
			outer = right; inner = left;
			leftRight = false;
		}
	
		/* Make the smaller layer, the outer loop */
		for( Entity body1 : outer ) {
		    Vector2D b1Pos = body1.getPosition();
		    for( Entity body2 : inner.get(b1Pos, 
						  new Vector2D(b1Pos.getX() + body1.getWidth(), 
							       b1Pos.getY() + body1.getHeight())) ){
				bodyBoundingBox1 = getBoundingBoxForBody(body1);
				bodyBoundingBox2 = getBoundingBoxForBody(body2);
				
				if(bodyBoundingBox1 != null && bodyBoundingBox2 != null){
				    if (bodyBoundingBox1.intersects(bodyBoundingBox2)) {
				    	//System.out.println("collision!");
				    	if(leftRight) 
				    		collide(body1, body2); // Body1 is layer1
				    	else
				    		collide(body2, body1); // Body2 is layer1
				    }			    
				}
		    }
		}
    }
    
    public abstract void collide(Entity body1, Entity body2);
    
}
