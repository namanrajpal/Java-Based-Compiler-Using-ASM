package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;

public class BinaryChain extends Chain {

	final Chain e0;
	final Token arrow;
	final ChainElem e1;
	
	public BinaryChain(Token firstToken, Chain e0, Token arrow, ChainElem e1) {
		super(firstToken); 
		this.e0= e0;
		this.arrow = arrow;
		this.e1 = e1;
	}

	public Chain getE0() {
		return e0;
	}


	public Token getArrow() {
		return arrow;
	}

	public ChainElem getE1() {
		return e1;
	}
	
	@Override
	public String toString() {
		return "BinaryChain [e0=" + e0 + ", arrow=" + arrow.getText() + ", e1=" + e1 +"]";
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((arrow == null) ? 0 : arrow.hashCode());
		result = prime * result + ((e0 == null) ? 0 : e0.hashCode());
		result = prime * result + ((e1 == null) ? 0 : e1.hashCode());
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
		if (!(obj instanceof BinaryChain)) {
			return false;
		}
		BinaryChain other = (BinaryChain) obj;
		if (arrow == null) {
			if (other.arrow != null) {
				return false;
			}
		} else if (!arrow.equals(other.arrow)) {
			return false;
		}
		if (e0 == null) {
			if (other.e0 != null) {
				return false;
			}
		} else if (!e0.equals(other.e0)) {
			return false;
		}
		if (e1 == null) {
			if (other.e1 != null) {
				return false;
			}
		} else if (!e1.equals(other.e1)) {
			return false;
		}
		return true;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitBinaryChain(this,arg);
	}


}
