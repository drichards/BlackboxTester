package blackboxTester.ast;

import java.util.ArrayList;

/**
 * An abstract syntax tree (AST) that represents a function call.
 *
 */
public class FunctionCall implements AST {
	/**
	 * The method name of this function call
	 */
	private String methodName;
	/**
	 * The arguments to this function call
	 */
	private ArrayList<AST> args;
	
	@Override
	public boolean isPrimitive() {
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(methodName);
		
		for (AST arg : args) {
			builder.append(" ");
			builder.append(arg.toString());
		}
		
		builder.append(")");
		
		return builder.toString();
	}
	
	/**
	 * Create a new FunctionCall object with the given method name and args.
	 * 
	 * @param methodName The method name of this function call.
	 * @param args The arguments to this function call.
	 */
	public FunctionCall(String methodName, ArrayList<AST> args) {
		this.methodName = methodName;
		this.args = args;
	}
}
