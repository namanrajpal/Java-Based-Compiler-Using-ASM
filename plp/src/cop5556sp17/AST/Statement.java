package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;

public abstract class Statement extends ASTNode {

	public Statement(Token firstToken) {
		super(firstToken);
	}

	abstract public Object visit(ASTVisitor v, Object arg) throws Exception;

}
