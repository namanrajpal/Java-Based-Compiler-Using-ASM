package cop5556sp17.AST;

import java.util.ArrayList;

import cop5556sp17.Scanner.Token;

public class Program extends ASTNode {

	private final ArrayList<ParamDec> params;
	private final Block b;

	public Program(Token firstToken, ArrayList<ParamDec> paramList, Block b) {
		super(firstToken);
		this.params = paramList;
		this.b = b;
	}

	public String getName() {
		return firstToken.getText();
	}

	public ArrayList<ParamDec> getParams() {
		return params;
	}

	public Block getB() {
		return b;
	}

	@Override
	public String toString() {
		return "Program [firstToken=" + firstToken + ", paramList=" + params + ", block=" + b + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
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
		if (!(obj instanceof Program)) {
			return false;
		}
		Program other = (Program) obj;
		if (b == null) {
			if (other.b != null) {
				return false;
			}
		} else if (!b.equals(other.b)) {
			return false;
		}
		if (params == null) {
			if (other.params != null) {
				return false;
			}
		} else if (!params.equals(other.params)) {
			return false;
		}
		return true;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitProgram(this, arg);
	}

}
