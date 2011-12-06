package blackboxTester.parser.ast;

/**
 * A class representing a term in the left or right hand sides of
 * a rewrite rule abstract syntax tree.
 */
public interface Term {
	/**
	 * @return true if this is a variable
	 */
	public boolean isVariable();
}
