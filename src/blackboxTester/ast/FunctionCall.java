package blackboxTester.ast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An abstract syntax tree (AST) that represents a function call.
 *
 */
public class FunctionCall implements AST, Serializable {
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
		return false;
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
	 * 
	 * @return ArrayList<AST> of this FunctionCall's Arguments
	 */
	public ArrayList<AST> getArgs() {
		return this.args;
	}
	
	public String getMethodName() {
		return this.methodName;
	}
	
	@Override
	public AST deepCopy() {
		ArrayList<AST> copiedArgs = new ArrayList<AST>();
		
		for (AST arg: args) {
			copiedArgs.add(arg.deepCopy());
		}
		
		return new FunctionCall(this.methodName, copiedArgs);
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
