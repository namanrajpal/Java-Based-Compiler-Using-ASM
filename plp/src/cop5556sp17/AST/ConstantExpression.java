package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;

public class ConstantExpression extends Expression {

	public ConstantExpression(Token firstToken) {
		super(firstToken);
	}
	

	@Override
	public String toString() {
		return "ConstantExpression [firstToken=" + firstToken + "]";
	}


	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitConstantExpression(this,arg);
		
	}

}
