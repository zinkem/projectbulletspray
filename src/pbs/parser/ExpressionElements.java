package pbs.parser;
import pbs.*;

public class ExpressionElements {
    public static abstract class Expression {
	public abstract int eval();
    }

    public static abstract class BinaryOp extends Expression {
	Expression left;
	Expression right;
	
	public BinaryOp(Expression l, Expression r){
	    left = l;
	    right = r;
	}
    }

    public static class Addition extends BinaryOp {
	public Addition(Expression l, Expression r){ super(l, r); }
	public int eval(){ return left.eval() + right.eval(); }
    }

    public static class Subtraction extends BinaryOp {
	public Subtraction(Expression l, Expression r){ super(l, r); }
	public int eval(){ return left.eval() - right.eval(); }
    }

    public static class Multiplication extends BinaryOp {
	public Multiplication(Expression l, Expression r){ super(l, r); }
	public int eval(){ return left.eval() * right.eval(); }
    }

    public static class Division extends BinaryOp {
	public Division(Expression l, Expression r){ super(l, r); }
	public int eval(){ return left.eval() / right.eval(); }
    }

    public static class Constant extends Expression {
	int value;
	public Constant(int v) { value = v; }
	public int eval(){ return value; }
    }

    public static abstract class LevelData extends Expression {
	Level leveldata;
	public LevelData(Level l){
	    leveldata = l;
	}
    }

    public static class Score extends LevelData {
	public Score(Level l){ super(l); }
	public int eval(){
	    return leveldata.getScore();
	}
    }

    public static class GameTime extends LevelData {
	public GameTime(Level l){ super(l); }
	public int eval(){
	    return leveldata.getTime();
	}
    }
    


}