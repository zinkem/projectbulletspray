/**
 * 
 */
package quadtree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jig.engine.Sprite;
import jig.engine.util.Vector2D;

/**
 * @author Skylar Hiebert
 *
 */
public class QuadNode<T extends Sprite> {
	private static int maxEntities = 10;
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
		if (entities == null) { /* Case: entities is full and has been made null, add to a child node */
			double minX = min.getX(), maxX = max.getX();
			double minY = min.getY(), maxY = max.getY();
			double midX = minX + (maxX - minX) / 2, midY = minY + (maxY - minY) / 2;
			double xPos = entity.getPosition().getX(), yPos = entity.getPosition().getY();
			double width = entity.getWidth(), height = entity.getHeight();
			if(((xPos > minX || xPos + width > minX) && (xPos < midX || xPos + width < midX)) &&
					((yPos > minY || yPos + height > minY) && (yPos < midY || yPos + height < midY))) {
				nwNode.addEntity(entity); // nwNode
			} 
			if(((xPos > midX || xPos + width > midX) && (xPos < maxX || xPos + width < maxX)) &&
					((yPos > minY || yPos + height > minY) && (yPos < midY || yPos + height < midY))) {
				neNode.addEntity(entity); //neNode				
			} 
			if(((xPos > minX || xPos + width > minX) && (xPos < midX || xPos + width < midX)) &&
					((yPos > midY || yPos + height > midY) && (yPos < maxY || yPos + height < maxY))) {
				swNode.addEntity(entity); //swNode
			} 
			if(((xPos > midX || xPos + width > midX) && (xPos < maxX || xPos + width < maxX)) &&
					((yPos > midY || yPos + height > midY) && (yPos < maxY || yPos + height < maxY))) {
				seNode.addEntity(entity); //seNode
			}
			
		} else if(entities.size() < maxEntities) { /* Case: entities is not full */ 
			entities.add(entity);
		} else  { /* Case: entities is full, create children nodes and move entities to children, make entities null */
			double minX = min.getX(), maxX = max.getX();
			double minY = min.getY(), maxY = max.getY();
			double midX = minX + (maxX - minX) / 2, midY = minY + (maxY - minY) / 2;
			nwNode = new QuadNode<T>(this, new Vector2D(minX, minY), new Vector2D(midX, midY));
			neNode = new QuadNode<T>(this, new Vector2D(midX, minY), new Vector2D(maxX, midY));
			swNode = new QuadNode<T>(this, new Vector2D(minX, midY), new Vector2D(midX, maxY));
			seNode = new QuadNode<T>(this, new Vector2D(midX, midY), new Vector2D(maxX, maxY));
			Iterator<T> iter = entities.iterator();
			entities = null;
			while(iter.hasNext()) {
				addEntity(iter.next());
			}
			addEntity(entity);
		}
	}
	
	public List<T> getEntities() {
		List<T> list = new ArrayList<T>();
		if(entities != null)
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
	
	public List<T> getPayload() {
		return entities;
	}
	
	public List<T> getEntities(Vector2D min, Vector2D max) {
		List<T> list = new ArrayList<T>();
		double aMinX = min.getX(), aMaxX = max.getX(); 
		double aMinY = min.getY(), aMaxY = max.getY();
		double bMinX = this.min.getX(), bMaxX = this.max.getX(); 
		double bMinY = this.min.getY(), bMaxY = this.max.getY();
		if(entities != null &&
				((aMinX > bMinX && aMinX < bMaxX) || // aMinX is inside this x-bounds
						(aMaxX > bMinX && aMaxX < bMaxX) || // aMaxX is inside this x-bounds
						(aMinX < bMinX && aMaxX > bMaxX)) && // aMinX and aMaxX surround this x-bounds
				((aMinY > bMinY && aMinY < bMaxY) || // aMinY is inside this y-bounds
								(aMaxY > bMinY && aMaxY < bMaxY) || // aMaxY is inside this Y-bounds
								(aMinY < bMinY && aMaxY > bMaxY))) { // aMinY and aMaxY surround this Y-bounds
			list.addAll(entities);
		}
		if(nwNode != null)
			list.addAll(nwNode.getEntities(min, max));
		if(neNode != null)
			list.addAll(neNode.getEntities(min, max));
		if(swNode != null)
			list.addAll(swNode.getEntities(min, max));
		if(seNode != null)
			list.addAll(seNode.getEntities(min, max));
		return list;
	}
	
	public int getSize() {
		if(entities != null)
			return entities.size();
		else 
			return nwNode.getSize() + neNode.getSize() + swNode.getSize() + seNode.getSize();
	}
	
	public static void setMaxEntitiesPerNode(int newMaxEntities) {
		maxEntities = newMaxEntities;
	}
	
	public Vector2D getMin() {
		return min;
	}
	
	public Vector2D getMax() {
		return max;
	}
	
	/**
	 * @return the parent
	 */
	public QuadNode<T> getParent() {
		return parent;
	}

	/**
	 * @return the nwNode
	 */
	public QuadNode<T> getNwNode() {
		return nwNode;
	}

	/**
	 * @return the neNode
	 */
	public QuadNode<T> getNeNode() {
		return neNode;
	}

	/**
	 * @return the swNode
	 */
	public QuadNode<T> getSwNode() {
		return swNode;
	}

	/**
	 * @return the seNode
	 */
	public QuadNode<T> getSeNode() {
		return seNode;
	}
}
