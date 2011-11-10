package pbs;

import java.util.*;

import jig.engine.*;

//import quadtree.*;

public class EntityLayer<T extends Entity> implements Iterable<T>, ViewableLayer {
    
    //goal will be to change this to a quad tree
    protected ArrayList<T> entities;

    public EntityLayer(){
	entities = new ArrayList<T>();
    }

    public boolean isActive(){
	return true;
    }

    public void setActivation(boolean a){
    }

    public void render(RenderingContext rc){
	for(Entity e : entities){
	    e.render(rc);
	}
    }

    public void update(long deltaMs){
	Entity e;

	for( int i = size()-1; i >= 0; i--){
	    e = entities.get(i);
	    if(e.alive() == false){
		entities.remove(i);
	    } else {
		e.update(deltaMs);
	    }
	}
    }

    public Iterator<T> iterator(){
	return entities.iterator();
    }

    public void add(T e){
	entities.add(e);
    }
    
    public int size(){
	return entities.size();
    }

    public void clear(){
	entities.clear();
    }

}