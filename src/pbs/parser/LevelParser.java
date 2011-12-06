package pbs.parser;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

import jig.engine.util.Vector2D;

import pbs.parser.Elements.*;
import pbs.parser.Statements.*;
import pbs.parser.ExpressionElements.*;
import pbs.parser.BooleanElements.*;
import pbs.Level;

public class LevelParser {

    public static String SPRITE_SHEET = "resources/pbs-spritesheet.png";

    //static strings, define 'reserved words'
    public static String TEMPLATE = "template";
    public static String CREATE = "create";
    public static String IF = "if";
    public static String SET = "set";
    public static String END = "end";

    //the following constants are entity types
    public static String FX =  "fx";
    public static String ENEMY = "enemy";
    public static String STATIC = "static";
    public static String TIMED = "timed";
    public static String COLLISION = "collision";
    public static String ONSCREEN = "onscreen";

    protected boolean ready;
    protected String err;

    protected String filename;
    protected Scanner source;

    protected String ctoken;

    protected Level thislevel;

    public LevelParser(String fname){
	filename = fname;
	thislevel = null;
	err = "";

	try {
	    source = new Scanner(LevelParser.class.getResourceAsStream("/"+fname));
	    ready = true;
	} catch(Exception e){
	    System.out.println("Error in LevelParser: " + e);
	    err = e.toString();
	    ready = false;
	}
    }


    public Level createLevel(){
	if(!ready){
	    System.out.println("Parser not ready: " + err);
	    return null;
	}

	thislevel = new Level();

	Pattern p = source.delimiter();
	System.out.println(p.pattern());

	ctoken = source.next();
	Statement s;

	System.out.print(ctoken + " ");

	//from the start statement, parse out statements and execute them
	while(source.hasNext()){
	    s = nextStatement();
	    if(s != null){
		//s.execute(thislevel);
		//normally, we would add statements to level event queue
		thislevel.addStatement(s);
	    } else {
		System.out.println("Aborting parse: " + err);
		return null;
	    }

	    if(match("endspec"))
	       break;
	}

	return thislevel;

    }

    protected String symbol(){
	String r = ctoken;
	ctoken = nextToken();
	return r;
    }
    
    protected int num(){
	boolean isnum = ctoken.matches("\\d+");
	int n = 0;
	if(isnum){
	    n = Integer.parseInt(ctoken);
	    ctoken = nextToken();
	}
	System.out.println("isnum is " + isnum + ", " + n );
	return n;
    }

    private boolean match(String s){
	boolean matches = ctoken.equalsIgnoreCase(s);
	if(matches){
	    if(source.hasNext())
		ctoken = nextToken();
	    else
		System.out.println("EOF");
	}	
	return matches;
    }
    
    private String nextToken(){
	String s = null;
	try{
	    s = source.next();
	} catch (Exception e) {
	    System.out.println("Scanner halted unexpectedly");
	}
	return s;
    }

    public Statement nextStatement(){

	if(match(TEMPLATE)){
	    return addTemplate();
	} else if(match(CREATE)) {
	    return addEntity();
	} else if(match(IF)){
	    return ifStmt();
	} else if(match(SET)){
	    return setStmt();
	}

	return null;
    }


    //classes get added to entity template hash
    public Statement addTemplate(){
	AddTemplate s = new AddTemplate();

	String name = ctoken;
	if(match(name)){
	    s.setName(name);
	} 

	ObjectDescription od = objdesc();
	if(od != null){
	    s.setDescription(od);
	}

	if(match(END)){
	    return s;
	}

	err = "template creation failed, no end marker found";
	return null;
    }

    //events get added to level event queue
    public Statement addEntity(){
	AddEntity s = new AddEntity();

	ObjectDescription od = objdesc();
	if(od != null){
	    s.setDescription(od);
	} else {
	    System.out.println(err);
	}

	return s;
    }
    

    protected Statement ifStmt(){
	
	return null;

	/*
	//parse conditional
	BooleanExpression c = boolExpr();
	//parse statement after
	Statement s = nextStatement();
	
	return new Conditional(c, s);
	*/
    }

    protected BooleanExpression boolExpr(){
	return null;
    }

    protected Statement setStmt(){
	return null;
    }



    protected ObjectDescription objdesc(){
	//type followed by param list

	ObjectDescription od;

	if(match("fx")) {
	    return fx();
	} else if(match("enemy")) {
	    return enemy();
	} else if(match("static")) {
	    return staticEnt();
	} else if(match("timed")) {
	    return timed();
	} else if(match("onscreen")) {
	    return onscreen();
	} else if(match("collision")) {
	    return collision();
	}

	err = "Invalid entity identifier";
	return null;
	
    }
    
    protected ObjectDescription fx(){
	return new fxEntity(SPRITE_SHEET + "#large_burst", paramList());
    }

    protected ObjectDescription enemy(){
	return new enemyEntity(SPRITE_SHEET + "#shuttle", paramList());
    }

    protected ObjectDescription staticEnt(){
	return new staticEntity(SPRITE_SHEET + "#hex", paramList());
    }

    protected ObjectDescription timed(){
	return new timedTrigger(stmtList());
    }

    protected ObjectDescription onscreen(){
	return new onscreenTrigger(stmtList());
    }

    protected ObjectDescription collision(){
	return new collisionTrigger(stmtList());
    }

    protected ArrayList<Statement> stmtList(){
	
	ArrayList<Statement> stmtlist = new ArrayList<Statement>();
	
	Statement s = nextStatement();
	while(s != null){
	    stmtlist.add(s);
	    s = nextStatement();
	}
	
	return stmtlist;
    }

    protected ArrayList<Param> paramList(){
	ArrayList<Param> paramlist = new ArrayList<Param>();

	Param p = nextParam();
	while(p != null){
	    paramlist.add(p);
	    p = nextParam();
	}

	return paramlist;
    }


    protected Param nextParam(){
	//here we define list of parameters and return the proper parameter
	if(match("position")){
	    return new PositionParam(new Vector2D(num(), num()));
	} else if(match("velocity")){
	    return new VelocityParam(new Vector2D(num(), num()));
	} else if(match("update")){

	} else if(match("render")){

	} else if(match("weapon")){

	} else if(match("spawnin")){
	    return new SpawnTime(num());
	}

	return null;

    }

 }