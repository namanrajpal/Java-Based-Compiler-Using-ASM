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

	public void testImageOpNew() throws Exception{

		String input = "prog  boolean y , file x {\n integer z \n scale(100) -> width; blur -> y; convolve -> blur -> gray |-> gray -> width;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();
		
		//thrown.expect(TypeCheckVisitor.TypeCheckException.class);
	
		program.visit(v, null);		

	}
	
	
	
	@Test

	public void testAssignmentBoolLit0() throws Exception{

		String input = "p integer temporary, boolean valid, integer number {boolean temporary temporary <- true; if(temporary) {valid <- false;number <- 6;}}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}



	@Test

	public void testAssignmentIntLit0() throws Exception{

		String input = "prog {\n boolean x \n scale(1234) -> width; \n  integer y \n scale(y) -> scale(y + 10);}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest01() throws Exception{

		String input = "p {\ninteger y \ny <- 6 + 7;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest02() throws Exception{

		String input = "p {\ninteger y \ny <- 6 - 7;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest03() throws Exception{

		String input = "p {\ninteger y \ny <- 6 * 7;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest04() throws Exception{

		String input = "p {\ninteger y \ny <- 6 / 7;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest05() throws Exception{

		String input = "p {\nboolean y \ny <- 6 > 7;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest06() throws Exception{

		String input = "p {\nboolean y \ny <- 6 >= 7;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest07() throws Exception{

		String input = "p {\nboolean y \ny <- 6 < 7;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest08() throws Exception{

		String input = "p {\nboolean y \ny <- 6 <= 7;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest09() throws Exception{

		String input = "p {\nboolean y \ny <- 6 == 7;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest10() throws Exception{

		String input = "p {\nboolean y \ny <- 6 != 7;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest11() throws Exception{

		String input = "p {\nimage y integer x \ny <- x * y;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest12() throws Exception{

		String input = "p {\nimage y integer x \ny <- y * x;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest13() throws Exception{

		String input = "p {\nimage y image x \ny <- y + x;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest14() throws Exception{

		String input = "p {\nimage y image x \ny <- y - x;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest15() throws Exception{

		String input = "p {\nboolean y boolean x \ny <- y == x;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest16() throws Exception{

		String input = "p {\nboolean y boolean x \ny <- y != x;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest17() throws Exception{

		String input = "p {\nboolean y boolean x \ny <- y > x;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest18() throws Exception{

		String input = "p {boolean a boolean b integer c a<-true; b<-true; if(a&b){ c<-10;}}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest19() throws Exception{

		String input = "p {\nboolean y boolean x \ny <- y < x;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionTest20() throws Exception{

		String input = "p {\nboolean y boolean x \ny <- y <= x;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionError01() throws Exception{

		String input = "p {\ninteger x image y y <- x +y \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionError02() throws Exception{

		String input = "p {\ninteger x image y y <- x -y \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionError03() throws Exception{

		String input = "p {\ninteger x image y y <- x / y \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionError04() throws Exception{

		String input = "p {\ninteger x image y y <- y / x \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		//thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionError05() throws Exception{

		String input = "p {\ninteger x image y y <- y >= x \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionError06() throws Exception{

		String input = "p {\ninteger x image y boolean z z <- y == x \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionError07() throws Exception{

		String input = "p {\nimage x image y boolean z z <- y <= x \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionError08() throws Exception{

		String input = "p {\nimage x integer y boolean z z <- y != x \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionError09() throws Exception{

		String input = "p {\nimage x integer y boolean z z <- y == x \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryExpressionError10() throws Exception{

		String input = "p {\nboolean x integer y boolean z z <- y == x \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testBinaryChain01() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img -> ident_frame ->xloc \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain02() throws Exception{

		String input = "p file ident_file {frame ident_frame image ident_img  ident_file -> ident_img -> ident_frame ->xloc \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain03() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img -> ident_frame ->yloc \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain04() throws Exception{

		String input = "p file ident_file , url ident_url {frame ident_frame image ident_img ident_url -> ident_img -> ident_file \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain05() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img -> ident_frame ->show\n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain06() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img -> ident_frame ->hide \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain07() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img -> ident_frame ->move(2,4) \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain08() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img ->width \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain09() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img ->height \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain10() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img ->scale(2) \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain11() throws Exception{

		String input = "p {\nimage ident_img ident_img ->gray \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain12() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img ->blur \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain13() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img ->convolve \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain14() throws Exception{

		String input = "p {\nimage ident_img integer ident frame ident_frame ident_img ->ident -> ident_frame\n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChain15() throws Exception{

		String input = "prog boolean y , file x {\n integer z \n scale(100) -> width; blur -> y; convolve -> blur -> gray |-> gray -> width;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError01() throws Exception{

		String input = "p file ident_file {frame ident_frame image ident_img  ident_img -> ident_file -> ident_frame ->xloc \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError02() throws Exception{

		String input = "p url ident_url {frame ident_frame image ident_img  ident_frame -> ident_url ->xloc \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError03() throws Exception{

		String input = "p url ident_url {frame ident_frame image ident_img  ident_url ->xloc \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError04() throws Exception{

		String input = "p url ident_url {frame ident_frame image ident_img  ident_url ->yloc \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError05() throws Exception{

		String input = "p url ident_url {frame ident_frame image ident_img ident_url ->show \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError06() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img ->width(2) \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError07() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img -> ident_frame ->move(2) \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError08() throws Exception{

		String input = "p {\nimage ident_img ident_img ->gray(2) \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError09() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img ->blur(9,8) \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError10() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img ->convolve(5,6,3) \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError11() throws Exception{

		String input = "p file f1 {\n image i integer h f1->i->height->h;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		//thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError12() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img ->scale(true) \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError13() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img -> ident_frame ->move(1,false) \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError14() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img -> ident_frame ->move(1,false) \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testBinaryChainTypeError15() throws Exception{

		String input = "p {\nimage ident_img frame ident_frame ident_img -> ident_frame ->move(false,7) \n;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);	

	}

	

	@Test

	public void testAssignmentBoolLitError0() throws Exception{

		String input = "p {\nboolean y \ny <- 3;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}	

	

	@Test

	public void testAssignmentImageError0() throws Exception{

		String input = "p {\nimage y \ny <- 3;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);

	}

	

	@Test

	public void testAssignmentIntLitError0() throws Exception{

		String input = "p {\ninteger y \ny <- false;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}	

	

	@Test

	public void testIfStatementConditionError01() throws Exception{

		String input = "p {\ninteger y if(3){\ny <- 6;}}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testIfStatementConditionError02() throws Exception{

		String input = "p {\ninteger y if(y){\ny <- 8;}}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testWhileStatementConditionError01() throws Exception{

		String input = "p {\ninteger y while(3){\ny <- 100;}}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testWhileStatementConditionError02() throws Exception{

		String input = "p {\ninteger y while(y){\ny <- 100;}}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testAssignmentUndeclaredError() throws Exception{

		String input = "p {y <- false;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testOutsideScopeError01() throws Exception{

		String input = "p {\ninteger y if(true){\ninteger x x <- 100;} x <-50;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testOutsideScopeError02() throws Exception{

		String input = "p {\ninteger y if(true){\ninteger x integer y x <- 100; y <- 10;} x <-50;}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}

	

	@Test

	public void testRedeclaredError() throws Exception{

		String input = "p {integer y boolean y}";

		Scanner scanner = new Scanner(input);

		scanner.scan();

		Parser parser = new Parser(scanner);

		ASTNode program = parser.parse();

		TypeCheckVisitor v = new TypeCheckVisitor();

		thrown.expect(TypeCheckVisitor.TypeCheckException.class);

		program.visit(v, null);		

	}
	
	
	
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

	
	

	

}

