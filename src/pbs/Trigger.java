package pbs;

import java.util.ArrayList;

import pbs.Entity.*;
import pbs.parser.Statements.*;

public class Trigger extends Entity{
	private Level lvl;
	private ArrayList<Statement> stack;
	
	public Trigger(String imgrsc, Level l, ArrayList<Statement> add) {
		super(imgrsc);
		this.lvl = l;
		this.stack = add;
	}
	
	
}
