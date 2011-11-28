package blackboxTester.parser.ast;

/**
 * An abstract syntax tree representing an Equation in the the parsed input.
 */
public class Equation {
	private Term leftHandSide;
	private RHS rightHandSide;
	
	public Equation(Term leftHandSide, RHS rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}
	
	public Term getLeftHandSide() {
		return leftHandSide;
	}
	
	public RHS getRightHandSide() {
		return rightHandSide;
	}
}
