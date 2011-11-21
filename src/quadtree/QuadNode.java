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
	public QuadNode(T entity, QuadNode<T> parent, Vector2D minVector, Vector2D maxVector) {
		this();
		this.entities.add(entity);
		this.parent = parent;
		this.min = minVector;
		this.max = maxVector;
	}
	
	public void addEntity(T entity) {
		if(entities.size() < MAX_ENTITIES)
			entities.add(entity);
		else {
			
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
