package cop5556sp17;

import cop5556sp17.Scanner.Kind;
import static cop5556sp17.Scanner.Kind.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cop5556sp17.Scanner.Token;
import cop5556sp17.AST.*;

public class Parser {

	/**
	 * Exception to be thrown if a syntax error is detected in the input.
	 * You will want to provide a useful error message.
	 *
	 */
	static Set<Kind> paramDec;
	static Set<Kind> dec;
	static Set<Kind> filterOp;
	static Set<Kind> frameOp;
	static Set<Kind> imageOp;
	static Set<Kind> relOp;
	static Set<Kind> weakOp;
	static Set<Kind> strongOp;
	
	
	
	@SuppressWarnings("serial")
	public static class SyntaxException extends Exception {
		public SyntaxException(String message) {
			super(message);
		}
	}
	
	/**
	 * Useful during development to ensure unimplemented routines are
	 * not accidentally called during development.  Delete it when 
	 * the Parser is finished.
	 *
	 */
	@SuppressWarnings("serial")	
	public static class UnimplementedFeatureException extends RuntimeException {
		public UnimplementedFeatureException() {
			super();
		}
	}

	Scanner scanner;
	Token t;

	Parser(Scanner scanner) {
		this.scanner = scanner;
		t = scanner.nextToken();
		paramDec = new HashSet<Kind>();
		dec = new HashSet<Kind>();
		filterOp = new HashSet<Kind>();
		frameOp = new HashSet<Kind>();
		imageOp = new HashSet<Kind>();
		relOp = new HashSet<Kind>();
		weakOp = new HashSet<Kind>();
		strongOp = new HashSet<Kind>();
		
		
		paramDec.add(Kind.KW_URL);
		paramDec.add(Kind.KW_FILE);
		paramDec.add(Kind.KW_INTEGER);
		paramDec.add(Kind.KW_BOOLEAN);
		
		dec.add(Kind.KW_FRAME);
		dec.add(Kind.KW_IMAGE);
		dec.add(Kind.KW_INTEGER);
		dec.add(Kind.KW_BOOLEAN);
		
		filterOp.add(Kind.OP_BLUR);
		filterOp.add(Kind.OP_GRAY);
		filterOp.add(Kind.OP_CONVOLVE);
		
		frameOp.add(Kind.KW_SHOW);
		frameOp.add(Kind.KW_HIDE);
		frameOp.add(Kind.KW_MOVE);
		frameOp.add(Kind.KW_XLOC);
		frameOp.add(Kind.KW_YLOC);

		imageOp.add(Kind.OP_HEIGHT);
		imageOp.add(Kind.OP_WIDTH);
		imageOp.add(Kind.KW_SCALE);

		relOp.add(Kind.LE);
		relOp.add(Kind.LT);
		relOp.add(Kind.GT);
		relOp.add(Kind.GE);
		relOp.add(Kind.EQUAL);
		relOp.add(Kind.NOTEQUAL);

		weakOp.add(Kind.PLUS);
		weakOp.add(Kind.MINUS);
		weakOp.add(Kind.OR);

		strongOp.add(Kind.TIMES);
		strongOp.add(Kind.DIV);
		strongOp.add(Kind.AND);
		strongOp.add(Kind.MOD);
					
	}

	/**
	 * parse the input using tokens from the scanner.
	 * Check for EOF (i.e. no trailing junk) when finished
	 * 
	 * @throws SyntaxException
	 */
	Program parse() throws SyntaxException {
		Program p = program();
		matchEOF();
		return p;
	}

	Expression expression() throws SyntaxException {
		Expression op1 = null;
		Expression op2 =null;
		Token first = t;
		op1 = term();
		while(relOp.contains(t.kind))
		{
			Token op = t;
			match(relOp);
			op2 =term();
			//term();
			op1 = new BinaryExpression(first,op1,op,op2);
		}
		
		return op1;
	}

	Expression term() throws SyntaxException {
		Expression op1 =null;
		Expression op2 =null;
		Token first =t;
		op1 = elem();
		
		while(weakOp.contains(t.kind))
		{
			Token op = t;
			match(weakOp);
			
			op2 = elem();
			op1 = new BinaryExpression(first,op1,op,op2);
		}
		return op1;
	}

	Expression elem() throws SyntaxException {
		Expression op1 =null;
		Expression op2 =null;
		Token first =t;
		
		op1 = factor();
		while(strongOp.contains(t.kind))
		{
			Token op = t;
			match(strongOp);
			op2 = factor();
			op1 = new BinaryExpression(first,op1,op,op2);
		}
		return op1;
	}

	Expression factor() throws SyntaxException {
		Expression e = null;
		
		Token first = t;
		Kind kind = t.kind;
		switch (kind) {
		case IDENT: {
			
			consume();
			e = new IdentExpression(first);
		}
			break;
		case INT_LIT: {
			consume();
			e = new IntLitExpression(first);
		}
			break;
		case KW_TRUE:
		case KW_FALSE: {
			consume();
			e = new BooleanLitExpression(first);
		}
			break;
		case KW_SCREENWIDTH:
		case KW_SCREENHEIGHT: {
			consume();
			e = new ConstantExpression(first);
		}
			break;
		case LPAREN: {
			consume();
			e = expression();
			match(RPAREN);
		}
			break;
		default:
			//you will want to provide a more useful error message
			throw new SyntaxException("illegal factor " + t.kind + " at " + t.pos + " : "+ t.getLinePos());
		}
		return e;
	}

	Block block() throws SyntaxException {
		//TODO
		Token first = t;
		match(Kind.LBRACE);
		
		Block b = null;
		ArrayList<Dec> decList = new ArrayList<Dec>();
		ArrayList<Statement> statementList = new ArrayList<Statement>();
		
		//TODO check for other starters for statement
		while(dec.contains(t.kind)|| //dec first
				t.isKind(Kind.OP_SLEEP)|| //statement first
				t.isKind(Kind.KW_WHILE)|| //statement while first
				t.isKind(Kind.KW_IF)||	//statement if first	
				t.isKind(Kind.IDENT)|| //statement chain chainElem first
				filterOp.contains(t.kind)|| //statement chain chainElem filter first
				imageOp.contains(t.kind)||//statement chain chainElem image first
				frameOp.contains(t.kind)) //statement chain chainElem filter first
		{
			
			
			if(dec.contains(t.kind))
			{
				
				decList.add(dec());
			}else
			{
				
				statementList.add(statement());
			}
			
			
		}
		
		
		match(Kind.RBRACE);
		
		b = new Block(first,decList,statementList);
		return b;
	}

	//start symbol
  Program program() throws SyntaxException {
	
	 
	 Program pGram = null;	
	 
	 ArrayList<ParamDec> pDecList = new ArrayList<ParamDec>();
	 //ParamDec pDec = null;
	 
	 Token first = t;
	 match(Kind.IDENT);
	 
	 if(paramDec.contains(t.kind)){
		 pDecList.add(paramDec());
		 while(t.isKind(Kind.COMMA))
		 {
			 consume();
			 pDecList.add(paramDec());
		 }
	 }
	 
	 Block b = block();
	 
	 pGram = new Program(first,pDecList,b);
	 return pGram;
	}

	ParamDec paramDec() throws SyntaxException {
		
		ParamDec pDec = null;

		Token type = t;
		match(paramDec);
		
		Token ident = t;
		match(Kind.IDENT);
		pDec = new ParamDec(type,ident);
		return pDec;
	}

	Dec dec() throws SyntaxException {
		
		Dec d = null;
		Token decfirst =t;
		match(dec);
		
		Token ident =t;
		match(Kind.IDENT);
		
		d = new Dec(decfirst,ident);
		return d;
	}

	Statement statement() throws SyntaxException {
		
		Statement s = null;
		//TODO
		switch(t.kind){
		
		//statement first
		case OP_SLEEP:
			Token sleepFirst =t;
			match(OP_SLEEP);
			
			s = new SleepStatement(sleepFirst,expression());
			
			match(SEMI);
			break;
		
		//statement while first	
		case KW_WHILE:
			s= whileStatement();
			break;
		//statement if first	
		case KW_IF:
			s= ifStatement();
			break;
		//statement  assign first or statement chain's first
		case IDENT:
			
			Token next = scanner.peek();
			if(next.isKind(ASSIGN))
			{
				s= assign();
				match(SEMI);
			}else
			{
				s= chain();
				match(SEMI);
			}
			
			break;
		default:
			
			if(filterOp.contains(t.kind) ||imageOp.contains(t.kind) ||frameOp.contains(t.kind))
			{
				s= chain();
				match(SEMI);
			}else
			{
			throw new SyntaxException("illegal statement " + t.kind + " at " + t.pos + " : "+ t.getLinePos());
			}
		//Consuming OP
		}
		//System.out.println(s.firstToken.getText());
		return s;
	}

	WhileStatement whileStatement() throws SyntaxException {
		
		
		Token first = t;
		match(KW_WHILE);
		match(LPAREN);
		
		Expression e = expression();
		
		match(RPAREN);
		Block b = block();
		
		return new WhileStatement(first,e,b);
		
	}
	
	IfStatement ifStatement() throws SyntaxException {
		
		Token first = t;
		match(KW_IF);
		match(LPAREN);
		
		Expression e = expression();
		
		match(RPAREN);
		Block b = block();
		
		return new IfStatement(first,e,b);
		
	}
	
	//doubts
	AssignmentStatement assign() throws SyntaxException {
		
		Token first =t;
		match(IDENT);
		IdentLValue i = new IdentLValue(first);
		match(ASSIGN);
		Expression e =expression();
		
		return new AssignmentStatement(first,i,e);
	}

	Chain chain() throws SyntaxException {
		
		Chain c = null;
		
		Token first = t;
		c = chainElem();
		Token arrow =t;
		arrowOp();
		
		ChainElem cc;
		cc = chainElem();
		c = new BinaryChain(first,c,arrow,cc);
		while(t.isKind(ARROW)||t.isKind(BARARROW))
		{
			Token arr = t;
			arrowOp(); //consuming Arrow or Bararrow
			c = new BinaryChain(first,c,arr,chainElem());
		}
		
		return c;
	}

	void arrowOp() throws SyntaxException {
		
		if(t.isKind(ARROW))
		{
			match(ARROW);
		}else
		{
			match(BARARROW);
		}
		
	}
	
	ChainElem chainElem() throws SyntaxException {
		
		ChainElem c = null;
		if(t.isKind(IDENT))
		{	
			Token first =t;
			c = new IdentChain(first);
			match(IDENT);
		}else
		{
			
			if(filterOp.contains(t.kind))
			{
				Token filter = t;
				match(filterOp);
				Tuple tup = arg();
				c = new FilterOpChain(filter,tup);
			}else
			if(imageOp.contains(t.kind))
			{
				Token image = t;
				match(imageOp);
				Tuple tup = arg();
				c = new ImageOpChain(image,tup);
			}else
			if(frameOp.contains(t.kind))
			{
				Token frame = t;
				match(frameOp);
				
				Tuple tup = arg();
				c = new FrameOpChain(frame,tup);
			}else throw new SyntaxException("illegal chain " + t.kind + " at " + t.pos + " : "+ t.getLinePos());
			
		}
		
		return c;
	}

	Tuple arg() throws SyntaxException {
		
		Tuple tup = null;
		ArrayList<Expression> expressions = new ArrayList<Expression>();
		Token first = t;
		if(t.isKind(LPAREN))
		{
			
			match(LPAREN);
			
			expressions.add(expression());
			while(t.isKind(COMMA))
			{
				match(COMMA);
				expressions.add(expression());
				
			}
			match(RPAREN);
			
		}
		tup = new Tuple(first,expressions);
		return tup;
		
	}

	/**
	 * Checks whether the current token is the EOF token. If not, a
	 * SyntaxException is thrown.
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	private Token matchEOF() throws SyntaxException {
		if (t.isKind(EOF)) {
			return t;
		}
		throw new SyntaxException("expected EOF");
	}

	/**
	 * Checks if the current token has the given kind. If so, the current token
	 * is consumed and returned. If not, a SyntaxException is thrown.
	 * 
	 * Precondition: kind != EOF
	 * 
	 * @param kind
	 * @return
	 * @throws SyntaxException
	 */
	private Token match(Kind kind) throws SyntaxException {
		if (t.isKind(kind)) {
			return consume();
		}
		throw new SyntaxException("saw " + t.kind + "expected " + kind+ " at " + t.getLinePos());
	}

	/**
	 * Checks if the current token has one of the given kinds. If so, the
	 * current token is consumed and returned. If not, a SyntaxException is
	 * thrown.
	 * 
	 * * Precondition: for all given kinds, kind != EOF
	 * 
	 * @param kinds
	 *            list of kinds, matches any one
	 * @return
	 * @throws SyntaxException
	 */
	private Token match(Set<Kind> kinds) throws SyntaxException {
		
		for(Kind k : kinds)
		{
			if(t.isKind(k))
				return consume();
		}
		throw new SyntaxException("Saw: " + t.kind + " Expected one of these: " + kinds.toString() + " at " + t.getLinePos());
	}

	/**
	 * Gets the next token and returns the consumed token.
	 * 
	 * Precondition: t.kind != EOF
	 * 
	 * @return
	 * 
	 */
	private Token consume() throws SyntaxException {
		Token tmp = t;
		t = scanner.nextToken();
		return tmp;
	}

}
