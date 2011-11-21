package pbs.parser;

import pbs.parser.Elements.*;
import pbs.Level;

import java.io.File;
import java.util.Scanner;

public class LevelParser {

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
	}

	thislevel = new Level();
	ctoken = source.next();
	Statement s;

	//from the start statement, parse out statements and execute them
	while(source.hasNext()){
	    s = nextStatement();
	    if(s != null)
		s.execute(thislevel);
	    else {
		System.out.println("Aborting parse: " + err);
		return null;
	    }
	}

	return thislevel;

    }


    private boolean match(Lexeme l){

	boolean matches = false;
	String s = "";

	switch(l){
	case TEMPLATE:
	    matches = ctoken.compareTo("template") == 0;
	    break;
	case CREATE:
	    matches = ctoken.compareTo("create") == 0;
	    break;
	case END:
	    matches = ctoken.compareTo("end") == 0;
	    break;
	case ENTTYPE:
	    matches = ctoken.compareTo("fx") == 0 ||
		ctoken.compareTo("enemy") == 0 ||
		ctoken.compareTo("static") == 0;
	    break;
	case TRIGGERTYPE:
	    matches = ctoken.compareTo("timed") == 0 ||
		ctoken.compareTo("collision") == 0 ||
		ctoken.compareTo("onscreen") == 0;
	    break;
	case SYMBOL:
	    matches = true;
	    break;
	default:
	    break;
	}

	if(matches){
	    ctoken = source.next();
	    return true;
	}
	
	return false;
    }

    public Statement nextStatement(){
	if(match(Lexeme.TEMPLATE)){
	    return addTemplate();
	} else if(match(Lexeme.CREATE)) {
	    return addEvent();
	}

	return null;
    }


    //classes get added to entity template hash
    public Statement addTemplate(){
	AddTemplate s = new AddTemplate();

	String name = ctoken;
	if(match(Lexeme.SYMBOL)){
	    s.setName(name);
	} 

	ObjectDescription od = objdesc();
	if(od != null){
	    s.setDescription(od);
	}

	if(match(Lexeme.END)){
	    return s;
	}

	err = "template creation failed, no end marker found";
	return null;
    }

    protected ObjectDescription objdesc(){
	//type followed by param list
	String type = ctoken;
	if(match(Lexeme.ENTTYPE)){
	    return entobject(type);
	} else if(match(Lexeme.TRIGGERTYPE)){
	    return trigobject(type);
	}
	System.out.println("ret null:" + ctoken);
	return null;

    }

    protected ObjectDescription entobject(String s){
	return null;
    }
    
    protected ObjectDescription trigobject(String s){
	return null;
    }


    //events get added to level event queue
    public Statement addEvent(){
	return new AddEvent();
    }
    
}