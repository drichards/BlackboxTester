package blackboxTester.parser.ast;

import java.util.ArrayList;

/**
 * Abstract syntax tree representing the overall input to the blackbox tester
 * program.
 */
public class Input {
	/**
	 * The signatures in the input.
	 */
	private ArrayList<Signature> signatures;
	/**
	 * The equations in the input.
	 */
	private ArrayList<Equation> equations;

	/**
	 * Create a new Input with the given signatures and equations.
	 * 
	 * @param signatures
	 * @param equations
	 */
	public Input(ArrayList<Signature> signatures, ArrayList<Equation> equations) {
		this.signatures = signatures;
		this.equations = equations;
	}
	
	/**
	 * @return The Signatures in this Input.
	 */
	public ArrayList<Signature> getSignatures() {
		return signatures;
	}
	
	/**
	 * @return The Equations in this Input.
	 */
	public ArrayList<Equation> getEquations() {
		return equations;
	}
}
