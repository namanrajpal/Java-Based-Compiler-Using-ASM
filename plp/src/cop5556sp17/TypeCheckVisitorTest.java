/**  Important to test the error cases in case the

 * AST is not being completely traversed.

 * 

 * Only need to test syntactically correct programs, or

 * program fragments.

 */



package cop5556sp17;



import static org.junit.Assert.*;



import org.junit.Before;

import org.junit.BeforeClass;

import org.junit.Rule;

import org.junit.Test;

import org.junit.rules.ExpectedException;



import cop5556sp17.AST.ASTNode;

import cop5556sp17.AST.Dec;

import cop5556sp17.AST.IdentExpression;

import cop5556sp17.AST.Program;

import cop5556sp17.AST.Statement;

import cop5556sp17.Parser.SyntaxException;

import cop5556sp17.Scanner.IllegalCharException;

import cop5556sp17.Scanner.IllegalNumberException;

import cop5556sp17.TypeCheckVisitor.TypeCheckException;



public class TypeCheckVisitorTest {

	



	@Rule

	public ExpectedException thrown = ExpectedException.none();



	@Test

	public void testAssignmentBoolLit0() throws Exception{

		String input = "p {\nboolean y \ny <- false;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}



	@Test

	public void testAssignmentIntLit0() throws Exception{

		String input = "p {\ninteger y \ny <- 5;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	

}

