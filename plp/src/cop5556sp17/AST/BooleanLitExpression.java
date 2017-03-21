package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;

public class BooleanLitExpression extends Expression {

	private final Boolean value;

	public BooleanLitExpression(Token firstToken) {
		super(firstToken);
		value = firstToken.getText().equals("true");
	}

	@Override
	public String toString() {
		return "BooleanLitExpression [value=" + getValue() + "]";
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitBooleanLitExpression(this, arg);
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		if (!(obj instanceof BooleanLitExpression)) {
			return false;
		}
		BooleanLitExpression other = (BooleanLitExpression) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	public Boolean getValue() {
		return value;
	}



}
