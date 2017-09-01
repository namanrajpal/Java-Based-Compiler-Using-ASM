package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;
import cop5556sp17.AST.Type.TypeName;

public class IdentChain extends ChainElem {
	public Dec dec;
	public IdentChain(Token firstToken) {
		super(firstToken);
	}

	
	@Override
	public String toString() {
		return "IdentChain [firstToken=" + firstToken.getText() + "]";
	}


	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitIdentChain(this, arg);
	}
	
	public Dec getDec()
	{
		return dec;
		
	}
	
	public TypeName getType()
	{
		return val;
	}
}
