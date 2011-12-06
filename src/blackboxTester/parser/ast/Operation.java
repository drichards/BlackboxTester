package blackboxTester.parser.ast;

import java.util.ArrayList;

/**
 * An operation in a rewrite rule
 */
public class Operation implements Term {
	/**
	 * The operation's name
	 */
	private String name;
	
	/**
	 * The arguments to the operation
	 */
	private ArrayList<Term> args;
	
	/**
	 * Create a new operation with the given name and arguments
	 * 
	 * @param name
	 * @param args
	 */
	public Operation(String name, ArrayList<Term> args) {
		this.name = name;
		this.args = args;
	}
	
	/**
	 * @return This operation's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return The list of this operation's arguments
	 */
	public ArrayList<Term> getArgs() {
		return args;
	}
	
	@Override
	public boolean isVariable() {
		return false;
	}
}
