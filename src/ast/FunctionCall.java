package ast;

import java.util.ArrayList;

public class FunctionCall implements AST{
	private String methodName;
	private ArrayList<AST> args;
	
	public boolean isPrimitive() {
		return true;
	}
	
	public FunctionCall(String methodName, ArrayList<AST> args) {
		this.methodName = methodName;
		this.args = args;
	}
	
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
}
