package blackboxTester.parser.ast;

/**
 * An abstract syntax tree representing an Equation in the the parsed input.
 */
public class Equation {
	private Term leftHandSide;
	private RHS rightHandSide;
	
	/**
	 * Create a new equation with the given left and right hand sides.
	 * 
	 * @param leftHandSide
	 * @param rightHandSide
	 */
	public Equation(Term leftHandSide, RHS rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}
	
	/**
	 * @return The left hand side of the equation
	 */
	public Term getLeftHandSide() {
		return leftHandSide;
	}
	
	/**
	 * @return The right hand side of the equation
	 */
	public RHS getRightHandSide() {
		return rightHandSide;
	}
}
