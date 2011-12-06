package pbs;

import java.util.ArrayList;
import java.util.Iterator;

import pbs.Level.Layer;
import pbs.Entity.*;
import pbs.parser.Statements.*;

public class Trigger extends Entity{
	private Level lvl;
	private ArrayList<Statement> stack;
	
	public Trigger(String imgrsc, Level l, ArrayList<Statement> add) {
		super(imgrsc);
		lvl = l;
		stack = add;
	}
	
	public Trigger(String imgrsc, Level l){
		super(imgrsc);
		lvl = l;
	}
	
	public void setStatements(ArrayList<Statement> s){
		stack = s;
	}
	
	public void setLevel(Level l){
		lvl = l;
	}
	
	public void addToLevel(){
		Iterator<Statement> itr = stack.iterator();
		while(itr.hasNext()){
			lvl.addStatement(itr.next());
		}
	}

	
}
