package pbs;

import java.awt.geom.Rectangle2D;

import jig.engine.*;
import jig.engine.physics.BodyLayer;

import quadtree.*;

/* naive collision handler, performs n^2 algorithm for detection
 *
 */ 

public abstract class CollisionHandler {

    protected BodyLayer<Entity> left;
    protected BodyLayer<Entity> right;
    
    public CollisionHandler(BodyLayer<Entity> layer1,
			    BodyLayer<Entity> layer2){
	left = layer1;
	right = layer2;
    }
    
    private Rectangle2D getBoundingBoxForBody(Entity body){
	return new Rectangle2D.Double(body.getPosition().getX(), 
				      body.getPosition().getY(), 
				      body.getWidth()-1,body.getHeight()-1);
    }

    //treat everything like a rectangle, n^2 algorithm
    public void findAndReconcileCollisions(){

	double r = 0;
	Rectangle2D bodyBoundingBox1 = null;
	Rectangle2D bodyBoundingBox2 = null;
	
	for( Entity body1 : left )
	    for( Entity body2 : right ){
		
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
    
    public abstract void collide(Entity body1, Entity body2);
    
}
