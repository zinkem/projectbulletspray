/**
 * 
 */
package quadtree;

import java.util.*;

import jig.engine.util.Vector2D;

import pbs.Entity;

/**
 * @author Skylar Hiebert
 *
 */
public class QuadTree<T extends Entity> implements Iterable<T> {
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
	
	public void add(Entity entity) {
		
	}
	
	public void add(QuadNode<T> node) {
		
	}
	
	public ArrayList<Entity> getEntities(Vector2D min, Vector2D max) {
		return null;
	}
	
	//for conforming to iterable interface
	public Iterator<T> iterator(){
	    //should return entire tree as arraylist?
	    return null;
	}

}
