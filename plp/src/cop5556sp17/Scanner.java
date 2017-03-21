package cop5556sp17;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Scanner {
	/**
	 * Kind enum
	 */

	public enum State {
		START, IN_DIGIT, IN_IDENT, AFTER_EQ, AFTER_OR, AFTER_GT, AFTER_LT, AFTER_NOT, AFTER_MINUS, IS_IT_A_COMMENT, IN_COMMENT

		
	}
	
	int lineNum =1;
	int posInLine =1;
	String identifier = "";

	final ArrayList<Token> tokens;
	final String chars;
	HashMap<String,Kind> keyMap = new HashMap<String,Kind>();
	int tokenNum;

	//testing
	ArrayList<Integer> linenum = new ArrayList<Integer>();

	public static enum Kind {
		IDENT(""), INT_LIT(""), KW_INTEGER("integer"), KW_BOOLEAN("boolean"), 
		KW_IMAGE("image"), KW_URL("url"), KW_FILE("file"), KW_FRAME("frame"), 
		KW_WHILE("while"), KW_IF("if"), KW_TRUE("true"), KW_FALSE("false"), 
		SEMI(";"), COMMA(","), LPAREN("("), RPAREN(")"), LBRACE("{"), 
		RBRACE("}"), ARROW("->"), BARARROW("|->"), OR("|"), AND("&"), 
		EQUAL("=="), NOTEQUAL("!="), LT("<"), GT(">"), LE("<="), GE(">="), 
		PLUS("+"), MINUS("-"), TIMES("*"), DIV("/"), MOD("%"), NOT("!"), 
		ASSIGN("<-"), OP_BLUR("blur"), OP_GRAY("gray"), OP_CONVOLVE("convolve"), 
		KW_SCREENHEIGHT("screenheight"), KW_SCREENWIDTH("screenwidth"), 
		OP_WIDTH("width"), OP_HEIGHT("height"), KW_XLOC("xloc"), KW_YLOC("yloc"), 
		KW_HIDE("hide"), KW_SHOW("show"), KW_MOVE("move"), OP_SLEEP("sleep"), 
		KW_SCALE("scale"), EOF("eof");

		Kind(String text) {
			this.text = text;
		}

		final String text;

		String getText() {
			return text;
		}
	}
	/**
	 * Thrown by Scanner when an illegal character is encountered
	 */
	@SuppressWarnings("serial")
	public static class IllegalCharException extends Exception {
		public IllegalCharException(String message) {
			super(message);
		}
	}

	/**
	 * Thrown by Scanner when an int literal is not a value that can be represented by an int.
	 */
	@SuppressWarnings("serial")
	public static class IllegalNumberException extends Exception {
		public IllegalNumberException(String message){
			super(message);
		}
	}


	/**
	 * Holds the line and position in the line of a token.
	 */
	static class LinePos {
		public final int line;
		public final int posInLine;

		public LinePos(int line, int posInLine) {
			super();
			this.line = line;
			this.posInLine = posInLine;
		}

		@Override
		public String toString() {
			return "LinePos [line=" + line + ", posInLine=" + posInLine + "]";
		}
	}




	public class Token {
		public final Kind kind;
		public final int pos;  //position in input array
		public final int length; 







		//returns the text of this Token
		public String getText() {

			if(this.kind==Kind.IDENT||this.kind==Kind.INT_LIT)
			{

				//TODO use pos and length to return this and not text
				return chars.substring(pos, pos+length);

			}
			else
				return kind.getText();
		}


		//returns a LinePos object representing the line and column of this Token
		LinePos getLinePos(){

			int linenumber = FloorSearch(linenum,0,linenum.size()-1,pos);
			//System.out.println("line number "+linenumber);
			int positionInLine = pos - linenum.get(linenumber);
			//positionInLine--;

			//System.out.println(linenumber);
			return new LinePos(linenumber,positionInLine);
		}

		Token(Kind kind, int pos, int length) {
			this.kind = kind;
			this.pos = pos;
			this.length = length;
			//this.linepos = new LinePos(lineNum,posInLine);

			//this.linepos = new LinePos(lineNum,posInLine);
		}
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((kind == null) ? 0 : kind.hashCode());
			result = prime * result + length;
			result = prime * result + pos;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Token)) {
				return false;
			}
			Token other = (Token) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (kind != other.kind) {
				return false;
			}
			if (length != other.length) {
				return false;
			}
			if (pos != other.pos) {
				return false;
			}
			return true;
		}
		
		private Scanner getOuterType() {
			return Scanner.this;
		}
		
		

		/** 
		 * Precondition:  kind = Kind.INT_LIT,  the text can be represented with a Java int.
		 * Note that the validity of the input should have been checked when the Token was created.
		 * So the exception should never be thrown.
		 * 
		 * @return  int value of this token, which should represent an INT_LIT
		 * @throws NumberFormatException
		 */
		public int intVal() throws NumberFormatException{
			
			return Integer.valueOf(chars.substring(pos,pos+length));
			
			
		}


		public boolean isKind(Kind kind2) {
			// TODO Auto-generated method stub
			return this.kind==kind2;
		}

	}




	Scanner(String chars) {
		this.chars = chars;
		tokens = new ArrayList<Token>();
		linenum.add(0);

		keyMap.put("integer",Kind.KW_INTEGER);
		keyMap.put("boolean",Kind.KW_BOOLEAN);
		keyMap.put("image",Kind.KW_IMAGE);
		keyMap.put("url",Kind.KW_URL);
		keyMap.put("file",Kind.KW_FILE);
		keyMap.put("frame",Kind.KW_FRAME);
		keyMap.put("while",Kind.KW_WHILE);
		keyMap.put("if",Kind.KW_IF);
		keyMap.put("true",Kind.KW_TRUE);
		keyMap.put("false",Kind.KW_FALSE);
		keyMap.put("blur",Kind.OP_BLUR);
		keyMap.put("gray",Kind.OP_GRAY);
		keyMap.put("convolve",Kind.OP_CONVOLVE);
		keyMap.put("screenheight",Kind.KW_SCREENHEIGHT);
		keyMap.put("screenwidth",Kind.KW_SCREENWIDTH);
		keyMap.put("width",Kind.OP_WIDTH);
		keyMap.put("height",Kind.OP_HEIGHT);
		keyMap.put("xloc",Kind.KW_XLOC);
		keyMap.put("yloc",Kind.KW_YLOC);
		keyMap.put("hide",Kind.KW_HIDE);
		keyMap.put("show",Kind.KW_SHOW);
		keyMap.put("move",Kind.KW_MOVE);
		keyMap.put("sleep",Kind.OP_SLEEP);
		keyMap.put("scale",Kind.KW_SCALE);
		keyMap.put("eof",Kind.EOF);


	}



	/**
	 * Initializes Scanner object by traversing chars and adding tokens to tokens list.
	 * 
	 * @return this scanner
	 * @throws IllegalCharException
	 * @throws IllegalNumberException
	 */


	//This is the main function to work upon
	public Scanner scan() throws IllegalCharException, IllegalNumberException {






		int pos = 0;

		int length = chars.length();
		State state = State.START;
		int startPos = 0;
		int ch;

		//main scanning loop
		while (pos <= length) {

			/*for(Token t : tokens)
	    	{
	    		System.out.print(t.getText() + " ");
	    	}
	    	System.out.println();*/


			//if pos is just less than length than grab a character and Check in Start

			ch = (pos < length ? chars.charAt(pos) : -1);
			//System.out.println((char)ch + " at "+ pos);
			switch (state) {

			case START: {
				//System.out.println((char)ch + " " + pos);
				//main checking goes on over here
				pos = skipWhiteSpace(pos);
				ch = pos < length ? chars.charAt(pos) : -1;
				startPos = pos;
				
				switch (ch) {
				case -1:  {
					//System.out.println((char)ch + " ..at "+ pos);
					pos++;posInLine++;
				}  break;

				//Single Length First
				case '+': {
					tokens.add(new Token(Kind.PLUS, startPos, 1));pos++;posInLine++;} break;

				case '*': {tokens.add(new Token(Kind.TIMES, startPos, 1));pos++;posInLine++;} break;

				case '/': {state = State.IS_IT_A_COMMENT;pos++;
				} break;

				case '%': {tokens.add(new Token(Kind.MOD, startPos, 1));pos++;posInLine++;} break;


				case ';': {tokens.add(new Token(Kind.SEMI, startPos, 1));pos++;posInLine++;} break;

				case ',': {tokens.add(new Token(Kind.COMMA, startPos, 1));pos++;posInLine++;} break;

				case '(': {tokens.add(new Token(Kind.LPAREN, startPos, 1));pos++;posInLine++;} break;

				case ')': {tokens.add(new Token(Kind.RPAREN, startPos, 1));pos++;posInLine++;} break;

				case '}': {tokens.add(new Token(Kind.RBRACE, startPos, 1));pos++;posInLine++;} break;

				case '{': {tokens.add(new Token(Kind.LBRACE, startPos, 1));pos++;posInLine++;} break;

				case '&': {tokens.add(new Token(Kind.AND, startPos, 1));pos++;posInLine++;} break;


				//check further for these
				case '|': {state = State.AFTER_OR;pos++;} break;

				case '<': {state = State.AFTER_LT;pos++;} break;

				case '>': {state = State.AFTER_GT;pos++;} break;

				case '!': {state = State.AFTER_NOT;pos++;} break;

				case '-': {state = State.AFTER_MINUS;pos++;} break;

				case '=': {state = State.AFTER_EQ;pos++;}break;

				case '0': {tokens.add(new Token(Kind.INT_LIT,startPos, 1));pos++;posInLine++;}break;

				default: {
					if (Character.isDigit(ch)) {state = State.IN_DIGIT;pos++;} 
					else if (Character.isJavaIdentifierStart(ch)) {
						//check this
						identifier = Character.toString((char)ch);
						state = State.IN_IDENT;pos++;
					} 
					else {throw new IllegalCharException(
							"#illegal char " +ch+" at pos "+pos);
					}
				}
				} // Start's switch ends here



			}  break; // START ENDS HERE


			case IS_IT_A_COMMENT: {
				//System.out.println((char)ch + " comment " + pos);
				
				int last =  (pos-1 < length ? chars.charAt(pos-1) : -1);
				switch(ch)
				{

				case '*' :{  

					if(last=='/')//comment started this is a case of /*
					{
						pos++;
						state = State.IN_COMMENT;
					}



				}break;



				default : {

					tokens.add(new Token(Kind.DIV, startPos, 1));
					state = State.START;

				}


				}






			}break;



			case IN_COMMENT : {
				pos=skipWhiteSpace(pos);
				
				ch = (pos < length ? chars.charAt(pos) : -1);
				boolean flag =true;
				
				while(flag)
				{
					//if(pos<length)System.out.println("this");
					
					ch = (pos < length ? chars.charAt(pos) : -1);
					if(ch=='*') // may be a case of comment getting closed
					{
						pos++;
						ch = (pos < length ? chars.charAt(pos) : -1);

						if(ch=='/') //comment closed
						{
							pos++;
							state = State.START;
							flag = false;
							break;
						}

					}else
					{
						if(ch==-1){state=State.START;break;}
						else pos++;
					}

				}

			}break;




			case IN_DIGIT: {

				if(Character.isDigit(ch))
				{
					pos++;
					//identifier +=(char)ch;

				}else
				{
					
					String number = chars.substring(startPos,pos);
					//number = "9999999";
					String max = Integer.toString(Integer.MAX_VALUE);
					//System.out.println(number);
					//System.out.println(max);
					if(new BigInteger(number).compareTo(new BigInteger(max))>=0)
					{
						throw new IllegalNumberException(number+"#Illegal Number : Out of Range for Integer");
					}

					//System.out.println(number.compareTo(max));
					
					//int num = Integer.valueOf(number);
					
					
					tokens.add(new Token(Kind.INT_LIT,startPos,pos-startPos));
					//pos++;
					posInLine+=(pos-startPos);
					state = State.START;
				}



			}  break;


			case IN_IDENT: {
				//TODO Hashmap for checking keywords

				if (Character.isJavaIdentifierPart(ch)) {
					pos++;

				} else {
					boolean isKeyword = true;
					isKeyword = IdentisKeyword(startPos, pos - startPos);

					if(isKeyword==false)tokens.add(new Token(Kind.IDENT, startPos, pos - startPos));
					else tokens.add(new Token(keyMap.get(chars.substring(startPos, pos)),startPos,pos-startPos));
					posInLine += (pos-startPos);
					state = State.START;
				}


			}  break;


			case AFTER_EQ: {


				if(ch=='=')
				{
					tokens.add(new Token(Kind.EQUAL,startPos,2));
					posInLine++;posInLine++;
					pos++;
					state = State.START;
				}else
				{
					throw new IllegalCharException(
							"#illegal char " +ch+" at pos "+pos);
				}


			}  break;

			case AFTER_OR: {


				//barrarow
				if(ch=='-')
				{
					//checking next char
					pos++;	            		
					ch = pos < length ? chars.charAt(pos) : -1;
					if(ch=='>')
					{
						tokens.add(new Token(Kind.BARARROW,startPos,3));
						posInLine++;posInLine++;posInLine++;
						pos++;
						state = State.START;
					}else
					{
						//check for the case of 
						tokens.add(new Token(Kind.OR,startPos,1));
						tokens.add(new Token(Kind.MINUS,startPos+1,1));
						state = State.START;
					}

				}else
				{
					tokens.add(new Token(Kind.OR,startPos,1));
					posInLine++;
					//pos++;
					state = State.START;	
				}

			}break;

			case AFTER_LT: {

				switch(ch){
				// case for <=
				case '=' : {
					tokens.add(new Token(Kind.LE,startPos,2));
					posInLine++;posInLine++;
					pos++;
					state = State.START;

				}break;

				//case for <- assign
				case '-' : {

					tokens.add(new Token(Kind.ASSIGN,startPos,2));
					posInLine++;posInLine++;
					pos++;
					state = State.START;

				}break;

				//default case for <
				default : 
				{
					tokens.add(new Token(Kind.LT,startPos,1));
					posInLine++;
					//pos++;
					state = State.START;  		
				}

				}//inner switch ends here

			}break;

			case AFTER_NOT: {


				switch(ch){

				// case for !=
				case '=' : {
					tokens.add(new Token(Kind.NOTEQUAL,startPos,2));
					posInLine++;posInLine++;
					pos++;
					state = State.START;

				}break;

				//default case for !
				default : 
				{
					tokens.add(new Token(Kind.NOT,startPos,1));
					posInLine++;
					//pos++;
					state = State.START;  		
				}

				}//inner switch ends here

			}break;


			case AFTER_GT: {
				/*	            	IDENT(""), INT_LIT(""), KW_INTEGER("integer"), KW_BOOLEAN("boolean"), 
	            		        		KW_IMAGE("image"), KW_URL("url"), KW_FILE("file"), KW_FRAME("frame"), 
	            		        		KW_WHILE("while"), KW_IF("if"), KW_TRUE("true"), KW_FALSE("false"), 
	            		        		SEMI(";"), COMMA(","), LPAREN("("), RPAREN(")"), LBRACE("{"), 
	            		        		RBRACE("}"), ARROW("->"), BARARROW("|->"), OR("|"), AND("&"), 
	            		        		EQUAL("=="), NOTEQUAL("!="), LT("<"), GT(">"), LE("<="), GE(">="), 
	            		        		PLUS("+"), MINUS("-"), TIMES("*"), DIV("/"), MOD("%"), NOT("!"), 
	            		        		ASSIGN("<-"), OP_BLUR("blur"), OP_GRAY("gray"), OP_CONVOLVE("convolve"), 
	            		        		KW_SCREENHEIGHT("screenheight"), KW_SCREENWIDTH("screenwidth"), 
	            		        		OP_WIDTH("width"), OP_HEIGHT("height"), KW_XLOC("xloc"), KW_YLOC("yloc"), 
	            		        		KW_HIDE("hide"), KW_SHOW("show"), KW_MOVE("move"), OP_SLEEP("sleep"), 
	            		        		KW_SCALE("scale"), EOF("eof");*/

				switch(ch){

				// case for >=
				case '=' : {
					tokens.add(new Token(Kind.GE,startPos,2));
					posInLine++;posInLine++;
					pos++;
					state = State.START;

				}break;

				//default case for >
				default : 
				{
					tokens.add(new Token(Kind.GT,startPos,1));
					posInLine++;
					//pos++;
					state = State.START;  		
				}

				}//inner switch ends here

			}break;


			case AFTER_MINUS: {
				/*	            	IDENT(""), INT_LIT(""), KW_INTEGER("integer"), KW_BOOLEAN("boolean"), 
	            		        		KW_IMAGE("image"), KW_URL("url"), KW_FILE("file"), KW_FRAME("frame"), 
	            		        		KW_WHILE("while"), KW_IF("if"), KW_TRUE("true"), KW_FALSE("false"), 
	            		        		SEMI(";"), COMMA(","), LPAREN("("), RPAREN(")"), LBRACE("{"), 
	            		        		RBRACE("}"), ARROW("->"), BARARROW("|->"), OR("|"), AND("&"), 
	            		        		EQUAL("=="), NOTEQUAL("!="), LT("<"), GT(">"), LE("<="), GE(">="), 
	            		        		PLUS("+"), MINUS("-"), TIMES("*"), DIV("/"), MOD("%"), NOT("!"), 
	            		        		ASSIGN("<-"), OP_BLUR("blur"), OP_GRAY("gray"), OP_CONVOLVE("convolve"), 
	            		        		KW_SCREENHEIGHT("screenheight"), KW_SCREENWIDTH("screenwidth"), 
	            		        		OP_WIDTH("width"), OP_HEIGHT("height"), KW_XLOC("xloc"), KW_YLOC("yloc"), 
	            		        		KW_HIDE("hide"), KW_SHOW("show"), KW_MOVE("move"), OP_SLEEP("sleep"), 
	            		        		KW_SCALE("scale"), EOF("eof");*/

				switch(ch){

				// case for ->
				case '>' : {
					tokens.add(new Token(Kind.ARROW,startPos,2));
					posInLine++;
					posInLine++;
					pos++;
					state = State.START;

				}break;


				//default case for >
				default : 
				{
					tokens.add(new Token(Kind.MINUS,startPos,1));
					posInLine++;
					//pos++;
					state = State.START;  		
				}

				}//inner switch ends here

			}break;




			default:  assert false;
			}// Main Switch ends here
		} // main while loop ends here







		tokens.add(new Token(Kind.EOF,startPos,0));
		return this;  
	}





	private boolean IdentisKeyword(int startPos, int length) {
		// TODO Auto-generated method stub



		return keyMap.containsKey(chars.substring(startPos, startPos+length));
	}





	public int FloorSearch(ArrayList<Integer> arr, int low, int high, int x)
	{
		//for(int i : arr)
			//System.out.print(i);
		
		int mid; 
		
		//System.out.println(arr.get(high));
		//System.out.println(arr.get(low));
		if(x >= arr.get(high))
		{
			return high; 
		}
		if(x < arr.get(low))
		{  
			//System.out.println("this");
			return -1; 
		}
		/* If x is greater than the last element, then return -1 */
		

		/* get the index of middle element of arr[low..high]*/
		mid =  low + (high - low)/2 ;
		//System.out.println("mid" + mid);
		//System.out.println("mid" + arr.get(mid));
		
		/* If x is same as middle element, then return mid */
		if(arr.get(mid) == x)
			return mid;

		/* If x is smaller than arr[mid], then either arr[mid] 
		is ceiling of x or ceiling lies in arr[mid-1...high] */
		/* If x is greater than arr[mid], then either arr[mid + 1]
		is ceiling of x or ceiling lies in arr[mid+1...high] */
		else if(arr.get(mid) > x)
		{
			
			if(mid - 1 >= low && x >= arr.get(mid-1))
				return mid-1;
			else
				return FloorSearch(arr, low, mid-1, x);
		}

		
		else
		{
			//System.out.println("mid "+arr.get(mid));
			if(mid + 1 <= high && x < arr.get(mid+1))
				return mid;
			else	
				return FloorSearch(arr, mid+1, high, x);
		}
	}



	private int skipWhiteSpace(int pos) {
		// returns position skipped after variable
		int newpos = 0;
		boolean flag = true;
		int ch;
		int length = chars.length();

		while(flag)
		{
			ch = pos < length ? chars.charAt(pos) : -1;
			if( Character.isWhitespace(ch))
			{
				posInLine++;
				pos++;
				//if its a new line
				//set pos in line to 1
				//and increase the line number

				if(ch=='\n')
				{
					lineNum++;
					linenum.add(pos);
					posInLine = 1;
				}

			}
			else
			{
				flag = false;
				//pos++;
			}


		}

		newpos = pos;	
		return newpos;
	}



	/*
	 * Return the next token in the token list and update the state so that
	 * the next call will return the Token..  
	 */
	public Token nextToken() {
		if (tokenNum >= tokens.size())
			return null;
		return tokens.get(tokenNum++);
	}

	/*
	 * Return the next token in the token list without updating the state.
	 * (So the following call to next will return the same token.)
	 */
	public Token peek(){
		if (tokenNum >= tokens.size())
			return null;
		return tokens.get(tokenNum);		
	}



	/**
	 * Returns a LinePos object containing the line and position in line of the 
	 * given token.  
	 * 
	 * Line numbers start counting at 0
	 * 
	 * @param t
	 * @return
	 */
	public LinePos getLinePos(Token t) {

		return t.getLinePos();
	}


}
