package cop5556sp17.AST;

import java.util.List;

import cop5556sp17.Scanner.Token;

public class Tuple extends ASTNode {

	final List<Expression> exprList;

	public Tuple(Token firstToken, List<Expression> argList) {
		super(firstToken);
		this.exprList = argList;
	}

	public List<Expression> getExprList() {
		return exprList;
	}

	@Override
	public String toString() {
		return "Tuple [exprList=" + exprList + "]";
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((exprList == null) ? 0 : exprList.hashCode());
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
		if (!(obj instanceof Tuple)) {
			return false;
		}
		Tuple other = (Tuple) obj;
		if (exprList == null) {
			if (other.exprList != null) {
				return false;
			}
		} else if (!exprList.equals(other.exprList)) {
			return false;
		}
		return true;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitTuple(this, arg);
	}
}
