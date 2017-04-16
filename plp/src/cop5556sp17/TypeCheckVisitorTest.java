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
	public void testComplicatedProgram() throws Exception{

		String input = "prog1  file file1, integer itx, "
				+ "boolean b1{ integer ii1 boolean bi1 \n "
				+ "image IMAGE1 frame fram1 sleep itx+ii1; "
				+ "while (b1){if(bi1)\n{sleep ii1+itx*2;}}"
				+ "\nfile1->blur |->gray;fram1 ->yloc;\n "
				+ "IMAGE1->blur->scale (ii1+1)|-> gray;\nii1 <- 12345+54321;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}
	
	
	@Test
	public void testImagOp() throws Exception{

		String input = "prog  boolean y , file x {\n integer z \n scale(100) -> width; blur -> y; convolve -> blur -> gray |-> gray -> width;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

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

