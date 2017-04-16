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

import cop5556sp17.Scanner.Kind;
import cop5556sp17.Scanner.LinePos;
import cop5556sp17.Scanner.Token;
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

	@SuppressWarnings("serial")
	public static class TypeCheckException extends Exception {
		TypeCheckException(String message) {
			super(message);
		}
	}

	SymbolTable symtab = new SymbolTable();

	@Override
	public Object visitBinaryChain(BinaryChain binaryChain, Object arg) throws Exception {
		Chain chain=binaryChain.getE0();
		ChainElem chainElem =binaryChain.getE1();
		
		TypeName t1=(TypeName) chain.visit(this, null);
		TypeName t2=(TypeName) chainElem.visit(this, null);
		Token firstElemChain=chainElem.firstToken;
		
		if(binaryChain.getArrow().kind.equals(ARROW))
		{
			
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
						
							else throw new TypeCheckException("ILLEGAL TYPE FOUND : " + binaryChain.val);
					}
					else if(t1.equals(IMAGE) && chainElem instanceof ImageOpChain){
						if(firstElemChain.kind.equals(OP_WIDTH) || firstElemChain.kind.equals(OP_HEIGHT))
							binaryChain.val=INTEGER;
						else if(firstElemChain.kind.equals(KW_SCALE))
							binaryChain.val=IMAGE;
						else throw new TypeCheckException("ILLEGAL TYPE FOUND : " + binaryChain.val);
					}	
			
					else if(t1.equals(IMAGE) && t2.equals(FRAME))
						binaryChain.val=FRAME;
					else if(t1.equals(IMAGE) && t2.equals(FILE))
						binaryChain.val=NONE;
					else if(t1.equals(IMAGE) && chainElem instanceof IdentChain)
						binaryChain.val=IMAGE;
					else if(t1.equals(IMAGE) && chainElem instanceof FilterOpChain){
						if(firstElemChain.kind.equals(OP_GRAY) || firstElemChain.kind.equals(OP_BLUR) || firstElemChain.kind.equals(OP_CONVOLVE))
							binaryChain.val=IMAGE;
						else throw new TypeCheckException("ILLEGAL TYPE FOUND : " + binaryChain.val);
						}
					else throw new TypeCheckException("ILLEGAL TYPE FOUND : " + binaryChain.val);
		}
		else if(binaryChain.getArrow().kind.equals(BARARROW)){
			if(t1.equals(IMAGE) && chainElem instanceof FilterOpChain){
				if(firstElemChain.kind.equals(OP_GRAY) || firstElemChain.kind.equals(OP_BLUR) || firstElemChain.kind.equals(OP_CONVOLVE))
					binaryChain.val=IMAGE;
				else throw new TypeCheckException("ILLEGAL TYPE FOUND : " + binaryChain.val);
				
			}
			
			else throw new TypeCheckException("ILLEGAL TYPE FOUND : " + binaryChain.val);
			
		}
		else throw new TypeCheckException("ILLEGAL TYPE FOUND : " + binaryChain.val);
		return binaryChain.val;
	}
	

	@Override
	public Object visitBinaryExpression(BinaryExpression binaryExpression, Object arg) throws Exception {
		// TODO 
		TypeName val1=(TypeName)binaryExpression.getE0().visit(this,null);
		TypeName val2=(TypeName)binaryExpression.getE1().visit(this,null);
		
		if(binaryExpression.getOp().kind.equals(PLUS)||binaryExpression.getOp().kind.equals(MINUS))
		{
			if(val1.equals(INTEGER)&&val2.equals(INTEGER))
			{
				binaryExpression.val=INTEGER;
			}
			else if(val1.equals(IMAGE)&&val2.equals(IMAGE))
			{
				binaryExpression.val=IMAGE;
			}
			else 
			{
				throw new TypeCheckException("Invalid type found" + binaryExpression.firstToken.getText()+ " at " + binaryExpression.firstToken.getLinePos());
			}
		}
		
		else if(binaryExpression.getOp().kind.equals(TIMES))
		{
			if(val1.equals(INTEGER)&&val2.equals(IMAGE))
			{
				binaryExpression.val=IMAGE;
			}
			else if(val1.equals(IMAGE)&&val2.equals(INTEGER))
			{
				binaryExpression.val=IMAGE;
			}
			else if(val1.equals(INTEGER)&&val2.equals(INTEGER))
			{
				binaryExpression.val=INTEGER;
			}
			else
			{  
			
				throw new TypeCheckException("Invalid type found" + binaryExpression.firstToken.getText()+ " at " + binaryExpression.firstToken.getLinePos());
			}
		}
		else if(binaryExpression.getOp().kind.equals(LT)||binaryExpression.getOp().kind.equals(GT)||binaryExpression.getOp().kind.equals(LE)||binaryExpression.getOp().kind.equals(GE))
		{
			if(val1.equals(INTEGER)&&val2.equals(INTEGER))
			{
				binaryExpression.val=BOOLEAN;
			}
			else if(val1.equals(BOOLEAN)&&val2.equals(BOOLEAN))
			{
				binaryExpression.val=BOOLEAN;
			}
			else
			{
				throw new TypeCheckException("Invalid type found" + binaryExpression.firstToken.getText()+ " at " + binaryExpression.firstToken.getLinePos());
			}
		}
		else if(binaryExpression.getOp().kind.equals(EQUAL)||binaryExpression.getOp().kind.equals(NOTEQUAL))
		{
			if(val1.equals(val2))
			{
				binaryExpression.val=BOOLEAN;
			}
			else
			{
			 throw new TypeCheckException("Invalid type found" + binaryExpression.firstToken.getText()+ " at " + binaryExpression.firstToken.getLinePos());
			}
		}
		else if(binaryExpression.getOp().kind.equals(DIV))
		{
			if(val1.equals(INTEGER)&&val2.equals(INTEGER))
			{
				binaryExpression.val=INTEGER;
			}
			else
			{
				throw new TypeCheckException("Invalid type found" + binaryExpression.firstToken.getText()+ " at " + binaryExpression.firstToken.getLinePos());
			}
		}
		else
		{
			throw new TypeCheckException("Invalid type found" + binaryExpression.firstToken.getText()+ " at " + binaryExpression.firstToken.getLinePos());
		}
		return binaryExpression.val;
	}

	@Override
	public Object visitBlock(Block block, Object arg) throws Exception {
		// TODO Auto-generated method stub
		symtab.enterScope();
		
		for(Dec d:block.getDecs())
			d.visit(this,null);
			
		for(Statement s:block.getStatements())
			s.visit(this,null);
		
		symtab.leaveScope();
		return null;
	}

	@Override
	public Object visitBooleanLitExpression(BooleanLitExpression booleanLitExpression, Object arg) throws Exception {
		// TODO Auto-generated method stub
		
		booleanLitExpression.val=BOOLEAN;
		return booleanLitExpression.val;
		
	}

	@Override
	public Object visitFilterOpChain(FilterOpChain filterOpChain, Object arg) throws Exception {
		// TODO Auto-generated method stub
		
		filterOpChain.getArg().visit(this, null);
		if(filterOpChain.getArg().getExprList().size() == 0)
			filterOpChain.val = IMAGE;
		
		else throw new TypeCheckException("Invalid type found" + filterOpChain.firstToken.getText()+ " at " + filterOpChain.firstToken.getLinePos());
		return filterOpChain.val;
	}
		

	@Override
	public Object visitFrameOpChain(FrameOpChain frameOpChain, Object arg) throws Exception {
		// TODO Auto-generated method stub
	
		frameOpChain.getArg().visit(this, null);
		
		Kind token = frameOpChain.getFirstToken().kind;
		
		if(token.equals(KW_SHOW) || token.equals(KW_HIDE))
		{
			if(frameOpChain.getArg().getExprList().size()==0)
				frameOpChain.val = NONE;
			else throw new TypeCheckException("Invalid type found" + frameOpChain.firstToken.getText()+ " at " + frameOpChain.firstToken.getLinePos());
		}
		
		else if(token.equals(KW_MOVE))
		{
			if(frameOpChain.getArg().getExprList().size()==2)
				frameOpChain.val = NONE;
			else throw new TypeCheckException("Invalid Cse " + frameOpChain.firstToken.getText() + " at " + frameOpChain.firstToken.getLinePos());
		}
		else if(token.equals(KW_XLOC) || token.equals(KW_YLOC))
		{
			if(frameOpChain.getArg().getExprList().size()==0)
				frameOpChain.val = INTEGER;
			else throw new TypeCheckException("Invalid type found " + frameOpChain.firstToken.getText() + " at " + frameOpChain.firstToken.getLinePos());
		}
		else 
			throw new TypeCheckException("Invalid type found" + frameOpChain.firstToken.getText()+ " at " + frameOpChain.firstToken.getLinePos());
		
		return frameOpChain.val;
	}
	

	@Override
	public Object visitIdentChain(IdentChain identChain, Object arg) throws Exception {
		// TODO Auto-generated method stub
		
		Dec new_ident = symtab.lookup(identChain.firstToken.getText());
		
		if(new_ident != null)
		{
			identChain.val = new_ident.val;
		}
		else
			throw new TypeCheckException("Invalid type found" + identChain.firstToken.getText()+ " at " + identChain.firstToken.getLinePos());
		
		return identChain.val;
	}
	

	@Override
	public Object visitIdentExpression(IdentExpression identExpression, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Dec new_ident = symtab.lookup(identExpression.firstToken.getText());
		if(new_ident != null)
		{
			identExpression.val = new_ident.val;
			identExpression.dec = new_ident;
		}
		else
			throw new TypeCheckException("Invalid type found" + identExpression.firstToken.getText()+ " at " + identExpression.firstToken.getLinePos());
		return identExpression.val;
	}
	

	@Override
	public Object visitIfStatement(IfStatement ifStatement, Object arg) throws Exception {
		// TODO Auto-generated method stub
		
		Expression e=ifStatement.getE();
		if(!e.visit(this,null).equals(BOOLEAN))
			throw new TypeCheckException("Invalid type found" + ifStatement.firstToken.getText()+ " at " + ifStatement.firstToken.getLinePos());
	    
		Block b=ifStatement.getB();
		b.visit(this,null);
		return null;
	}
	

	@Override
	public Object visitIntLitExpression(IntLitExpression intLitExpression, Object arg) throws Exception {
		// TODO Auto-generated method stub
		intLitExpression.val = INTEGER;
		return intLitExpression.val;
	}

	@Override
	public Object visitSleepStatement(SleepStatement sleepStatement, Object arg) throws Exception {
		// TODO Auto-generated method stub
	
		Expression e=sleepStatement.getE();
		if(!e.visit(this,null).equals(INTEGER))
			throw new TypeCheckException("Invalid type found" + sleepStatement.firstToken.getText()+ " at " + sleepStatement.firstToken.getLinePos());
		return null;
	}

	@Override
	public Object visitWhileStatement(WhileStatement whileStatement, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Expression e=whileStatement.getE();
		if(!e.visit(this,null).equals(BOOLEAN))
			throw new TypeCheckException("Invalid type found" + whileStatement.firstToken.getText()+ " at " + whileStatement.firstToken.getLinePos());
		Block b=whileStatement.getB();
		b.visit(this,null);
		return null;
	}
	

	@Override
	public Object visitDec(Dec declaration, Object arg) throws Exception {
		// TODO Auto-generated method stub
		
       declaration.val=Type.getTypeName(declaration.getFirstToken());
		
		if(!symtab.insert(declaration.getIdent().getText(), declaration))
		throw new TypeCheckException("Invalid type found" + declaration.firstToken.getText()+ " at " + declaration.firstToken.getLinePos());
		return declaration.val;
	}

	@Override
	public Object visitProgram(Program program, Object arg) throws Exception {
		// TODO Auto-generated method stub
		for(ParamDec p:program.getParams())
		{
			p.visit(this,null);
			
		}
		program.getB().visit(this,null);
		return null;
	}	
	

	@Override
	public Object visitAssignmentStatement(AssignmentStatement assignStatement, Object arg) throws Exception {
		// TODO Auto-generated method stub
	
		if(assignStatement.getVar().visit(this, null).equals(assignStatement.getE().visit(this, null)))
		{}
		else
			throw new TypeCheckException("Illegal Type" + assignStatement.firstToken.getText()+ " at " + assignStatement.firstToken.getLinePos());
		
		return null;
	
	}

	@Override
	public Object visitIdentLValue(IdentLValue identX, Object arg) throws Exception {
		// TODO Auto-generated method stub
		
		Dec ident = symtab.lookup(identX.firstToken.getText());
		
		if(ident!= null)
			identX.dec = ident;
		else
			throw new TypeCheckException("Invalid type found" + identX.firstToken.getText()+ " at " + identX.firstToken.getLinePos());
		return identX.getdec().val;
	}
	

	@Override
	public Object visitParamDec(ParamDec paramDec, Object arg) throws Exception {
		// TODO Auto-generated method stub
		
		paramDec.val = Type.getTypeName(paramDec.getFirstToken());
		if(!symtab.insert(paramDec.getIdent().getText(), paramDec))
			throw new TypeCheckException("Invalid type found" + paramDec.firstToken.getText()+ " at " + paramDec.firstToken.getLinePos());
		
		return paramDec.val;
	}
	

	@Override
	public Object visitConstantExpression(ConstantExpression constantExpression, Object arg) {
		// TODO Auto-generated method stub
	
		constantExpression.val = INTEGER;
		return constantExpression.val;
	}

	@Override
	public Object visitImageOpChain(ImageOpChain imageOpChain, Object arg) throws Exception {
		// TODO Auto-generated method stub
		
		imageOpChain.getArg().visit(this,null);
		
		Kind token = imageOpChain.getFirstToken().kind;
		
		if(token.equals(OP_WIDTH) || token.equals(OP_HEIGHT))
		{
			if(imageOpChain.getArg().getExprList().size()==0)
				imageOpChain.val = INTEGER;
			
			else throw new TypeCheckException("Invalid type found " + imageOpChain.firstToken.getText() + " at " + imageOpChain.firstToken.getLinePos());
		}
		else if(token.equals(KW_SCALE))
		{
			if(imageOpChain.getArg().getExprList().size()==1)
				imageOpChain.val = IMAGE;
			
			else throw new TypeCheckException("Invalid type found " + imageOpChain.firstToken.getText() + " at " + imageOpChain.firstToken.getLinePos());
		}
		else 
			throw new TypeCheckException("Invalid type found " + imageOpChain.firstToken.getText() + " at " + imageOpChain.firstToken.getLinePos());
		
		return imageOpChain.val;
		
	}

	@Override
	public Object visitTuple(Tuple tuple, Object arg) throws Exception {
		// TODO Auto-generated method stub
		for(Expression e : tuple.getExprList())
		{
			if(!e.visit(this,null).equals(INTEGER))
				throw new TypeCheckException("Invalid type found" + tuple.firstToken.getText()+ " at " + tuple.firstToken.getLinePos());
		}
		return null;
	}
	}



