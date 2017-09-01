package cop5556sp17;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import cop5556sp17.AST.ASTNode;
import cop5556sp17.AST.Program;

public class Compiler {

	static boolean devel = false;
	static boolean grade = false;

	public static void main(String[] args) throws Exception {
		String input;
		if (args.length == 0) {
			System.err.println("No filename given");
			return;
		}
		try {
			input = new String(Files.readAllBytes(Paths.get(args[0])));
		} catch (IOException e) {
			System.err.println("Problem reading file " + args[0]);
			return;
		}
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode program = parser.parse();
		TypeCheckVisitor v = new TypeCheckVisitor();
		program.visit(v, null);
		CodeGenVisitor cv = new CodeGenVisitor(devel, grade, null);
		byte[] bytecode = (byte[]) program.visit(cv, null);
		String name = ((Program) program).getName();
		String classFileName = "bin/" + name + ".class";
		OutputStream output = new FileOutputStream(classFileName);
		output.write(bytecode);
		output.close();
	}

}
