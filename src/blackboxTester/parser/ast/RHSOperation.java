package blackboxTester.parser.ast;

import java.util.ArrayList;

public class RHSOperation implements RHS {
	private String name;
	private ArrayList<RHS> args;
	
	public RHSOperation(String name, ArrayList<RHS> args) {
		this.name = name;
		this.args = args;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<RHS> getArgs() {
		return args;
	}
}
