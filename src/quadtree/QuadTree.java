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
public class QuadTree<T extends Sprite> implements Iterable<T> {
	private QuadNode<T> root;
	private int size;
	private int width, height;
	
	public QuadTree() {
		this(1, 100, 100);
	}
	
	public QuadTree(int maxEntitiesPerNode, int width, int height) {
		QuadNode.setMaxEntitiesPerNode(maxEntitiesPerNode);
		this.width = width;
		this.height = height;
		this.root = null;
		size = 0;
	}
	
	public void add(T entity) {
		if(root == null) 
			root = new QuadNode<T>(null, new Vector2D(0,0), new Vector2D(width, height));
		root.addEntity(entity);
		size++;
	}
	
	public List<T> getAllEntities() {
		return root.getEntities();
	}
	
	public List<T> getEntities(Vector2D min, Vector2D max) {
		return root.getEntities(min, max);
	}
	
	//for conforming to iterable interface
	public Iterator<T> iterator(){
	    return root.getEntities().iterator();
	}
	
	public int getSize() {
		return size;
	}

	/**
	 * @return the root
	 */
	public QuadNode<T> getRoot() {
		return root;
	}

}
