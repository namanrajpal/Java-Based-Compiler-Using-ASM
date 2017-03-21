package cop5556sp17;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556sp17.Parser.SyntaxException;
import cop5556sp17.Scanner.IllegalCharException;
import cop5556sp17.Scanner.IllegalNumberException;


public class ParserTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testFactor0() throws IllegalCharException, IllegalNumberException, SyntaxException {
		String input = "abc";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		parser.factor();
	}

	@Test
	public void testArg() throws IllegalCharException, IllegalNumberException, SyntaxException {
		String input = "  (3,5) ";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//System.out.println(scanner);
		Parser parser = new Parser(scanner);
        parser.arg();
	}

	@Test
	public void testArgerror() throws IllegalCharException, IllegalNumberException, SyntaxException {
		String input = "  (3,) ";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		thrown.expect(Parser.SyntaxException.class);
		parser.arg();
	}


	@Test
	public void testProgram0() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "prog0 {}";
		Parser parser = new Parser(new Scanner(input).scan());
		parser.parse();
	}
	
	@Test
	public void testWhile() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "while(123){sleep a100;}";
		Parser parser = new Parser(new Scanner(input).scan());
		parser.statement();
	}
	@Test
	public void testIf() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "if (8<50){image bhumikaGarg}";
		Parser parser = new Parser(new Scanner(input).scan());
		parser.statement();
	}
	@Test
	public void testArgblank() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "abc{abc -> blur;}";
		Parser parser = new Parser(new Scanner(input).scan());
		parser.program();
	}
	@Test
	public void testAssign() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "a7<-8;";
		Parser parser = new Parser(new Scanner(input).scan());
		parser.statement();
	}
	
	@Test
	public void testProg() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = " see{ if(true){sleep 5;}  }   ";
		Parser parser = new Parser(new Scanner(input).scan());
		parser.parse();
	}
	@Test
	public void testArgNone() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "";
		Parser parser = new Parser(new Scanner(input).scan());
		parser.arg();
	}
	@Test
	public void testE() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "100 / 5";
		Parser parser = new Parser(new Scanner(input).scan());
		parser.elem();
	}
	@Test
	public void testT() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "frame * wow + book - pencil";
		Parser parser = new Parser(new Scanner(input).scan());
		thrown.expect(Parser.SyntaxException.class);
		parser.term();
	}
	


}
