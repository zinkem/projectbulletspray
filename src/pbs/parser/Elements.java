package pbs.parser;

import pbs.*;

import jig.engine.util.Vector2D;


public class Elements {

    public enum Lexeme {
	TEMPLATE,
	    CREATE,
	    SYMBOL,
	    VECTOR,
	    PARAM,
	    TRIGGERTYPE,
	    ENTTYPE,
	    END	    
    }


    //statements are top level commands that alter the level data
    //upon execution
    public static abstract class Statement {
	public abstract boolean execute(Level l);
    }

    //add event is a list of events to get added to the level's event queue
    public static class AddEvent extends Statement {
	//Event root;

	public AddEvent(){
	}

	//does the work of adding events to levels
	public boolean execute(Level l){
	    //l.add(root);
	    System.out.println("Add event to level event queue!");
	    return true;
	}
    }

    public static class AddTemplate extends Statement {
	
	String name;
	ObjectDescription theObject;

	public AddTemplate(){
	    name = "";
	    theObject = null;
	}

	public boolean execute(Level l){
	    //adds new 'class template' to level
	    System.out.println("Add template <" + name + 
			       "> to level template hash!");
	    return true;
	}

	public void setName(String n){
	    name = n;
	}

	public void setDescription(ObjectDescription od){
	    theObject = od;
	}

    }
    
    enum EntType {
		    
    }

    //entity descriptions... 
    public abstract static class ObjectDescription {
	//returns an entity matching this description
	public abstract Entity getEntity();	
    }

    public static class TriggerDescription extends ObjectDescription {

	public TriggerDescription(){
	}

	public Entity getEntity(){
	    return null;
	}
    }

    public static class EntityDescription extends ObjectDescription {
	public EntityDescription(){
	}

	public Entity getEntity(){
	    return null;
	}
    }
}