package parser.ast;

import java.util.ArrayList;

public class OpSpec {
	
	private final String operation;
	private final ArrayList<Type> argTypes;
	private final Type type;

	public OpSpec(String operation, ArrayList<Type> argTypes, Type type) {
		this.operation = operation;
		this.argTypes = argTypes;
		this.type = type;
	}

	public String getOperation() {
		return operation;
	}
	
	public ArrayList<Type> getArgTypes() {
		return argTypes;
	}
	
	public Type getType() {
		return type;
	}
}
