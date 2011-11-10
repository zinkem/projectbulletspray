package pbs;

import java.awt.geom.Rectangle2D;

import jig.engine.*;

import quadtree.*;

public abstract class CollisionHandler {

    protected EntityLayer<Entity> left;
    protected EntityLayer<Entity> right;
    
    public CollisionHandler(EntityLayer<Entity> layer1,
			    EntityLayer<Entity> layer2){
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
		
		if(bodyBoundingBox1 != null && bodyBoundingBox2 != null)
		    if (bodyBoundingBox1.intersects(bodyBoundingBox2)) {
			collide(body1, body2);
		    }
		
		
	    }
    }
    
    public abstract void collide(Entity body1, Entity body2);
    
}
