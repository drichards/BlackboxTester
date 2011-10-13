package parser.ast;

import java.util.ArrayList;

public class Signature {
	private final String typename;
	private final ArrayList<OpSpec> opSpecs;
	
	public Signature(String typename, ArrayList<OpSpec> opSpecs) {
		this.typename = typename;
		this.opSpecs = opSpecs;
	}
	
	public String getTypename() {
		return typename;
	}
	
	public ArrayList<OpSpec> getOpSpecs() {
		return opSpecs;
	}
}
