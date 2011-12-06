package blackboxTester.parser.ast;

/**
 * The right hand side of a rewrite rule
 */
public interface RHS {
	/**
	 * @return true if this is a variable
	 */
	public boolean isVariable();
}
