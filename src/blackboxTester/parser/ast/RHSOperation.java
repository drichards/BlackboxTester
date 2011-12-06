package blackboxTester.parser.ast;

import java.util.ArrayList;

/**
 * A class representing an operation on the right hand side of a rewrite
 * equation.
 * 
 * For primitive operations see RHSPrimitiveOperation.
 * 
 * @see RHSPrimitiveOperation
 */
public class RHSOperation implements RHS {
	/**
	 * The name of this operation.
	 */
	private String name;
	
	/**
	 * The arguments to this operation
	 */
	private ArrayList<RHS> args;
	
	/**
	 * Create a new RHSOperation with the given name and arguments
	 * @param name
	 * @param args
	 */
	public RHSOperation(String name, ArrayList<RHS> args) {
		this.name = name;
		this.args = args;
	}
	
	/**
	 * @return The name of this operation.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return The arguments to this operation.
	 */
	public ArrayList<RHS> getArgs() {
		return args;
	}
	
	@Override
	public boolean isVariable() {
		return false;
	}
}
