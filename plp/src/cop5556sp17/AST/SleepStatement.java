package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;

public class SleepStatement extends Statement {
	private Expression e;

	public SleepStatement(Token firstToken, Expression e) {
		super(firstToken);
		this.setE(e);
	}


	public Expression getE() {
		return e;
	}

	public void setE(Expression e) {
		this.e = e;
	}

	@Override
	public String toString() {
		return "SleepStatement [e=" + e + "]";
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((e == null) ? 0 : e.hashCode());
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
		if (!(obj instanceof SleepStatement)) {
			return false;
		}
		SleepStatement other = (SleepStatement) obj;
		if (e == null) {
			if (other.e != null) {
				return false;
			}
		} else if (!e.equals(other.e)) {
			return false;
		}
		return true;
	}


	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitSleepStatement(this, arg);
	}

}
