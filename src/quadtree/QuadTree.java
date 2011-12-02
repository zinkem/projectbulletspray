/**
 * 
 */
package quadtree;

import java.util.*;

import jig.engine.Sprite;
import jig.engine.util.Vector2D;

/**
 * @author Skylar Hiebert
 *
 */
public class QuadTree<E extends Sprite> implements Iterable<E> {
	private QuadNode<E> root;
	private int size;
	public Vector2D min, max;
	public int maxEntitiesPerNode;
	
	public QuadTree() {
		this(1, new Vector2D(0,0), new Vector2D(1000,1000));
	}
	
	public QuadTree(int maxEntitiesPerNode, Vector2D min, Vector2D max) {
		QuadNode.setMaxEntitiesPerNode(maxEntitiesPerNode);
		this.maxEntitiesPerNode = maxEntitiesPerNode;
		this.min = min;
		this.max = max;
		this.root = new QuadNode<E>(null, min, max);
		size = 0;
	}
	
	public void add(E entity) {
		root.addEntity(entity);
		size++;
	}
	
	public List<E> getAllEntities() {
		return root.getEntities();
	}
	
	public List<E> getEntities(Vector2D min, Vector2D max) {
		return root.getEntities(min, max);
	}
	
	//for conforming to iterable interface
	public Iterator<E> iterator(){
	    return root.getEntities().iterator();
	}
	
	public int getSize() {
		return size;
	}

	/**
	 * @return the root
	 */
	public QuadNode<E> getRoot() {
		return root;
	}

}
