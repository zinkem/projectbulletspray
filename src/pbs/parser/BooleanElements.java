package pbs.parser;

import pbs.Level;
import pbs.parser.ExpressionElements.*;

public class BooleanElements {

    public interface BooleanExpression {
	public boolean eval();
    }

    public static abstract class BinaryBooleanExpression implements BooleanExpression {
	BooleanExpression left;
	BooleanExpression right;
	public BinaryBooleanExpression(BooleanExpression l, BooleanExpression r){
	    left = l;
	    right = r;
	}
    }

    public static class AndOp extends BinaryBooleanExpression {
	public AndOp(BooleanExpression l, BooleanExpression r){
	    super(l, r);
	}

	public boolean eval(){
	    return left.eval() && right.eval();
	}
    }

    public static class OrOp extends BinaryBooleanExpression {
	public OrOp(BooleanExpression l, BooleanExpression r){
	    super(l, r);
	}

	public boolean eval(){
	    return left.eval() || right.eval();
	}
    }

    
    public static abstract class BooleanOperation implements BooleanExpression {
	Expression left;
	Expression right;

	public BooleanOperation(Expression l, Expression r){
	    left = l;
	    right = r;
	}

	public abstract boolean eval();
    }

    public static class EqualTo extends BooleanOperation {
	public EqualTo(Expression l, Expression r){ super(l, r); }
	public boolean eval(){ return left.eval() == right.eval(); }
    }

    public static class LesserThan extends BooleanOperation {
	public LesserThan(Expression l, Expression r){ super(l, r); }
	public boolean eval(){ return left.eval() < right.eval(); }
    }

    public static class GreaterThan extends BooleanOperation {
	public GreaterThan(Expression l, Expression r){ super(l, r); }
	public boolean eval(){ return left.eval() > right.eval(); }
    }

    public static class LesserThanOrEqualTo extends BooleanOperation {
	public LesserThanOrEqualTo(Expression l, Expression r){ super(l, r); }
	public boolean eval(){ return left.eval() <= right.eval(); }
    }

    public static class GreaterThanOrEqualTo extends BooleanOperation {
	public GreaterThanOrEqualTo(Expression l, Expression r){ super(l, r); }
	public boolean eval(){ return left.eval() >= right.eval(); }
    }

    public static class BooleanValue implements BooleanExpression {
	boolean val;
	public BooleanValue(boolean b){ val = b; }
	public boolean eval(){ return val; }
    }

    //level switches give various yes/no answers about the level
    public static abstract class LevelSwitch implements BooleanExpression {
	Level level;
	public LevelSwitch(Level l){ level = l; }
    }

    public static class PlayerAlive extends LevelSwitch {
	public PlayerAlive(Level l){ super(l); }
	public boolean eval(){ return true; }
    }

    public static class LevelOver extends LevelSwitch {
	public LevelOver(Level l){ super(l); }
	public boolean eval(){ return true; }
    }

    //what other yes/no questions would we ask?
}