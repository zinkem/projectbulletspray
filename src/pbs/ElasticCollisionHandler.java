package pbs;

import jig.engine.util.Vector2D;
import jig.engine.physics.*;

public class ElasticCollisionHandler extends CollisionHandler {

    public ElasticCollisionHandler(EntityLayer<Entity> layer1, 
				   EntityLayer<Entity> layer2){
	super(layer1, layer2);
    }

    //transoform original vector to new coordinate system specified by x and y axis
    Vector2D transform(Vector2D x_axis, Vector2D y_axis, Vector2D original){
	return new Vector2D(x_axis.getX()*original.getX() + x_axis.getY()*original.getY(),
			    y_axis.getX()*original.getX() + y_axis.getY()*original.getY());
    }
    
    
    //clculates a bounce collision assuming both bodies have the same mass
    public void collide(Entity body1, Entity body2){
	
	if(body1.isActive() == false ||
	   body2.isActive() == false){
	    return;
	} //exit function if one of the bodies is inactive
	
	//calulate difference
	Vector2D v1 = body1.getCenterPosition();
	Vector2D v2 = body2.getCenterPosition();
	Vector2D diff = v1.difference(v2);
	
	//calculate vectors normal and tangent to the collision
	Vector2D unitNormal = diff.unitVector();
	Vector2D unitTan = new Vector2D(-diff.getY(), diff.getX());
	
	Vector2D bodyXform1 = transform(unitNormal, unitTan, body1.getVelocity());
	Vector2D bodyXform2 = transform(unitNormal, unitTan, body2.getVelocity());
	
	Vector2D newVel1 = new Vector2D(bodyXform2.getX() * unitNormal.getX(),
					//+ bodyXform1.getY() * unitTan.getX(),
					bodyXform2.getX() * unitNormal.getY());
	                                //+ bodyXform1.getY() * unitTan.getY());
	
	Vector2D newVel2 = new Vector2D(bodyXform1.getX() * unitNormal.getX(),
					//+ bodyXform2.getY() * unitTan.getX(),
					bodyXform1.getX() * unitNormal.getY());
	                                //+ bodyXform2.getY() * unitTan.getY());
	
	body1.setPosition(body1.getPosition().translate(body1.getVelocity().scale(-1)));
	//body2.revertPosition();
	
	body1.setVelocity(newVel1);
	body2.setVelocity(newVel2);
    }

}