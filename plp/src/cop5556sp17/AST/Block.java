package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;
import java.util.ArrayList;

public class Block extends ASTNode {
	
	final ArrayList<Dec> decList;
	final ArrayList<Statement> statementList;
	
	public Block(Token firstToken, ArrayList<Dec> decs, ArrayList<Statement> statements) {
		super(firstToken);
		this.decList = decs;
		this.statementList = statements;
	}

	public ArrayList<Dec> getDecs(){
		return decList;
	}

	public ArrayList<Statement> getStatements() {
		return statementList;
	}
	
	@Override
	public String toString() {
		return "Block [decList=" + decList.toString() + ", statementList=" + statementList.toString() + "]";
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((decList == null) ? 0 : decList.hashCode());
		result = prime * result + ((statementList == null) ? 0 : statementList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Block)) {
			return false;
		}
		Block other = (Block) obj;
		if (decList == null) {
			if (other.decList != null) {
				return false;
			}
		} else if (!decList.equals(other.decList)) {
			return false;
		}
		if (statementList == null) {
			if (other.statementList != null) {
				return false;
			}
		} else if (!statementList.equals(other.statementList)) {
			return false;
		}
		return true;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitBlock(this,arg);
	}

}
