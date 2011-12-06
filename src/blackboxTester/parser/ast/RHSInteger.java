package blackboxTester.parser.ast;

/**
 * A class representing an integer in the right hand side of a rewrite rule.
 */
public class RHSInteger implements RHS {
	/**
	 * The value of this integer
	 */
	private int value;
	
	/**
	 * Create a new RHSInteger with the given value
	 * 
	 * @param value
	 */
	public RHSInteger(int value) {
		this.value = value;
	}
	
	/**
	 * @return the value of this integer
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	public boolean isVariable() {
		return false;
	}
}
