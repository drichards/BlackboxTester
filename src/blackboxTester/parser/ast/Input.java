package blackboxTester.parser.ast;

import java.util.ArrayList;

public class Input {
	private ArrayList<Signature> signatures;
	private ArrayList<Equation> equations;

	public Input(ArrayList<Signature> signatures, ArrayList<Equation> equations) {
		this.signatures = signatures;
		this.equations = equations;
	}
	
	public ArrayList<Signature> getSignatures() {
		return signatures;
	}
	
	public ArrayList<Equation> getEquations() {
		return equations;
	}
}
