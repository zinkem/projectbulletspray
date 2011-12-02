package pbs.parser;

import java.util.ArrayList;

import jig.engine.util.Vector2D;

import pbs.*;
import pbs.parser.Statements.*;
import pbs.parser.BooleanElements.*;
import pbs.parser.ExpressionElements.*;

public class Elements {

    //entity descriptions... 
    public abstract static class ObjectDescription {
	//returns an entity matching this description
	//public abstract ALAYERTYPE getLayerType();
	public abstract void mutate(Level l);	
    }

    public static class TriggerDescription extends ObjectDescription {
	//triggers have a list of statements that get added to the
	//event queue when they are triggered
	ArrayList<Statement> stmtlist;

	public TriggerDescription(ArrayList<Statement> sl){
	    stmtlist = sl;
	}

	public void mutate(Level l){
	    //this method SHOULD add a trigger with the specified stmtlist to the event layer
	    System.out.println("Trigger Description");

	    if(stmtlist != null){
		for(Statement s : stmtlist){
		    System.out.print("Not executed yet: ");
		    s.execute(l);
		}
	    }
	}
    }

    public static class timedTrigger extends TriggerDescription {
	public timedTrigger(ArrayList<Statement> sl){ super(sl); }

	public void mutate(Level l){
	    System.out.print("Timed ");
	    super.mutate(l);
	}
    }

    public static class collisionTrigger extends TriggerDescription {
	public collisionTrigger(ArrayList<Statement> sl){ super(sl); }

	public void mutate(Level l){
	    System.out.print("Collision ");
	    super.mutate(l);
	}
    }

    public static class onscreenTrigger extends TriggerDescription {
	public onscreenTrigger(ArrayList<Statement> sl){ super(sl); }

	public void mutate(Level l){
	    System.out.print("Onscreen ");
	    super.mutate(l);
	}
    }


    public static class EntityDescription extends ObjectDescription {

	ArrayList<Param> paramlist;

	public EntityDescription(ArrayList<Param> pl){
	    paramlist = pl;
	}

	public void mutate(Level l){
	    System.out.println("Entity Description");
	}
    }

    public static class fxEntity extends EntityDescription {
	public fxEntity(ArrayList<Param> pl){ super(pl); }

	public void mutate(Level l){
	    //get fx layer from level
	    //create entity
	    //evaluate parameters
	    //add entity to level layer
	    System.out.print("Fx ");
	    super.mutate(l);
	}
    }

    public static class enemyEntity extends EntityDescription {
	public enemyEntity(ArrayList<Param> pl){ super(pl); }

	public void mutate(Level l){
	    //get enemy layer from level
	    //create entity
	    //evaluate parameters
	    //add entity to level layer
	    System.out.print("Enemy ");
	    super.mutate(l);

	}
    }

    public static class staticEntity extends EntityDescription {
	public staticEntity(ArrayList<Param> pl){ super(pl); }

	public void mutate(Level l){
	    //get static layer from level
	    //create entity
	    //evaluate parameters
	    //add entity to level layer
	    System.out.print("Static ");
	    super.mutate(l);
	}
    }

    //parameters for entities
    public interface Param {
	public abstract boolean mutate(Entity e);
    }

    public static abstract class VectorParam implements Param {
	Vector2D vec;
	public VectorParam(Vector2D v){ vec = v; }
    }

    public static class VelocityParam extends VectorParam {
	public VelocityParam(Vector2D v){ super(v); }
	public boolean mutate(Entity e){
	    e.setVelocity(vec);
	    return true;
	}
    }

   public static class PositionParam extends VectorParam {
	public PositionParam(Vector2D p){ super(p); }
	public boolean mutate(Entity e){
	    e.setPosition(vec);
	    return true;
	}
    }
}