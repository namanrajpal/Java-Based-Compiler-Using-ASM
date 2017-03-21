package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;

public class IntLitExpression extends Expression {

	public final int value;

	public IntLitExpression(Token firstToken) {
		super(firstToken);
		value = firstToken.intVal();
	}

	@Override
	public String toString() {
		return "IntLitExpression [value=" + value + "]";
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + value;
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
		if (!(obj instanceof IntLitExpression)) {
			return false;
		}
		IntLitExpression other = (IntLitExpression) obj;
		if (value != other.value) {
			return false;
		}
		return true;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitIntLitExpression(this, arg);
	}

}
