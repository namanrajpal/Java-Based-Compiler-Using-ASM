package cop5556sp17;

import static cop5556sp17.Scanner.Kind.PLUS;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556sp17.Parser.SyntaxException;
import cop5556sp17.Scanner.IllegalCharException;
import cop5556sp17.Scanner.IllegalNumberException;
import cop5556sp17.Scanner.Kind;
import cop5556sp17.AST.ASTNode;
import cop5556sp17.AST.AssignmentStatement;
import cop5556sp17.AST.BinaryExpression;
import cop5556sp17.AST.Block;
import cop5556sp17.AST.BooleanLitExpression;
import cop5556sp17.AST.ConstantExpression;
import cop5556sp17.AST.Dec;
import cop5556sp17.AST.FilterOpChain;
import cop5556sp17.AST.IdentChain;
import cop5556sp17.AST.IdentExpression;
import cop5556sp17.AST.IdentLValue;
import cop5556sp17.AST.IfStatement;
import cop5556sp17.AST.IntLitExpression;
import cop5556sp17.AST.ParamDec;
import cop5556sp17.AST.Program;
import cop5556sp17.AST.Statement;
import cop5556sp17.AST.Tuple;

public class ASTTest {

	static final boolean doPrint = true;
	static void show(Object s){
		if(doPrint){System.out.println(s);}
	}
	

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testProgram4() throws IllegalCharException, IllegalNumberException, SyntaxException {
		String input = "mnv {grayish <-  998|(abcgg*77%true+9<=false<98>=abc>nncd8==(a!=b));}";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode ast = parser.program();
		assertEquals(Program.class,ast.getClass());
		Program be = (Program) ast;
		assertEquals(Kind.IDENT,be.getFirstToken().kind);
		assertEquals(java.util.ArrayList.class, be.getParams().getClass());
		assertEquals(Block.class, be.getB().getClass());
		Block b = be.getB();
		assertEquals("{",b.getFirstToken().getText());
		AssignmentStatement s = (AssignmentStatement) b.getStatements().get(0);
		assertEquals(Kind.IDENT,be.getFirstToken().kind);
		assertEquals(IdentLValue.class, s.getVar().getClass());
		assertEquals("grayish", s.getVar().getText());
		assertEquals(BinaryExpression.class, s.getE().getClass());
		
	}
	
	
	@Test
	public void testChainElem() throws IllegalCharException, IllegalNumberException, SyntaxException {
		String input = "abc blur";//blur will be ignored
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode ast = parser.chainElem(); //stops as abc
		assertEquals(IdentChain.class, ast.getClass());
	}

	
	
	@Test
	public void testProgram1() throws IllegalCharException, IllegalNumberException, SyntaxException {
		String input = "value file value1 integer value2 {}";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		thrown.expect(Parser.SyntaxException.class);
		ASTNode ast = parser.program();			
	}
	
	@Test
	public void testProgram2() throws IllegalCharException, IllegalNumberException, SyntaxException {
		String input = "value file value1, integer value2 {}";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode ast = parser.program();
		assertEquals(Program.class, ast.getClass());
	}
	
	@Test
	public void testProgram3() throws IllegalCharException, IllegalNumberException, SyntaxException {
		String input2 = "value file value1, image value2 {}";
		Parser parser2 = new Parser(new Scanner(input2).scan());
		thrown.expect(Parser.SyntaxException.class);
		parser2.parse();
	}
	
	
}

	
