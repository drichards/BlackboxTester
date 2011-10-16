package blackboxTester.parser.ast;

/**
 * An abstract syntax tree representing a non-primitive type in the parsed
 * input.
 */
public class DynamicType implements Type {
	/**
	 * The name of this type.
	 */
	private final String value;
	
	/**
	 * Create a new DynamicType with the given name.
	 * @param value
	 */
	public DynamicType(String value) {
		this.value = value;
	}
	
	/**
	 * @return The name of this type.
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	public boolean isPrimitive() {
		return false;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof DynamicType) && 
		((DynamicType)other).getValue().equals(this.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

}
