package blackboxTester.parser.ast;

import java.util.ArrayList;

/**
 * A class representing primitive operations on the right hand side
 * of a rewrite rule.
 * 
 * For non-primitive operations see RHSOperation.
 * 
 * @see RHSOperation
 */
public class RHSPrimitiveOperation implements RHS {
	/**
	 * The name of this operation
	 */
	private String operation;
	
	/**
	 * The arguments to this operation
	 */
	private ArrayList<RHS> args;
	
	/**
	 * Create a new RHSPrimitiveOperation with the given operation
	 * and arguments.
	 * 
	 * @param operation
	 * @param args
	 */
	public RHSPrimitiveOperation(String operation, ArrayList<RHS> args) {
		this.operation = operation;
		this.args = args;
	}
	
	/**
	 * @return The name of this operation.
	 */
	public String getOperation() {
		return operation;
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
