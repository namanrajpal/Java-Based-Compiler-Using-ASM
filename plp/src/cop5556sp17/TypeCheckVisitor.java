package cop5556sp17;

import cop5556sp17.AST.ASTNode;
import cop5556sp17.AST.ASTVisitor;
import cop5556sp17.AST.Tuple;
import cop5556sp17.AST.Type;
import cop5556sp17.AST.AssignmentStatement;
import cop5556sp17.AST.BinaryChain;
import cop5556sp17.AST.BinaryExpression;
import cop5556sp17.AST.Block;
import cop5556sp17.AST.BooleanLitExpression;
import cop5556sp17.AST.Chain;
import cop5556sp17.AST.ChainElem;
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
import cop5556sp17.AST.Type.TypeName;
import cop5556sp17.AST.WhileStatement;

import java.util.ArrayList;
import java.util.List;

import cop5556sp17.Scanner.Kind;
import cop5556sp17.Scanner.LinePos;
import cop5556sp17.Scanner.Token;
import cop5556sp17.TypeCheckVisitor.TypeCheckException;

import static cop5556sp17.AST.Type.TypeName.*;
import static cop5556sp17.Scanner.Kind.ARROW;
import static cop5556sp17.Scanner.Kind.KW_HIDE;
import static cop5556sp17.Scanner.Kind.KW_MOVE;
import static cop5556sp17.Scanner.Kind.KW_SHOW;
import static cop5556sp17.Scanner.Kind.KW_XLOC;
import static cop5556sp17.Scanner.Kind.KW_YLOC;
import static cop5556sp17.Scanner.Kind.OP_BLUR;
import static cop5556sp17.Scanner.Kind.OP_CONVOLVE;
import static cop5556sp17.Scanner.Kind.OP_GRAY;
import static cop5556sp17.Scanner.Kind.OP_HEIGHT;
import static cop5556sp17.Scanner.Kind.OP_WIDTH;
import static cop5556sp17.Scanner.Kind.*;

public class TypeCheckVisitor implements ASTVisitor {
	SymbolTable symtab = new SymbolTable();
	
	@SuppressWarnings("serial")
	public static class TypeCheckException extends Exception {
		TypeCheckException(String message) {
			super(message);
		}
	}

	@Override
	public Object visitBinaryChain(BinaryChain binaryChain, Object arg) throws Exception {
			Chain chain=binaryChain.getE0();
			ChainElem chainElem =binaryChain.getE1();
			Token arrow=binaryChain.getArrow();
			TypeName t1=(TypeName) chain.visit(this, arg);
			TypeName t2=(TypeName) chainElem.visit(this, arg);
			Token firstElemChain=chainElem.firstToken;
			
			if(arrow.kind.equals(ARROW))
			{
				//System.out.println(t1 + " " + t2);
				if(t1.equals(URL) && t2.equals(IMAGE))
					binaryChain.val=IMAGE;
				else
					if(t1.equals(FILE) && t2.equals(IMAGE))
						binaryChain.val=IMAGE;
					else 
						if(t1.equals(FRAME) && chainElem instanceof FrameOpChain){
							if(firstElemChain.kind.equals(KW_XLOC) || firstElemChain.kind.equals(KW_YLOC))
								binaryChain.val=INTEGER;
							
							else if(firstElemChain.kind.equals(KW_SHOW) || firstElemChain.kind.equals(KW_HIDE) || firstElemChain.kind.equals(KW_MOVE))
								binaryChain.val=FRAME;
							
								else throw new TypeCheckException("ILLEGAL TYPE");
						}
						else if(t1.equals(IMAGE) && chainElem instanceof ImageOpChain){
							if(firstElemChain.kind.equals(OP_WIDTH) || firstElemChain.kind.equals(OP_HEIGHT))
								binaryChain.val=INTEGER;
							else if(firstElemChain.kind.equals(KW_SCALE))
								binaryChain.val=IMAGE;
							else throw new TypeCheckException("ILLEGAL TYPE");
						}	
				
						else if(t1.equals(IMAGE) && t2.equals(FRAME))
							binaryChain.val=FRAME;
						else if(t1.equals(IMAGE) && t2.equals(FILE))
							binaryChain.val=NONE;
						else if(t1.equals(IMAGE) && chainElem instanceof IdentChain && chainElem.val.equals(IMAGE))
							binaryChain.val=IMAGE;
						else if(t1.equals(INTEGER) && chainElem instanceof IdentChain && chainElem.val.equals(INTEGER))
							binaryChain.val=INTEGER;
						else if(t1.equals(IMAGE) && chainElem instanceof FilterOpChain){
							if(firstElemChain.kind.equals(OP_GRAY) || firstElemChain.kind.equals(OP_BLUR) || firstElemChain.kind.equals(OP_CONVOLVE))
								binaryChain.val=IMAGE;
							else throw new TypeCheckException("ILLEGAL TYPE");
							}
						else throw new TypeCheckException("\nILLEGAL TYPE " + binaryChain.firstToken.getLinePos() + ":\n" + binaryChain.toString());
			}
			
			else if(arrow.kind.equals(BARARROW)){
				if(t1.equals(IMAGE) && chainElem instanceof FilterOpChain){
					if(firstElemChain.kind.equals(OP_GRAY) || firstElemChain.kind.equals(OP_BLUR) || firstElemChain.kind.equals(OP_CONVOLVE))
						binaryChain.val=IMAGE;
					else throw new TypeCheckException("ILLEGAL TYPE");
					
				}
				
				else throw new TypeCheckException("ILLEGAL TYPE");
				
			}
			else throw new TypeCheckException("ILLEGAL TYPE");
			return binaryChain.val;
		
	}

	@Override
	public Object visitBinaryExpression(BinaryExpression binaryExpression, Object arg) throws Exception {
		
		Expression exp1=binaryExpression.getE0();
		Expression exp2=binaryExpression.getE1();
		Token operator=binaryExpression.getOp();
		//System.out.println("Serving " + exp1.firstToken.getText() + " " + exp2.firstToken.getText());
		
		TypeName t1=(TypeName) exp1.visit(this, null);
		TypeName t2=(TypeName) exp2.visit(this, null);
		if(operator.kind.equals(PLUS) ||operator.kind.equals(MINUS))
		{
			if(t1.equals(INTEGER) && t2.equals(INTEGER))
				binaryExpression.val=INTEGER;
			else if(t1.equals(IMAGE) && t2.equals(IMAGE))
				binaryExpression.val=IMAGE;
			else throw new TypeCheckException("ILLEGAL TYPE");
				
		}
		
		else if(operator.kind.equals(TIMES))
		{
			if(t1.equals(INTEGER) && t2.equals(INTEGER))
				binaryExpression.val=INTEGER;
			else if(t1.equals(INTEGER) && t2.equals(IMAGE))
				binaryExpression.val=IMAGE;
			else if(t1.equals(IMAGE) && t2.equals(INTEGER))
				binaryExpression.val=IMAGE;
			else throw new TypeCheckException("ILLEGAL TYPE");
			
			
		}
		
		else if(operator.kind.equals(DIV))
		{
			if(t1.equals(INTEGER) && t2.equals(INTEGER))
				binaryExpression.val=INTEGER;
			else if(t1.equals(IMAGE) && t2.equals(INTEGER))
				binaryExpression.val=IMAGE;
			else throw new TypeCheckException("ILLEGAL TYPE");
			}
		
		
		else if(operator.kind.equals(LT) ||operator.kind.equals(GT) || operator.kind.equals(LE) ||operator.kind.equals(GE))
		{
			if(t1.equals(INTEGER) && t2.equals(INTEGER))
				binaryExpression.val=BOOLEAN;
			else if(t1.equals(BOOLEAN) && t2.equals(BOOLEAN))
				binaryExpression.val=BOOLEAN;
			else throw new TypeCheckException("ILLEGAL TYPE");
				
		}
		
		else if(operator.kind.equals(EQUAL) ||operator.kind.equals(NOTEQUAL)){
			if(t1.equals(t2))
				binaryExpression.val=BOOLEAN;
			
			else throw new TypeCheckException("ILLEGAL TYPE");
		}else if(operator.kind.equals(AND) ||operator.kind.equals(OR))
		{
			if(t1.equals(t2))
				binaryExpression.val=BOOLEAN;
			
			else throw new TypeCheckException("ILLEGAL TYPE");
		}else if(operator.kind.equals(MOD))
		{
			if(t1.equals(INTEGER) && t2.equals(INTEGER))
				binaryExpression.val=t1;
			else if(t1.equals(IMAGE) && t2.equals(INTEGER))
				binaryExpression.val=t1;
			else throw new TypeCheckException("ILLEGAL TYPE");
		}
		else throw new TypeCheckException("ILLEGAL TYPE :\n " + binaryExpression.toString() + " "+ binaryExpression.val);
		
		return binaryExpression.val;
	}

	@Override
	public Object visitBlock(Block block, Object arg) throws Exception {
		symtab.enterScope();
		//System.out.println(block.toString());
		
		for(Dec dec:block.getDecs()){
			dec.visit(this,arg);
		}
		for(Statement stmt:block.getStatements()){
			stmt.visit(this,arg);
		}
		symtab.leaveScope();
		return null;
	}

	
	@Override
	public Object visitBooleanLitExpression(BooleanLitExpression booleanLitExpression, Object arg) throws Exception {
		booleanLitExpression.val=BOOLEAN;
		return booleanLitExpression.val;
	}

	@Override
	public Object visitFilterOpChain(FilterOpChain filterOpChain, Object arg) throws Exception {
		Tuple t=filterOpChain.getArg();
		List<Expression> exprList=t.getExprList();
		t.visit(this, arg);
		
		if(exprList.size() == 0)
			filterOpChain.val = IMAGE;
		else
			throw new TypeCheckException("ILLEGAL TYPE");
		
		return filterOpChain.val;
	}

	@Override
	public Object visitFrameOpChain(FrameOpChain frameOpChain, Object arg) throws Exception {
		Tuple t=frameOpChain.getArg();
		List<Expression> exprList=t.getExprList();
		t.visit(this, arg);
		Kind frameOp = frameOpChain.getFirstToken().kind;
		//System.out.println(frameOp.text);
		
		switch(frameOp)
		{
		case KW_SHOW:
		case KW_HIDE:
		{
			if(exprList.size() == 0)
				frameOpChain.val = NONE;
			else
				throw new TypeCheckException("ILLEGAL TYPE");
		}break;
		case KW_XLOC:
		case KW_YLOC:
		{
			if(exprList.size() == 0)
				frameOpChain.val = INTEGER;
			else
				throw new TypeCheckException("ILLEGAL TYPE");
		}break;
		case KW_MOVE:
		{
			if(exprList.size() == 2)
				frameOpChain.val = NONE;
			else
				throw new TypeCheckException("ILLEGAL TYPE");
		} break;
		
		default:
			throw new TypeCheckException("ILLEGAL TYPE");
		}
		
		return frameOpChain.val;
	
	}

	@Override
	public Object visitIdentChain(IdentChain identChain, Object arg) throws Exception {
		Dec dec= symtab.lookup(identChain.firstToken.getText());
		
		if(dec!=null){
			identChain.val = dec.val;
			identChain.dec = dec;
	}
		else
			throw new TypeCheckException("ILLEGAL TYPE");
		
		return identChain.val;
	}

	@Override
	public Object visitIdentExpression(IdentExpression identExpression, Object arg) throws Exception {
		Dec dec= symtab.lookup(identExpression.firstToken.getText());
		
		if(dec != null)
		{
			identExpression.val =dec.val;
			identExpression.dec = dec;
		}
		
		else
			throw new TypeCheckException("ILLEGAL TYPE");
		
		return identExpression.val;
	}
	

	@Override
	public Object visitIfStatement(IfStatement ifStatement, Object arg) throws Exception {
		Expression exp=ifStatement.getE();
		if(!exp.visit(this,arg).equals(BOOLEAN))
			throw new TypeCheckException("ILLEGAL TYPE"); 
		ifStatement.getB().visit(this,arg);		
		return null;
	}

	@Override
	public Object visitIntLitExpression(IntLitExpression intLitExpression, Object arg) throws Exception {
		intLitExpression.val=INTEGER;
		return intLitExpression.val;
	}

	@Override
	public Object visitSleepStatement(SleepStatement sleepStatement, Object arg) throws Exception {
		Expression exp=sleepStatement.getE();
		if(!exp.visit(this,arg).equals(INTEGER))
			throw new TypeCheckException("ILLEGAL TYPE"); 
				
		return null;
	}

	@Override
	public Object visitWhileStatement(WhileStatement whileStatement, Object arg) throws Exception {
		Expression exp=whileStatement.getE();
		if(!exp.visit(this,arg).equals(BOOLEAN))
			throw new TypeCheckException("ILLEGAL TYPE"); 
		whileStatement.getB().visit(this,null);		
		return null;
	}

	@Override
	public Object visitDec(Dec declaration, Object arg) throws Exception {
		declaration.val = Type.getTypeName(declaration.getFirstToken());
		Boolean b = symtab.insert(declaration.getIdent().getText(), declaration);
		if(!b)
			throw new TypeCheckException("ILLEGAL TYPE at Line: " + declaration.firstToken.pos + " "+ declaration.toString());
		
		return declaration.val;
	}

	@Override
	public Object visitProgram(Program program, Object arg) throws Exception {
		Block b=program.getB();
		for(ParamDec x: program.getParams())
		{
			x.visit(this,arg);
		}
		b.visit(this, arg);
		return null;
		
	}

	@Override
	public Object visitAssignmentStatement(AssignmentStatement assignStatement, Object arg) throws Exception {
		if (!(assignStatement.getVar().visit(this, null).equals(assignStatement.getE().visit(this, null))))
			throw new TypeCheckException("ILLEGAL TYPE");
		
		return null;
	}

	@Override
	public Object visitIdentLValue(IdentLValue identX, Object arg) throws Exception {
		Dec d =symtab.lookup(identX.firstToken.getText());
		if(d!=null)
		{
			identX.dec=d;
			identX.val = d.val;
		}
		else
			throw new TypeCheckException("ILLEGAL TYPE");
			
			
		return identX.getDec().val;
	}

	@Override
	public Object visitParamDec(ParamDec paramDec, Object arg) throws Exception {
		paramDec.val = Type.getTypeName(paramDec.getFirstToken());
		Boolean b = symtab.insert(paramDec.getIdent().getText(), paramDec);
		if(!b)
			throw new TypeCheckException("ILLEGAL TYPE");
		return paramDec.val;
	}

	@Override
	public Object visitConstantExpression(ConstantExpression constantExpression, Object arg) {
		constantExpression.val=INTEGER;
		return constantExpression.val;
	}

	@Override
	public Object visitImageOpChain(ImageOpChain imageOpChain, Object arg) throws Exception {
		imageOpChain.getArg().visit(this, arg);
		Kind imageOp = imageOpChain.getFirstToken().kind;
		List<Expression> exprlist=imageOpChain.getArg().getExprList();
		switch(imageOp)
		{case KW_SCALE:
		{
			if(exprlist.size() == 1)
				imageOpChain.val= IMAGE;
			else
				throw new TypeCheckException("ILLEGAL TYPE");
		}
		break;
		case OP_WIDTH:
		case OP_HEIGHT:
		{
			if(exprlist.size() == 0)
				imageOpChain.val = INTEGER;
			else
				throw new TypeCheckException("ILLEGAL TYPE");
		}
		break;
		default:
			throw new TypeCheckException("ILLEGAL TYPE");
		}
		
		return imageOpChain.val;
	}

	@Override
	public Object visitTuple(Tuple tuple, Object arg) throws Exception {
		List<Expression> exprlist= tuple.getExprList();
		for(Expression e:exprlist)
		{
			if(!e.visit(this,arg).equals(INTEGER))
				throw new TypeCheckException("ILLEGAL TYPE + " + e.firstToken.getText() ); 
				
		}
		
		return null;
	}


}
