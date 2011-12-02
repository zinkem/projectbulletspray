package pbs.parser;

import java.util.ArrayList;

import jig.engine.util.Vector2D;

import pbs.*;
import pbs.parser.Elements.*;
import pbs.parser.BooleanElements.*;
import pbs.parser.ExpressionElements.*;


public class Statements {

    //statements are top level commands that alter the level data
    //upon execution, set level parameters, and add entities
    //the event queue in the level is a series of statements
    public interface Statement {
	public abstract boolean execute(Level l);
    }

    //if the conditional is satisfied, executes a statement on execute
    public static class Conditional implements Statement {
	
	BooleanOperation boolop;
	Statement stmt;

	public Conditional(BooleanOperation b, Statement s){
	    boolop = b;
	    stmt = s;
	}

	public boolean execute(Level l){
	    boolean b = boolop.eval();
	    
	    if(b){
		stmt.execute(l);
	    }

	    return b;
	}
    }

    //addEntity adds an object to the level on execute
    public static class AddEntity implements Statement {

	ObjectDescription theObject;
	
	public AddEntity(){
	    theObject = null;
	}
	
	public void setDescription(ObjectDescription od){
	    theObject = od;
	}

	//does the work of adding events to levels
	public boolean execute(Level l){
	    System.out.println("Creation event!");
	    if(theObject != null)
		theObject.mutate(l);

	    return true;
	}
    }


    //adds a template to the level template hash (entity symbol table) on execute
    public static class AddTemplate extends AddEntity {
	
	String name;

	public AddTemplate(){
	    name = "";
	    theObject = null;
	}

	public boolean execute(Level l){
	    //adds new 'class template' to level
	    System.out.println("[Add template <" + name + 
			       "> to level template hash] ");
	    //we would not normally 'mutate' here... this is just for demo
	    //we want to add 'theObject' to the level template hash
	    //l.addToTemplateHash(name, theObject);
	    theObject.mutate(l);
	    return true;
	}

	public void setName(String n){
	    name = n;
	}

    }


    //setter statements, these all set some parameter of the level
    public static class Setter implements Statement {
	public Setter(){
	}

	public boolean execute(Level l){
	    return true;
	}
    }

    /*
      Candidates:
      background image
      score
      avatar hp
      
     */

    public static abstract class VectorSetter implements Statement {
	Vector2D vec;
	public VectorSetter(Vector2D v) { vec = v; }
	public abstract boolean execute(Level l);
    }

    public static class CameraSetter extends VectorSetter {
	public CameraSetter(Vector2D c) { super(c); }
	public boolean execute(Level l) { l.setCam(vec); return true; }
    }

    public static class ScrollSetter extends VectorSetter {
	public ScrollSetter(Vector2D c) { super(c); }
	public boolean execute(Level l) { l.setScrollSpeed(vec); return true; }
    }


    public static class ScoreModifier implements Statement {
	int mod;
	public ScoreModifier(int m) { mod = m; }
	public boolean execute(Level l) { l.modScore(mod); return true; }
    }

}