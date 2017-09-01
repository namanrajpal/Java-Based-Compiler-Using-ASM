package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;
import cop5556sp17.AST.Type.TypeName;

public class IdentLValue extends ASTNode {
	
	public TypeName val;
	public Dec dec;
	
	public IdentLValue(Token firstToken) {
		super(firstToken);
	}
	
	@Override
	public String toString() {
		return "IdentLValue [firstToken=" + firstToken.getText() + "]";
	}
	
	public Dec getDec()
	{
		return dec;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitIdentLValue(this,arg);
	}
	
	public TypeName getType(){
		return val;
	}
	
	public String getText() {
		return firstToken.getText();
	}

}
