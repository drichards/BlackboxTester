package blackboxTester.parser.ast;

import java.util.ArrayList;

public class RHSPrimitiveOperation implements RHS {
	private String operation;
	private ArrayList<RHS> args;
	
	public RHSPrimitiveOperation(String operation, ArrayList<RHS> args) {
		this.operation = operation;
		this.args = args;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public ArrayList<RHS> getArgs() {
		return args;
	}
}
