package blackboxTester.parser.ast;

/**
 * A class representing a variable on either the right or left hand sides
 * of a rewrite rule abstract syntax tree.
 */
public class Variable implements Term, RHS {
	/**
	 * The identifier for this variable.
	 */
	private String name;
	
	/**
	 * Create a new variable with the given name.
	 * @param name
	 */
	public Variable(String name) {
		this.name = name;
	}
	
	/**
	 * @return The name of this variable.
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public boolean isVariable() {
		return true;
	}
}
