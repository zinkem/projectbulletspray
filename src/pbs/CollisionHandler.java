package pbs;

import java.awt.geom.Rectangle2D;

import jig.engine.*;

import quadtree.*;

public abstract class CollisionHandler {

    protected QuadTree<Entity> left;
    protected QuadTree<Entity> right;
    
    public CollisionHandler(QuadTree<Entity> layer1,
			    QuadTree<Entity> layer2){
	left = layer1;
	right = layer2;
    }
    
    private Rectangle2D getBoundingBoxForBody(Entity body){
	return new Rectangle2D.Double(body.getPosition().getX(), 
				      body.getPosition().getY(), 
				      body.getWidth()-1,body.getHeight()-1);
    }

    //treats all VanillaSpheres and VanillaAARectangles like rectangles
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
