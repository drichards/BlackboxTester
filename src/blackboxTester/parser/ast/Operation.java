package blackboxTester.parser.ast;

import java.util.ArrayList;

public class Operation implements Term {
	private String name;
	private ArrayList<Term> args;
	
	public Operation(String name, ArrayList<Term> args) {
		this.name = name;
		this.args = args;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Term> getArgs() {
		return args;
	}
	
	public boolean isVariable() {
		return false;
	}
}
