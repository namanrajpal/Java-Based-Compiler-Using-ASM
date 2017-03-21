package cop5556sp17.AST;

import cop5556sp17.Parser;
import cop5556sp17.Parser.SyntaxException;
import cop5556sp17.Scanner.Token;

public class Type  {
	

	public static TypeName getTypeName(Token token) throws SyntaxException{
		switch (token.kind){
		case KW_INTEGER: {return TypeName.INTEGER;} 
		case KW_BOOLEAN: {return TypeName.BOOLEAN;} 
		case KW_IMAGE: {return TypeName.IMAGE;} 
		case KW_FRAME: {return TypeName.FRAME;} 
		case KW_URL: {return TypeName.URL;} 
		case KW_FILE: {return TypeName.FILE;} 
		default: throw new Parser.SyntaxException("illegal type");
		}		
	}

	public static enum TypeName {
		INTEGER, 
		BOOLEAN, 
		IMAGE, 
		FRAME,
	    URL, 
	    FILE, 
	    NONE;
		
		public boolean isType(TypeName... types){
			for (TypeName type: types){
				if (type.equals(this)) return true;
			}
			return false;
		}
}
}
