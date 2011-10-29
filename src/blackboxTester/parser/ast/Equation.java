package blackboxTester.parser.ast;

/**
 * An abstract syntax tree representing an Equation in the the parsed input.
 */
public class Equation {
	private Term leftHandSide;
	private Term rightHandSide;
	
	public Equation(Term leftHandSide, Term rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}
	
	public Term getLeftHandSide() {
		return leftHandSide;
	}
	
	public Term getRightHandSide() {
		return rightHandSide;
	}
}
