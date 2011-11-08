/**
 * 
 */
package quadtree;

import java.util.ArrayList;
import java.util.List;

import pbs.Entity;
import jig.engine.util.Vector2D;

/**
 * @author Skylar Hiebert
 *
 */
public class QuadNode<T extends Entity> {
	private QuadNode<T> parent, nwNode, neNode, swNode, seNode;
	private Vector2D min, max;
	private T entity;

	/**
	 * 
	 */
	public QuadNode() {
		parent = nwNode = neNode = swNode = seNode = null;
		min = max = null;
		entity = null;
	}
	
	/**
	 * 
	 */
	public QuadNode(T entity, QuadNode<T> parent, Vector2D minVector, Vector2D maxVector) {
		this.entity = entity;
		this.parent = parent;
		this.min = minVector;
		this.max = maxVector;
		nwNode = neNode = swNode = seNode = null;
	}
	
	public List<T> getEntities() {
		List<T> list = new ArrayList<T>();
		list.add(entity);
		list.addAll(nwNode.getEntities());
		list.addAll(neNode.getEntities());
		list.addAll(swNode.getEntities());
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
