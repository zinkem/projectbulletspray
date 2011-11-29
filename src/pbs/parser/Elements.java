package pbs.parser;

import java.util.ArrayList;

import jig.engine.util.Vector2D;

import pbs.*;

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
    //upon execution-- usually adding events. 
    public static abstract class Statement {
	public abstract boolean execute(Level l);
    }

    public static class Conditional extends Statement {
	public Conditional(){
	}

	public boolean execute(Level l){
	    return true;
	}
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
	    theObject.insertInto(l);
	    return true;
	}

	public void setName(String n){
	    name = n;
	}

	public void setDescription(ObjectDescription od){
	    theObject = od;
	}

    }

    //entity descriptions... 
    public abstract static class ObjectDescription {
	//returns an entity matching this description
	public abstract void insertInto(Level l);	
    }

    public static class TriggerDescription extends ObjectDescription {
	//triggers have a list of statements that get added to the
	//event queue when they are triggered
	ArrayList<Statement> stmtlist;

	public void setStatements(ArrayList<Statement> sl){
	    stmtlist = sl;
	}

	public TriggerDescription(){
	    stmtlist = null;
	}

	public void insertInto(Level l){
	    //this method SHOULD add a trigger with the specified stmtlist to the event layer
	    System.out.println("Trigger Description");

	    if(stmtlist != null){
		for(Statement s : stmtlist){
		    s.execute(l);
		}
	    }
	}
    }

    public static class timedTrigger extends TriggerDescription {
	public timedTrigger(){
	}
    }

    public static class collisionTrigger extends TriggerDescription {
	public collisionTrigger(){
	}
    }

    public static class onscreenTrigger extends TriggerDescription {
	public onscreenTrigger(){
	}
    }


    public static class EntityDescription extends ObjectDescription {

	//ArrayList<Param> paramlist;

	public EntityDescription(){
	}

	public void insertInto(Level l){
	    //
	    System.out.println("Entity Description");
	}
    }

    public static class fxEntity extends EntityDescription {
	public fxEntity(){
	}
    }

    public static class enemyEntity extends EntityDescription {

	public enemyEntity(){
	}
    }

    public static class staticEntity extends EntityDescription {
	public staticEntity(){
	}
    }

}