/**
 * 
 */
package quadtree;

import java.util.ArrayList;
import java.util.List;

import jig.engine.physics.Body;
import jig.engine.util.Vector2D;

/**
 * @author Skylar Hiebert
 *
 */
public class QuadNode<T extends Body> {
	protected static final int MAX_ENTITIES = 1;
	private QuadNode<T> parent, nwNode, neNode, swNode, seNode;
	private Vector2D min, max;
	private List<T> entities;

	/**
	 * 
	 */
	public QuadNode() {
		parent = nwNode = neNode = swNode = seNode = null;
		min = max = null;
		entities = new ArrayList<T>();
	}
	
	/**
	 * 
	 */
	public QuadNode(QuadNode<T> parent, Vector2D minVector, Vector2D maxVector) {
		this();
		this.parent = parent;
		this.min = minVector;
		this.max = maxVector;
	}
	
	public void addEntity(T entity) {
		if(entities.size() < MAX_ENTITIES)
			entities.add(entity);
		else {
			double minX = min.getX(), maxX = max.getX();
			double minY = min.getY(), maxY = max.getY();
			double midX = (maxX - minX) / 2, midY = (maxY - minY) / 2;
			nwNode = new QuadNode<T>(this, new Vector2D(minX, minY), new Vector2D(midX, midY));
			neNode = new QuadNode<T>(this, new Vector2D(midX, minY), new Vector2D(maxX, midY));
			swNode = new QuadNode<T>(this, new Vector2D(minX, midY), new Vector2D(midX, maxY));
			seNode = new QuadNode<T>(this, new Vector2D(midX, minY), new Vector2D(maxX, midY));
			for(T ent : entities) {
				double xPos = ent.getPosition().getX(), yPos = ent.getPosition().getY();
				double width = ent.getWidth(), height = ent.getHeight();
				if((xPos > minX && xPos < midX) && (yPos > minY && yPos < midY)) // Width and Height won't matter
					;// nwNode
			}
		}
	}
	
	public List<T> getEntities() {
		List<T> list = new ArrayList<T>();
		list.addAll(entities);
		if(nwNode != null)
			list.addAll(nwNode.getEntities());
		if(neNode != null)
			list.addAll(neNode.getEntities());
		if(swNode != null)
			list.addAll(swNode.getEntities());
		if(seNode != null)
			list.addAll(seNode.getEntities());
		return list;
	}
	
	public List<T> getEntities(Vector2D min, Vector2D max) {
		List<T> list = new ArrayList<T>();
		double aMinX = min.getX(), aMaxX = max.getX(); 
		double aMinY = min.getY(), aMaxY = max.getY();
		double bMinX = this.min.getX(), bMaxX = this.max.getX(); 
		double bMinY = this.min.getY(), bMaxY = this.max.getY();
		if(entities != null &&
				((aMinX > bMinX && aMinX < bMaxX) || // aMinX is inside b x-bounds
						(aMaxX > bMinX && aMaxX < bMaxX) || // aMaxX is inside b x-bounds
						(aMinX < bMinX && aMaxX > bMaxX)) && // aMinX and aMaxX surround b x-bounds
				((aMinY > bMinY && aMinY < bMaxY) || // aMinY is inside b y-bounds
								(aMaxY > bMinY && aMaxY < bMaxY) || // aMaxY is inside b Y-bounds
								(aMinY < bMinY && aMaxY > bMaxY))) // aMinY and aMaxY surround b Y-bounds
			list.addAll(entities);
		if(nwNode != null)
			list.addAll(nwNode.getEntities());
		if(neNode != null)
			list.addAll(neNode.getEntities());
		if(swNode != null)
			list.addAll(swNode.getEntities());
		if(seNode != null)
			list.addAll(seNode.getEntities());
		return list;
	}
	
	public QuadNode<T> getParent() {
		return parent;
	}
	
	public Vector2D getMin() {
		return min;
	}
	
	public Vector2D getMax() {
		return max;
	}
}
