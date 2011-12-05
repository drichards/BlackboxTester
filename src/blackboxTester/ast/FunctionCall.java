package blackboxTester.ast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * An abstract syntax tree (AST) that represents a function call.
 *
 */
public class FunctionCall implements IFunctionCall {
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
	public boolean isFullyReduced() {
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
	
	@Override
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

	@Override
	public String getHash() {
		MessageDigest digest = null;
		
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		return new String(digest.digest(toString().getBytes()));
	}

	@Override
	public String getComparator() {
		//if(this.methodName.equals("+") || this.methodName.equals("-")) {
			return "poop";
		//} else {
			//return null;
		//}
	}
}
