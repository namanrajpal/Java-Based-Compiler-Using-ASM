package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;
import cop5556sp17.AST.Type.TypeName;


public abstract class Chain extends Statement {
	
	public TypeName val;
	
	public Chain(Token firstToken) {
		super(firstToken);
	}
	
	public TypeName getType()
	{
		return val;
	}

}
