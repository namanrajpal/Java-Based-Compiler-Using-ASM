package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;

public class BinaryExpression extends Expression {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((e0 == null) ? 0 : e0.hashCode());
		result = prime * result + ((e1 == null) ? 0 : e1.hashCode());
		result = prime * result + ((op == null) ? 0 : op.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof BinaryExpression)) {
			return false;
		}
		BinaryExpression other = (BinaryExpression) obj;
		if (e0 == null) {
			if (other.e0 != null) {
				return false;
			}
		} else if (!e0.equals(other.e0)) {
			return false;
		}
		if (e1 == null) {
			if (other.e1 != null) {
				return false;
			}
		} else if (!e1.equals(other.e1)) {
			return false;
		}
		if (op == null) {
			if (other.op != null) {
				return false;
			}
		} else if (!op.equals(other.op)) {
			return false;
		}
		return true;
	}

	final Expression e0;
	final Token op;
	final Expression e1;

	public BinaryExpression(Token firstToken, Expression e0, Token op, Expression e1) {
		super(firstToken);
		this.e0 = e0;
		this.op = op;
		this.e1 = e1;
	}

	public Expression getE0() {
		return e0;
	}

	public Expression getE1() {
		return e1;
	}

	public Token getOp() {
		return op;
	}

	@Override
	public String toString() {
		return "BinaryExpression [e0=" + e0.getType() + ", op=" + op.getText() + ", e1=" + e1.getType() + "]";
	}
	
	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitBinaryExpression(this,arg);
	}

}
