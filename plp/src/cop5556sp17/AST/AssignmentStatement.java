package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;

public class AssignmentStatement extends Statement {
	
	public final IdentLValue var;
	public final Expression e;

	public AssignmentStatement(Token firstToken, IdentLValue var, Expression e) {
		super(firstToken);
		this.var = var;
		this.e = e;
	}
	
	public IdentLValue getVar() {
		return var;
	}

	public Expression getE() {
		return e;
	}

	
	@Override
	public String toString() {
		return "AssignStatement [e=" + e + ", firstToken=" + firstToken + "]";
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((e == null) ? 0 : e.hashCode());
		result = prime * result + ((var == null) ? 0 : var.hashCode());
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
		if (!(obj instanceof AssignmentStatement)) {
			return false;
		}
		AssignmentStatement other = (AssignmentStatement) obj;
		if (e == null) {
			if (other.e != null) {
				return false;
			}
		} else if (!e.equals(other.e)) {
			return false;
		}
		if (var == null) {
			if (other.var != null) {
				return false;
			}
		} else if (!var.equals(other.var)) {
			return false;
		}
		return true;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitAssignmentStatement(this,arg);
	}

}
