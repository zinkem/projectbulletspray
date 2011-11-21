/**
 * 
 */
package quadtree;

import java.util.*;

import jig.engine.physics.Body;
import jig.engine.util.Vector2D;

/**
 * @author Skylar Hiebert
 *
 */
public class QuadTree<T extends Body> implements Iterable<T> {
	QuadNode<T> root;
	int size;
	
	public QuadTree() {
		this.root = null;
		size = 0;
	}
	
	public QuadTree(QuadNode<T> root) {
		this.root = root;
		size = 1;
	}
	
	public void add(T entity) {
		root.addEntity(entity);
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

}
