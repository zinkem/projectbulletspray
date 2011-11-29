package pbs.parser;

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
}