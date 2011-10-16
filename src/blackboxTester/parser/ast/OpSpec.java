package blackboxTester.parser.ast;

import java.util.ArrayList;

/**
 * An abstract syntax tree representing the operation specs in the parsed input.
 */
public class OpSpec {
	
	/**
	 * The name of the operation.
	 */
	private final String operation;
	
	/**
	 * A list of the arg types for the operation.
	 */
	private final ArrayList<Type> argTypes;
	
	/**
	 * The return type for the operation.
	 */
	private final Type type;
	
	/**
	 * Does this operation only take primitive arguments.
	 */
	private boolean primitiveOnlyArgs;

	/**
	 * Create a new operation with the given name, list of arg types, and return
	 * type.
	 * 
	 * @param operation
	 * @param argTypes
	 * @param type
	 */
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

	/**
	 * @return The name of this operation.
	 */
	public String getOperation() {
		return operation;
	}
	
	/**
	 * @return The list of argument types for this operation.
	 */
	public ArrayList<Type> getArgTypes() {
		return argTypes;
	}
	
	/**
	 * @return The return type for this operation.
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * @return  True if this operation only has primitive arguments.
	 */
	public boolean hasOnlyPrimitiveArgs() {
		return primitiveOnlyArgs;
	}
}
