package pbs;

import java.util.ArrayList;
import java.util.Iterator;

import jig.engine.util.Vector2D;

import pbs.Level.Layer;
import pbs.Entity.*;
import pbs.parser.Statements.*;
import pbs.parser.Elements.*;

public class Trigger implements CustomTrigger {

    private ArrayList<Statement> stack;
    
    public Trigger(ArrayList<Statement> add) {
	stack = add;
    }
    
    public void setStatements(ArrayList<Statement> s){
	stack = s;
    }
    
    public void fire(Level l, Vector2D p){
	ArrayList<Param> plist = new ArrayList<Param>();
	plist.add(new TranslatePositionParam(p));

	Iterator<Statement> itr = stack.iterator();
	if(itr != null)
	    while(itr.hasNext()){
		Statement s = itr.next();
		s.finalParams(plist);
		l.addStatement(s);
	    }
    }
	
}
