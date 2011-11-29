package pbs.parser;

import pbs.parser.ExpressionElements.*;

public class BooleanElements {

    public static abstract class BooleanOperation {
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
    
}