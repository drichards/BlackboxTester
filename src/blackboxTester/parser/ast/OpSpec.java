package blackboxTester.parser.ast;

import java.util.ArrayList;

public class OpSpec {
	
	private final String operation;
	private final ArrayList<Type> argTypes;
	private final Type type;
	private boolean primitiveOnlyArgs;

	public OpSpec(String operation, ArrayList<Type> argTypes, Type type) {
		this.operation = operation;
		this.argTypes = argTypes;
		this.type = type;
		
		primitiveOnlyArgs = true;
		for (Type arg : argTypes) {
			if (!arg.isPrimitive()) {
				primitiveOnlyArgs = false;
				break;
			}
		}
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
	
	public boolean hasOnlyPrimitiveArgs() {
		return primitiveOnlyArgs;
	}
}
