package cop5556sp17;

import static cop5556sp17.AST.Type.TypeName.IMAGE;
import static cop5556sp17.Scanner.Kind.AND;
import static cop5556sp17.Scanner.Kind.BARARROW;
import static cop5556sp17.Scanner.Kind.DIV;
import static cop5556sp17.Scanner.Kind.EQUAL;
import static cop5556sp17.Scanner.Kind.GE;
import static cop5556sp17.Scanner.Kind.GT;
import static cop5556sp17.Scanner.Kind.LE;
import static cop5556sp17.Scanner.Kind.LT;
import static cop5556sp17.Scanner.Kind.MINUS;
import static cop5556sp17.Scanner.Kind.MOD;
import static cop5556sp17.Scanner.Kind.NOTEQUAL;
import static cop5556sp17.Scanner.Kind.OR;
import static cop5556sp17.Scanner.Kind.PLUS;
import static cop5556sp17.Scanner.Kind.TIMES;

import java.util.ArrayList;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import cop5556sp17.Scanner.Kind;
import cop5556sp17.Scanner.Token;
import cop5556sp17.AST.AssignmentStatement;
import cop5556sp17.AST.BinaryChain;
import cop5556sp17.AST.BinaryExpression;
import cop5556sp17.AST.Block;
import cop5556sp17.AST.BooleanLitExpression;
import cop5556sp17.AST.ConstantExpression;
import cop5556sp17.AST.Dec;
import cop5556sp17.AST.Expression;
import cop5556sp17.AST.FilterOpChain;
import cop5556sp17.AST.FrameOpChain;
import cop5556sp17.AST.IdentChain;
import cop5556sp17.AST.IdentExpression;
import cop5556sp17.AST.IdentLValue;
import cop5556sp17.AST.IfStatement;
import cop5556sp17.AST.ImageOpChain;
import cop5556sp17.AST.IntLitExpression;
import cop5556sp17.AST.ParamDec;
import cop5556sp17.AST.Program;
import cop5556sp17.AST.SleepStatement;
import cop5556sp17.AST.Statement;
import cop5556sp17.AST.Tuple;
import cop5556sp17.AST.Type.TypeName;
import cop5556sp17.AST.WhileStatement;

public class CodeGenVisitor implements cop5556sp17.AST.ASTVisitor, Opcodes {

	/**
	 * @param DEVEL
	 *            used as parameter to genPrint and genPrintTOS
	 * @param GRADE
	 *            used as parameter to genPrint and genPrintTOS
	 * @param sourceFileName
	 *            name of source file, may be null.
	 */
	public CodeGenVisitor(boolean DEVEL, boolean GRADE, String sourceFileName) {
		super();
		this.DEVEL = DEVEL;
		this.GRADE = GRADE;
		this.sourceFileName = sourceFileName;
	}

	ClassWriter cw;
	String className;
	String classDesc;
	String sourceFileName;
	MethodVisitor mv; // visitor of method currently under construction
	int slotNo=1; // Using to assign slot numbers
	int paramCount=0; // to keep a count of parameters
	
	/** Indicates whether genPrint and genPrintTOS should generate code. */
	final boolean DEVEL;
	final boolean GRADE;

	@Override
	public Object visitProgram(Program program, Object arg) throws Exception {
		cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		className = program.getName();
		classDesc = "L" + className + ";";
		String sourceFileName = (String) arg;
		cw.visit(52, ACC_PUBLIC + ACC_SUPER, className, null, "java/lang/Object",
				new String[] { "java/lang/Runnable" });
		cw.visitSource(sourceFileName, null);

		// generate constructor code
		// get a MethodVisitor
		mv = cw.visitMethod(ACC_PUBLIC, "<init>", "([Ljava/lang/String;)V", null,
				null);
		mv.visitCode();
		// Create label at start of code
		Label constructorStart = new Label();
		mv.visitLabel(constructorStart);
		// this is for convenience during development--you can see that the code
		// is doing something.
		CodeGenUtils.genPrint(DEVEL, mv, "\nentering <init>");
		// generate code to call superclass constructor
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		// visit parameter decs to add each as field to the class
		// pass in mv so decs can add their initialization code to the
		// constructor.
		ArrayList<ParamDec> params = program.getParams();
		for (ParamDec paramDec : params)
			paramDec.visit(this, mv);
		mv.visitInsn(RETURN);
		// create label at end of code
		Label constructorEnd = new Label();
		mv.visitLabel(constructorEnd);
		// finish up by visiting local vars of constructor
		// the fourth and fifth arguments are the region of code where the local
		// variable is defined as represented by the labels we inserted.
		mv.visitLocalVariable("this", classDesc, null, constructorStart, constructorEnd, 0);
		mv.visitLocalVariable("args", "[Ljava/lang/String;", null, constructorStart, constructorEnd, 1);
		// indicates the max stack size for the method.
		// because we used the COMPUTE_FRAMES parameter in the classwriter
		// constructor, asm
		// will do this for us. The parameters to visitMaxs don't matter, but
		// the method must
		// be called.
		mv.visitMaxs(1, 1);
		// finish up code generation for this method.
		mv.visitEnd();
		// end of constructor
		mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null,
				null);
		mv.visitCode();
		Label mainStart = new Label();
		mv.visitLabel(mainStart);
		// this is for convenience during development--you can see that the code
		// is doing something.
		CodeGenUtils.genPrint(DEVEL, mv, "\nentering main");
		mv.visitTypeInsn(NEW, className);
		mv.visitInsn(DUP);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, className, "<init>", "([Ljava/lang/String;)V", false);
		mv.visitMethodInsn(INVOKEVIRTUAL, className, "run", "()V", false);
		mv.visitInsn(RETURN);
		Label mainEnd = new Label();
		mv.visitLabel(mainEnd);
		mv.visitLocalVariable("args", "[Ljava/lang/String;", null, mainStart, mainEnd, 0);
		mv.visitLocalVariable("instance", classDesc, null, mainStart, mainEnd, 1);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		
		// create run method
		mv = cw.visitMethod(ACC_PUBLIC, "run", "()V", null, null);
		mv.visitCode();
		Label startRun = new Label();
		mv.visitLabel(startRun);
		CodeGenUtils.genPrint(DEVEL, mv, "\nentering run");
		program.getB().visit(this, null);
		mv.visitInsn(RETURN);
		Label endRun = new Label();
		mv.visitLabel(endRun);
		mv.visitLocalVariable("this", classDesc, null, startRun, endRun, 0);
		mv.visitMaxs(1, 1);
		mv.visitEnd(); // end of run method
			
		cw.visitEnd();//end of class
		return cw.toByteArray();
	}



	@Override
	public Object visitAssignmentStatement(AssignmentStatement assignStatement, Object arg) throws Exception 
	{
		assignStatement.getE().visit(this, arg);
		CodeGenUtils.genPrint(DEVEL, mv, "\nassignment: " + assignStatement.var.getText() + "=");
		CodeGenUtils.genPrintTOS(GRADE, mv, assignStatement.getE().getType());
		TypeName expressionType = assignStatement.getE().getType();
		if(expressionType.isType(TypeName.IMAGE))
		{
			mv.visitInsn(DUP);
		}
		assignStatement.getVar().visit(this, arg);
		return null;
	}

	@Override
	public Object visitBinaryChain(BinaryChain binaryChain, Object arg) throws Exception {
		//Object toPass;
		if(binaryChain.getE0() instanceof FilterOpChain)
			binaryChain.getE0().visit(this, binaryChain.getArrow().kind);
		else
		   binaryChain.getE0().visit(this, 1); //using 1 and 2 as flag
		TypeName e1Type = binaryChain.getE0().getType();
		if(e1Type.equals(TypeName.URL)){
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeImageIO", "readFromURL", "(Ljava/net/URL;)Ljava/awt/image/BufferedImage;", false);
		}
		else if(binaryChain.getE0().getType().equals(TypeName.FILE)){

			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeImageIO", "readFromFile", "(Ljava/io/File;)Ljava/awt/image/BufferedImage;", false);
		}
		if(binaryChain.getE1() instanceof FilterOpChain)
			binaryChain.getE1().visit(this, binaryChain.getArrow().kind); // passing kind to check for Barraow
		else
			binaryChain.getE1().visit(this, 2);
		return null;
	}



	
	@Override
	public Object visitBinaryExpression(BinaryExpression binaryExpression, Object arg) throws Exception {	
		Label bExpStart = new Label();
		Label bExpEnd = new Label();
		binaryExpression.getE0().visit(this, arg);
		binaryExpression.getE1().visit(this, arg);
		TypeName e2Type = binaryExpression.getE0().getType();
		TypeName e1Type = binaryExpression.getE1().getType();
		Kind op = binaryExpression.getOp().kind;
		if(op.equals(MOD))
		{
			if (e2Type.equals(TypeName.IMAGE)) {
				mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeImageOps", "mod",
						"(Ljava/awt/image/BufferedImage;I)Ljava/awt/image/BufferedImage;", false);
			} else 
				mv.visitInsn(IREM);
			
		}else if (op.equals(OR))
		{
			mv.visitInsn(IOR);
		}
		else if (op.equals(MINUS))
		{
			if(e2Type.isType(IMAGE))
			{
				mv.visitInsn(SWAP);
				mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeImageOps", "sub",
						"(Ljava/awt/image/BufferedImage;Ljava/awt/image/BufferedImage;)Ljava/awt/image/BufferedImage;",
						false);
			}else
			mv.visitInsn(ISUB);
		}
		else if(op.equals(PLUS))
		{
			if(e2Type.isType(IMAGE))
			{
				mv.visitInsn(SWAP);
				mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeImageOps", "add",
						"(Ljava/awt/image/BufferedImage;Ljava/awt/image/BufferedImage;)Ljava/awt/image/BufferedImage;",
						false);
			}else
			mv.visitInsn(IADD);
		}
		else if(op.equals(TIMES))
		{
			if (e2Type.equals(TypeName.IMAGE)) {
				mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeImageOps", "mul",
						"(Ljava/awt/image/BufferedImage;I)Ljava/awt/image/BufferedImage;", false);
			} else if (e1Type.equals(TypeName.IMAGE)) {
				mv.visitInsn(SWAP);
				mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeImageOps", "mul",
						"(Ljava/awt/image/BufferedImage;I)Ljava/awt/image/BufferedImage;",
						false);
			} else 
			mv.visitInsn(IMUL);
		}
		else if(op.equals(AND))
		{		
			mv.visitInsn(IAND);
		}
		else if(op.equals(DIV))
		{
			if (e2Type.equals(TypeName.IMAGE)) {
				mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeImageOps", "div",
						"(Ljava/awt/image/BufferedImage;I)Ljava/awt/image/BufferedImage;",
						false);
			} else
			mv.visitInsn(IDIV);
		}
		else if (op.equals(LT))
		{
				mv.visitJumpInsn(IF_ICMPLT,bExpStart);
			mv.visitLdcInsn(false);
		}
		else if(op.equals(LE))
		{
			mv.visitJumpInsn(IF_ICMPLE,bExpStart);
			mv.visitLdcInsn(false);
		}
		else if(op.equals(GT))
		{
			mv.visitJumpInsn(IF_ICMPGT,bExpStart);
			mv.visitLdcInsn(false);		
		}
		else if (op.equals(GE))
		{
			mv.visitJumpInsn(IF_ICMPGE,bExpStart);
			mv.visitLdcInsn(false);
		}
		else if (op.equals(EQUAL))
		{
			if(binaryExpression.getType().equals(IMAGE))
				mv.visitJumpInsn(IF_ACMPEQ ,bExpStart);
			else
			mv.visitJumpInsn(IF_ICMPEQ,bExpStart);
			mv.visitLdcInsn(false);
		}
		else if(op.equals(NOTEQUAL))
		{
			if(binaryExpression.getType().equals(IMAGE))
				mv.visitJumpInsn(IF_ACMPNE ,bExpStart);
			else
			mv.visitJumpInsn(IF_ICMPNE,bExpStart);
			mv.visitLdcInsn(false);
		}
		mv.visitJumpInsn(GOTO, bExpEnd);
		mv.visitLabel(bExpStart);
		mv.visitLdcInsn(true);
		mv.visitLabel(bExpEnd);
		return null;
	}

	@Override
	public Object visitBlock(Block block, Object arg) throws Exception {
		Label Start=new Label();
		Label End=new Label();
		for(Dec d: block.getDecs())
		{
			if(d.getType().isType(TypeName.IMAGE,TypeName.FRAME))
			{
				mv.visitInsn(ACONST_NULL);
				mv.visitVarInsn(ASTORE,slotNo);
			}
			d.visit(this, arg);
			mv.visitLocalVariable(d.getIdent().getText(), d.getType().getJVMTypeDesc(), null, Start, End, d.getSlot());
		}
		
		mv.visitLabel(Start);
		for(Statement  s: block.getStatements())
		{		
			if(s instanceof AssignmentStatement)
				{
				
					if(((AssignmentStatement)s).getVar().getDec() instanceof ParamDec)
						mv.visitVarInsn(ALOAD,0);	
				}
			s.visit(this, arg);
			if((s) instanceof BinaryChain)
				mv.visitInsn(POP);	
		}
		mv.visitLabel(End);
		return null;
	}

	@Override
	public Object visitBooleanLitExpression(BooleanLitExpression booleanLitExpression, Object arg) throws Exception {
		mv.visitLdcInsn(booleanLitExpression.getValue());
		return null;
	}

	@Override
	public Object visitConstantExpression(ConstantExpression constantExpression, Object arg) {
		Token first = constantExpression.getFirstToken();
		if(first.isKind(Kind.KW_SCREENHEIGHT)){
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeFrame", "getScreenHeight", "()I", false);
		}
		else if(first.isKind(Kind.KW_SCREENWIDTH)){
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeFrame", "getScreenWidth", "()I", false);
		}
		return null;
	}

	@Override
	public Object visitDec(Dec declaration, Object arg) throws Exception {
		declaration.setSlot(slotNo);
		slotNo++;
		return null;
	}

	@Override
	public Object visitFilterOpChain(FilterOpChain filterOpChain, Object arg) throws Exception {
		Kind kindOfop = (Kind) arg; //passing from chain
		if(kindOfop.equals(BARARROW) && filterOpChain.getFirstToken().kind.equals(Kind.OP_GRAY))
		{
			mv.visitInsn(DUP);
		}
		else
		mv.visitInsn(ACONST_NULL);
		
		if(filterOpChain.getFirstToken().kind.equals(Kind.OP_BLUR)){
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeFilterOps", "blurOp", "(Ljava/awt/image/BufferedImage;Ljava/awt/image/BufferedImage;)Ljava/awt/image/BufferedImage;", false);
		}
		else if(filterOpChain.getFirstToken().kind.equals(Kind.OP_CONVOLVE)){
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeFilterOps", "convolveOp", "(Ljava/awt/image/BufferedImage;Ljava/awt/image/BufferedImage;)Ljava/awt/image/BufferedImage;", false);
		}
		if(filterOpChain.getFirstToken().kind.equals(Kind.OP_GRAY)){
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeFilterOps", "grayOp", "(Ljava/awt/image/BufferedImage;Ljava/awt/image/BufferedImage;)Ljava/awt/image/BufferedImage;", false);
		}
		return null;
	}

	@Override
	public Object visitFrameOpChain(FrameOpChain frameOpChain, Object arg) throws Exception {
		frameOpChain.getArg().visit(this, arg);
		
		if(frameOpChain.getFirstToken().kind.equals(Kind.KW_SHOW)){
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKEVIRTUAL, "cop5556sp17/PLPRuntimeFrame", "showImage", "()Lcop5556sp17/PLPRuntimeFrame;", false);
		}
		else if(frameOpChain.getFirstToken().kind.equals(Kind.KW_MOVE)){
			mv.visitMethodInsn(INVOKEVIRTUAL, "cop5556sp17/PLPRuntimeFrame", "moveFrame", "(II)Lcop5556sp17/PLPRuntimeFrame;", false);
		}
		else if(frameOpChain.getFirstToken().kind.equals(Kind.KW_HIDE)){
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKEVIRTUAL, "cop5556sp17/PLPRuntimeFrame", "hideImage", "()Lcop5556sp17/PLPRuntimeFrame;", false);
		}
		else if(frameOpChain.getFirstToken().kind.equals(Kind.KW_XLOC)){
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKEVIRTUAL, "cop5556sp17/PLPRuntimeFrame", "getXVal", "()I", false);
		}
		else if(frameOpChain.getFirstToken().kind.equals(Kind.KW_YLOC)){
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKEVIRTUAL, "cop5556sp17/PLPRuntimeFrame", "getYVal", "()I", false);
		}
		return null;
	}

	@Override
	public Object visitIdentChain(IdentChain identChain, Object arg) throws Exception {
		/* flag :
		 * 1 for left
		 * 2 for right
		 */
		Integer flag = (Integer) arg;

		if(flag==1){
			if(identChain.getDec() instanceof ParamDec){
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, className, identChain.getFirstToken().getText(), ((TypeName) identChain.getType()).getJVMTypeDesc());
			} else {
				// this is a local var
				if (identChain.getType().equals(TypeName.INTEGER) || identChain.getType().equals(TypeName.BOOLEAN)) {
					mv.visitVarInsn(ILOAD, identChain.getDec().getSlot());
				}else if(identChain.getType().equals(TypeName.FILE) || identChain.getType().equals(TypeName.IMAGE)){
					mv.visitVarInsn(ALOAD, identChain.getDec().getSlot());
				}
			}
		}
		else{
			if(identChain.getType().equals(TypeName.FILE)){
				if(identChain.getDec() instanceof ParamDec){
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, className, identChain.getFirstToken().getText(), ((TypeName) identChain.getType()).getJVMTypeDesc());
				}
				else {
					// local variable
					mv.visitVarInsn(ALOAD, identChain.getDec().getSlot());
				}

				mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeImageIO", "write",
						"(Ljava/awt/image/BufferedImage;Ljava/io/File;)Ljava/awt/image/BufferedImage;", false);
			}
			else if(identChain.getType().equals(TypeName.FRAME)){
				if(identChain.getDec() instanceof ParamDec){
					// class variable
					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, className, identChain.getFirstToken().getText(), ((TypeName) identChain.getType()).getJVMTypeDesc());
				}
				else {
					// local variable
					mv.visitInsn(DUP);
					mv.visitVarInsn(ALOAD, identChain.getDec().getSlot());
				}
				mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeFrame", "createOrSetFrame",
						"(Ljava/awt/image/BufferedImage;Lcop5556sp17/PLPRuntimeFrame;)Lcop5556sp17/PLPRuntimeFrame;", false);
			}
			
			mv.visitInsn(DUP);
			TypeName identChainType = identChain.getType();	
			if (identChainType.equals(TypeName.IMAGE) || identChainType.equals(TypeName.INTEGER)
					|| identChainType.equals(TypeName.BOOLEAN) || identChainType.equals(TypeName.FRAME)) {
				if (identChain.getDec() instanceof ParamDec) {
					mv.visitVarInsn(ALOAD, 0);
					mv.visitInsn(SWAP);
					mv.visitFieldInsn(PUTFIELD, className, identChain.getFirstToken().getText(),
							((TypeName) identChainType).getJVMTypeDesc());
				} else {
					if (identChainType.equals(TypeName.IMAGE)) {
						mv.visitVarInsn(ASTORE, identChain.getDec().getSlot());
					} else if (identChainType.equals(TypeName.INTEGER)
							|| identChainType.equals(TypeName.BOOLEAN)) {
						mv.visitVarInsn(ISTORE, identChain.getDec().getSlot());
					} else if (identChainType.equals(TypeName.FRAME)) {
						mv.visitVarInsn(ASTORE, identChain.getDec().getSlot());
					}
				}
			}
		}
		return null;
	}



	@Override
	public Object visitIdentExpression(IdentExpression identExpression, Object arg) throws Exception {
		if (identExpression.getDec() instanceof ParamDec) {
			mv.visitVarInsn(ALOAD, 0);

			mv.visitFieldInsn(GETFIELD, className, identExpression.getFirstToken().getText(),
					identExpression.getType().getJVMTypeDesc());
		} else {
			if (identExpression.getType().equals(TypeName.INTEGER)
					|| identExpression.getType().equals(TypeName.BOOLEAN)) {
				mv.visitVarInsn(ILOAD, identExpression.getDec().getSlot());
			} else if (identExpression.getType().equals(TypeName.IMAGE)
					|| identExpression.getType().equals(TypeName.FRAME)
					|| identExpression.getType().equals(TypeName.FILE)
					|| identExpression.getType().equals(TypeName.URL)) {
				mv.visitVarInsn(ALOAD, identExpression.getDec().getSlot());
			}
		}
		return null;
	}
	

	@Override
	public Object visitIdentLValue(IdentLValue lValue, Object arg) throws Exception {
		if (lValue.getDec() instanceof ParamDec) {
					mv.visitFieldInsn(PUTFIELD, className, lValue.getFirstToken().getText(),
					lValue.getType().getJVMTypeDesc());
		} else {
			if (lValue.getType().equals(TypeName.IMAGE)) {
				mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeImageOps", "copyImage", "(Ljava/awt/image/BufferedImage;)Ljava/awt/image/BufferedImage;", false);
				mv.visitVarInsn(ASTORE, lValue.getDec().getSlot());
			} else if (lValue.getType().equals(TypeName.INTEGER) || lValue.getType().equals(TypeName.BOOLEAN)) {
				mv.visitVarInsn(ISTORE, lValue.getDec().getSlot());
			}	
		}
		return null;
	}



	@Override
	public Object visitIfStatement(IfStatement ifStatement, Object arg) throws Exception 
	{
		ifStatement.getE().visit(this, arg);
		Label AFTER=new Label();
		mv.visitJumpInsn(IFEQ,AFTER);
		ifStatement.getB().visit(this, arg);
		mv.visitLabel(AFTER);
		return null;
	}

	@Override
	public Object visitImageOpChain(ImageOpChain imageOpChain, Object arg) throws Exception {
		imageOpChain.getArg().visit(this, arg);
		if(imageOpChain.getFirstToken().isKind(Kind.OP_WIDTH)){
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/awt/image/BufferedImage", "getWidth", "()I", false);
		}
		else if(imageOpChain.getFirstToken().isKind(Kind.OP_HEIGHT)){
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/awt/image/BufferedImage", "getHeight", "()I", false);
		}
		else if(imageOpChain.getFirstToken().isKind(Kind.KW_SCALE)){
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeImageOps", "scale", "(Ljava/awt/image/BufferedImage;I)Ljava/awt/image/BufferedImage;", false);
		}
		return null;
	}

	
	@Override
	public Object visitIntLitExpression(IntLitExpression intLitExpression, Object arg) throws Exception 
	{
		mv.visitLdcInsn(intLitExpression.value);
		return null;
	}


	@Override
	public Object visitParamDec(ParamDec paramDec, Object arg) throws Exception 
	{
		
		FieldVisitor fv=cw.visitField(ACC_PUBLIC, paramDec.getIdent().getText(), paramDec.getType().getJVMTypeDesc(), null, null);
		fv.visitEnd();
		TypeName pType = paramDec.getType();
		
		switch(pType)
		{
		case INTEGER:
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(paramCount++);
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "parseInt", "(Ljava/lang/String;)I",false);
			mv.visitFieldInsn(PUTFIELD,className, paramDec.getIdent().getText(), "I");
			break;
		case BOOLEAN:
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(paramCount++);
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "parseBoolean", "(Ljava/lang/String;)Z",false);
			mv.visitFieldInsn(PUTFIELD,className, paramDec.getIdent().getText(), "Z");
			break;
		case FILE:
			mv.visitVarInsn(ALOAD, 0);
			mv.visitTypeInsn(NEW, "java/io/File");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(paramCount++);
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESPECIAL, "java/io/File", "<init>", "(Ljava/lang/String;)V", false);
			mv.visitFieldInsn(PUTFIELD, className, paramDec.getIdent().getText(), "Ljava/io/File;");
			break;
		case URL:
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(paramCount++);
			mv.visitMethodInsn(INVOKESTATIC, "cop5556sp17/PLPRuntimeImageIO", "getURL", "([Ljava/lang/String;I)Ljava/net/URL;", false);
			mv.visitFieldInsn(PUTFIELD, className, paramDec.getIdent().getText(), "Ljava/net/URL;");
			break;
		default:
			break;	
		}
		
		return null;

	}

	@Override
	public Object visitSleepStatement(SleepStatement sleepStatement, Object arg) throws Exception {
		sleepStatement.getE().visit(this, arg);
		mv.visitInsn(I2L);
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/Thread", "sleep", "(J)V", false);
		return null;
	}

	@Override
	public Object visitTuple(Tuple tuple, Object arg) throws Exception {
		for(Expression expression: tuple.getExprList()){
			expression.visit(this, arg);
		}
		return null;
	}

	
	
	@Override
	public Object visitWhileStatement(WhileStatement whileStatement, Object arg) throws Exception 
	{
		Label GUARD=new Label();
		mv.visitJumpInsn(GOTO, GUARD);
		Label BODY=new Label();
		mv.visitLabel(BODY);
		whileStatement.getB().visit(this, arg);
		mv.visitLabel(GUARD);
		whileStatement.getE().visit(this, arg);
		mv.visitJumpInsn(IFNE,BODY);
		return null;
	}

}