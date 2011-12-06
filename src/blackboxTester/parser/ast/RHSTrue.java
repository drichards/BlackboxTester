package blackboxTester.parser.ast;

/**
 * A class representing the "#f" value in the right hand side of 
 * a rewrite rule.
 */
public class RHSTrue implements RHS {
	
	@Override
	public boolean isVariable() {
		return false;
	}
}
