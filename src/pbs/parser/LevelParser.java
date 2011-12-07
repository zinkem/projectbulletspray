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
import pbs.Entity.*;
import pbs.Updater.*;
import pbs.Weapons.*;
import pbs.Renders.*;

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
		System.out.println("Aborting parse at " + ctoken + " : " + err);
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
	boolean isnum = ctoken.matches("-?\\d+");
	int n = 0;
	int modifier = 1;

	if(isnum){
	    n = Integer.parseInt(ctoken);
	    ctoken = nextToken();
	}
	//System.out.println("isnum is " + isnum + ", " + n );
	return n*1;
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
	} if(match(END)){
	    return null;
	}

	return null;
    }


    //classes get added to entity template hash
    public Statement addTemplate(){

	String name = symbol();
	
	ObjectDescription od = objdesc();
	
	if(match(END)){
	    return new AddTemplate(name, od);
	}
	
	err = "template creation failed, no end marker found";
	return null;
    }

    //events get added to level event queue
    public Statement addEntity(){

	return new AddEntity(objdesc());
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
	} else {
	    return new TemplateDescription(symbol(), paramList());
	}
    }
    
    protected String imgRscName(){
	return SPRITE_SHEET + "#" + symbol();
    }

    protected ObjectDescription fx(){
	return new fxEntity(imgRscName(), paramList());
    }

    protected ObjectDescription enemy(){
	return new enemyEntity(imgRscName(), paramList());
    }

    protected ObjectDescription staticEnt(){
	return new staticEntity(imgRscName(), paramList());
    }

    protected ObjectDescription timed(){
	return new timedTrigger(num(), paramList(), stmtList());
    }

    protected ObjectDescription onscreen(){
	return new onscreenTrigger(paramList(), stmtList());
    }

    protected ObjectDescription collision(){
	return new collisionTrigger(paramList(), stmtList());
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
	    return getUpdate();
	} else if(match("render")){
	    return getRender();
	} else if(match("weapon")){
	    return getWeapon();
	} else if(match("spawnin")){
	    return new SpawnTime(num());
	}

	return null;

    }

    protected UpdateParam getUpdate(){
	return new UpdateParam(new YOcil());
    }

    protected RenderParam getRender(){
	//FollowVelocity
	//PointDirection
	//Spin
	return new RenderParam(new Spin(0.0, .005));
    }

    protected WeaponParam getWeapon(){
	if(match("outward")){
	    return new WeaponParam(new SurroundShot(num(), num(), num()));
	} else if(match("spinning")){
	    return new WeaponParam(new SpinningSurroundShot(num(), num(), num(), num()));
	}

	err = "No weapon parameter found";
	return null;
    }

 }